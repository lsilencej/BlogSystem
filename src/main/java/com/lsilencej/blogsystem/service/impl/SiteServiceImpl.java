package com.lsilencej.blogsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.lsilencej.blogsystem.entity.Article;
import com.lsilencej.blogsystem.entity.Comment;
import com.lsilencej.blogsystem.entity.Statistic;
import com.lsilencej.blogsystem.entity.StatisticBack;
import com.lsilencej.blogsystem.mapper.ArticleMapper;
import com.lsilencej.blogsystem.mapper.CommentMapper;
import com.lsilencej.blogsystem.mapper.StatisticMapper;
import com.lsilencej.blogsystem.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：lsilencej
 * @date ：Created in 2022/6/22 15:26
 * @description：
 * @modified By：
 * @version: $
 */
@Service
public class SiteServiceImpl implements SiteService {

    @Autowired
    private StatisticMapper statisticMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public void updateStatistics(Article article) {
        Statistic statistic = statisticMapper.selectByArticleId(article.getId());
        statistic.setHits(statistic.getHits() + 1);
        statisticMapper.updateArticleHits(statistic);
    }

    @Override
    public List<Article> recentArticles(int limit) {
        PageHelper.startPage(1, limit > 10 || limit < 1 ? 10 : limit);
        List<Article> articles = articleMapper.getAllArticles();
        for (Article article: articles) {
            Statistic statistic = statisticMapper.selectByArticleId(article.getId());
            article.setCommentsNum(statistic.getCommentsNum());
        }
        return articles;
    }

    @Override
    public List<Comment> recentComments(int limit) {
        PageHelper.startPage(1, limit > 10 || limit < 1 ? 10 : limit);
        return commentMapper.selectAllComments();
    }

    @Override
    public StatisticBack getStatisticBack() {
        StatisticBack statisticBack = new StatisticBack();
        statisticBack.setArticles(articleMapper.countAllArticles());
        statisticBack.setComments(commentMapper.countComments());
        return statisticBack;
    }
}
