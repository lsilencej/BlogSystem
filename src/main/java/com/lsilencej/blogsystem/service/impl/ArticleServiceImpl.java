package com.lsilencej.blogsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lsilencej.blogsystem.entity.Article;
import com.lsilencej.blogsystem.entity.Statistic;
import com.lsilencej.blogsystem.mapper.ArticleMapper;
import com.lsilencej.blogsystem.mapper.CommentMapper;
import com.lsilencej.blogsystem.mapper.StatisticMapper;
import com.lsilencej.blogsystem.service.ArticleService;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ：lsilencej
 * @date ：Created in 2022/6/22 14:08
 * @description：
 * @modified By：
 * @version: $
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private StatisticMapper statisticMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public PageInfo<Article> getArticles(int page, int count) {
        PageHelper.startPage(page, count);
        List<Article> articles = articleMapper.getAllArticles();
        for (Article article: articles) {
            Statistic statistic = statisticMapper.selectByPrimaryKey(article.getId());
            article.setHits(statistic.getHits());
            article.setCommentsNum(statistic.getCommentsNum());
        }
        return new PageInfo<>(articles);
    }

    @Override
    public List<Article> getHotArticles() {
        List<Statistic> statistics = statisticMapper.getOrderHitsComments();
        List<Article> articles = new ArrayList<>();
        int i = 0;
        for (Statistic statistic: statistics) {
            Article article = articleMapper.selectByPrimaryKey(statistic.getArticleId());
            article.setHits(statistic.getHits());
            article.setCommentsNum(statistic.getCommentsNum());
            articles.add(article);
            i++;
            if (i > 9) break;
        }
        return articles;
    }

    @Override
    public Article getArticleById(int id) {
        Article article = null;
        Object o = redisTemplate.opsForValue().get("article_" + id);
        if (o != null) {
            article = (Article) o;
        } else {
            article = articleMapper.selectByPrimaryKey(id);
            redisTemplate.opsForValue().set("article_" + id, article);
        }
        return article;
    }

    @Override
    public void addArticle(Article article) {
        article.setContent(EmojiParser.parseToAliases(article.getContent()));
        article.setCreated(new Date());
        article.setHits(0);
        article.setCommentsNum(0);

        articleMapper.insert(article);
        statisticMapper.addStatistic(article);
    }

    @Override
    public void updateArticleById(Article article) {
        article.setModified(new Date());
        articleMapper.updateByPrimaryKeySelective(article);
        redisTemplate.delete("article_" + article.getId());
    }

    @Override
    public void deleteArticleById(int id) {
        articleMapper.deleteByPrimaryKey(id);
        redisTemplate.delete("article_" + id);
        statisticMapper.deleteByArticleId(id);
        commentMapper.deleteByArticleId(id);
    }
}
