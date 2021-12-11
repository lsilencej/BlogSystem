package com.lsilencej.blog_system.service;

import com.github.pagehelper.PageInfo;
import com.lsilencej.blog_system.entity.Article;

import java.util.List;

/**
 * @author ：lsilencej
 * @date ：Created in 2021/12/2 21:50
 * @description：
 * @modified By：
 * @version: $
 */
public interface IArticleService {
    public PageInfo<Article> getArticles(Integer page, Integer count);

    public List<Article> getHeatArticle();

    public Article selectArticleById(Integer id);

    public void publishArticle(Article article);

    public void updateArticleById(Article article);

    public void deleteArticleById(int id);
}
