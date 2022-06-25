package com.lsilencej.blogsystem.controller;

import com.github.pagehelper.PageInfo;
import com.lsilencej.blogsystem.entity.Article;
import com.lsilencej.blogsystem.entity.Comment;
import com.lsilencej.blogsystem.service.ArticleService;
import com.lsilencej.blogsystem.service.CommentService;
import com.lsilencej.blogsystem.service.SiteService;
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
 * @date ：Created in 2022/6/22 14:32
 * @description：
 * @modified By：
 * @version: $
 */
@Controller
public class IndexController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private SiteService siteService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        return this.index(request, 1, 5);
    }

    @GetMapping("/page/{p}")
    public String index(HttpServletRequest request, @PathVariable("p") int page, @RequestParam(value = "count", defaultValue = "5") int count) {
        PageInfo<Article> articles = articleService.getAllArticles(page, count);
        List<Article> hotArticles = articleService.getHotArticles();
        request.setAttribute("articles", articles);
        request.setAttribute("hotArticles", hotArticles);
        return "client/index";
    }

    @GetMapping("/article/{id}")
    public String getArticleById(HttpServletRequest request, @PathVariable("id") int id) {
        Article article = articleService.getArticleById(id);
        if (article != null) {
            siteService.updateStatistics(article);
            getCommentById(request, article);
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
            PageInfo<Comment> commentPageInfo = commentService.getComments(article.getId(), Integer.parseInt(cp), 3);
            request.setAttribute("comments", commentPageInfo);
        }
    }


}
