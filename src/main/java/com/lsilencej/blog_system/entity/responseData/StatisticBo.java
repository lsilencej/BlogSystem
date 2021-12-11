package com.lsilencej.blog_system.entity.responseData;

/**
 * @author ：lsilencej
 * @date ：Created in 2021/12/11 16:21
 * @description：
 * @modified By：
 * @version: $
 */
public class StatisticBo {
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
        return "StatisticBo{" +
                "articles=" + articles +
                ", comments=" + comments +
                '}';
    }
}
