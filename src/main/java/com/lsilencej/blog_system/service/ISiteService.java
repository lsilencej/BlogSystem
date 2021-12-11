package com.lsilencej.blog_system.service;

import com.lsilencej.blog_system.entity.Article;
import com.lsilencej.blog_system.entity.Comment;
import com.lsilencej.blog_system.entity.responseData.StatisticBo;

import java.util.List;

/**
 * @author ：lsilencej
 * @date ：Created in 2021/12/3 13:15
 * @description：
 * @modified By：
 * @version: $
 */
public interface ISiteService {
    public void updateStatistics(Article article);

    public List<Article> recentArticles(int limit);

    public List<Comment> recentComments(int limit);

    public StatisticBo getStatistics();
}
