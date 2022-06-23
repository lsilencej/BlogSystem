package com.lsilencej.blogsystem.service;

import com.lsilencej.blogsystem.entity.Article;
import com.lsilencej.blogsystem.entity.Comment;
import com.lsilencej.blogsystem.entity.StatisticBack;

import java.util.List;

/**
 * @author ：lsilencej
 * @date ：Created in 2022/6/22 15:25
 * @description：
 * @modified By：
 * @version: $
 */
public interface SiteService {

    void updateStatistics(Article article);

    List<Article> recentArticles(int limit);

    List<Comment> recentComments(int limit);

    StatisticBack getStatisticBack();

}
