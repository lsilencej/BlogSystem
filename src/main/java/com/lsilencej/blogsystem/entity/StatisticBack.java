package com.lsilencej.blogsystem.entity;

/**
 * @author ：lsilencej
 * @date ：Created in 2022/6/22 16:25
 * @description：
 * @modified By：
 * @version: $
 */
public class StatisticBack {

    private Integer articles;
    private Integer comments;

    public Integer getArticles() {
        return articles;
    }

    public void setArticles(Integer articles) {
        this.articles = articles;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "StatisticBack{" +
                "articles=" + articles +
                ", comments=" + comments +
                '}';
    }
}
