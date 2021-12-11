package com.lsilencej.blog_system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lsilencej.blog_system.entity.Article;
import com.lsilencej.blog_system.entity.Statistic;
import com.lsilencej.blog_system.mapper.ArticleMapper;
import com.lsilencej.blog_system.mapper.CommentMapper;
import com.lsilencej.blog_system.mapper.StatisticMapper;
import com.lsilencej.blog_system.service.IArticleService;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ：lsilencej
 * @date ：Created in 2021/12/2 21:51
 * @description：
 * @modified By：
 * @version: $
 */
@Service
public class ArticleServiceImpl implements IArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private StatisticMapper statisticMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public PageInfo<Article> getArticles(Integer page, Integer count) {
        PageHelper.startPage(page, count);
        List<Article> articleList = articleMapper.selectArticles();

        for (Article article : articleList) {
            Statistic statistic = statisticMapper.selectByPrimaryKey(article.getId());
            article.setHits(statistic.getHits());
            article.setCommentsNum(statistic.getCommentsNum());
        }

        PageInfo<Article> articlePageInfo = new PageInfo<>(articleList);
        return articlePageInfo;
    }

    @Override
    public List<Article> getHeatArticle() {
        List<Statistic> list = statisticMapper.getStatistic();
        ArrayList<Article> arrayList = new ArrayList<>();
        int i = 0;
        for (Statistic statistic : list) {
            Article article = articleMapper.selectByPrimaryKey(statistic.getArticleId());
            article.setHits(statistic.getHits());
            article.setCommentsNum(statistic.getCommentsNum());
            arrayList.add(article);
            i++;
            if (i > 9) {
                break;
            }
        }
        return arrayList;
    }

    @Override
    public Article selectArticleById(Integer id) {
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
    public void publishArticle(Article article) {
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
        statisticMapper.deleteByPrimaryKey(id);
        commentMapper.deleteByAid(id);
    }
}
