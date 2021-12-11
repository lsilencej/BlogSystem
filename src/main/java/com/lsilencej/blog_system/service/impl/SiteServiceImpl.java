package com.lsilencej.blog_system.service.impl;

import com.github.pagehelper.PageHelper;
import com.lsilencej.blog_system.entity.Article;
import com.lsilencej.blog_system.entity.Comment;
import com.lsilencej.blog_system.entity.Statistic;
import com.lsilencej.blog_system.entity.responseData.StatisticBo;
import com.lsilencej.blog_system.mapper.ArticleMapper;
import com.lsilencej.blog_system.mapper.CommentMapper;
import com.lsilencej.blog_system.mapper.StatisticMapper;
import com.lsilencej.blog_system.service.ISiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：lsilencej
 * @date ：Created in 2021/12/3 13:16
 * @description：
 * @modified By：
 * @version: $
 */
@Service
public class SiteServiceImpl implements ISiteService {
    @Autowired
    private StatisticMapper statisticMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public void updateStatistics(Article article) {
        Statistic statistic = statisticMapper.selectByPrimaryKey(article.getId());
        statistic.setHits(statistic.getHits() + 1);
        statisticMapper.updateArticleHits(statistic);
    }

    @Override
    public List<Article> recentArticles(int limit) {
        PageHelper.startPage(1, limit > 10 || limit < 1 ? 10 : limit);
        List<Article> articles = articleMapper.selectArticles();
        for (Article article : articles) {
            Statistic statistic = statisticMapper.selectByPrimaryKey(article.getId());
            article.setCommentsNum(statistic.getCommentsNum());
        }
        return articles;
    }

    @Override
    public List<Comment> recentComments(int limit) {
        PageHelper.startPage(1, limit > 10 || limit < 1 ? 10 : limit);
        List<Comment> comments = commentMapper.selectComments();
        return comments;
    }

    @Override
    public StatisticBo getStatistics() {
        StatisticBo statisticBo = new StatisticBo();
        Integer articles = articleMapper.countArticle();
        Integer comments = commentMapper.countComment();
        statisticBo.setArticles(articles);
        statisticBo.setComments(comments);
        return statisticBo;
    }
}
