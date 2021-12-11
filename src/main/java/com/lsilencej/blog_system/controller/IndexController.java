package com.lsilencej.blog_system.controller;

import com.github.pagehelper.PageInfo;
import com.lsilencej.blog_system.entity.Article;
import com.lsilencej.blog_system.entity.Comment;
import com.lsilencej.blog_system.service.impl.ArticleServiceImpl;
import com.lsilencej.blog_system.service.impl.CommentServiceImpl;
import com.lsilencej.blog_system.service.impl.SiteServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author ：lsilencej
 * @date ：Created in 2021/12/2 21:59
 * @description：
 * @modified By：
 * @version: $
 */
@Controller
public class IndexController {
    @Autowired
    private ArticleServiceImpl articleService;

    @Autowired
    private SiteServiceImpl siteServiceImpl;

    @Autowired
    private CommentServiceImpl commentService;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        return this.index(request, 1, 5);
    }

    @GetMapping("/page/{p}")
    public String index(HttpServletRequest request, @PathVariable("p") int page, @RequestParam(value = "count", defaultValue = "5") int count) {
        PageInfo<Article> pageInfo = articleService.getArticles(page, count);
        List<Article> heatArticle = articleService.getHeatArticle();
        request.setAttribute("articles", pageInfo);
        request.setAttribute("articleList", heatArticle);
        return "client/index";
    }

    @GetMapping(value = "/article/{id}")
    public String getArticleById(@PathVariable("id") Integer id, HttpServletRequest request) {
        Article article = articleService.selectArticleById(id);
        if (article != null) {
            getArticleComments(request, article);
            siteServiceImpl.updateStatistics(article);
            request.setAttribute("article", article);
            return "client/articleDetails";
        } else {
            return "comm/error_404";
        }
    }

    private void getCommentById(HttpServletRequest request, Article article) {
        if (article.getAllowComment()) {
            String cp = request.getParameter("cp");
            cp = StringUtils.isBlank(cp) ? "1" : cp;
            request.setAttribute("cp", cp);
            PageInfo<Comment> comments = commentService.getComment(article.getId(), Integer.parseInt(cp), 3);
            request.setAttribute("comments", comments);
        }
    }

    private void getArticleComments(HttpServletRequest request, Article article) {
        if (article.getAllowComment()) {
            String cp = request.getParameter("cp");
            cp = StringUtils.isBlank(cp) ? "1" : cp;
            request.setAttribute("cp", cp);
            PageInfo<Comment> comments = commentService.getComment(article.getId(), Integer.parseInt(cp), 3);
            request.setAttribute("cp", cp);
            request.setAttribute("comments", comments);
        }
    }
}
