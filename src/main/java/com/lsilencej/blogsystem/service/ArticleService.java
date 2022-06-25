package com.lsilencej.blogsystem.service;

import com.github.pagehelper.PageInfo;
import com.lsilencej.blogsystem.entity.Article;

import java.util.List;

/**
 * @author ：lsilencej
 * @date ：Created in 2022/6/22 13:56
 * @description：
 * @modified By：
 * @version: $
 */
public interface ArticleService {

    PageInfo<Article> getAllArticles(int page, int count);

    List<Article> getHotArticles();

    Article getArticleById(int id);

    void addArticle(Article article);

    void updateArticle(Article article);

    void deleteArticleById(int id);

}
