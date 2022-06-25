package com.lsilencej.blogsystem.controller;

import com.github.pagehelper.PageInfo;
import com.lsilencej.blogsystem.entity.Article;
import com.lsilencej.blogsystem.entity.Comment;
import com.lsilencej.blogsystem.entity.StatisticBack;
import com.lsilencej.blogsystem.service.ArticleService;
import com.lsilencej.blogsystem.service.SiteService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author ：lsilencej
 * @date ：Created in 2022/6/22 16:34
 * @description：
 * @modified By：
 * @version: $
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private SiteService siteService;

    @Autowired
    private ArticleService articleService;

    @GetMapping(value = {"", "/index"})
    public String index(HttpServletRequest request) {
        List<Article> articles = siteService.recentArticles(5);
        List<Comment> comments = siteService.recentComments(5);
        StatisticBack statisticBack = siteService.getStatisticBack();
        request.setAttribute("articles", articles);
        request.setAttribute("comments", comments);
        request.setAttribute("statistics", statisticBack);
        return "back/index";
    }

    @GetMapping("/article/newPage")
    public String newArticle() {
        return "back/article_edit";
    }

    @PostMapping("/article/publish")
    @ResponseBody
    public ResponseData addArticle(Article article) {
        if (StringUtils.isBlank(article.getCategories())) {
            article.setCategories("默认分类");
        }
        try {
            articleService.addArticle(article);
            return ResponseData.ok();
        } catch (Exception e) {
            return ResponseData.fail();
        }
    }

    @GetMapping("/article")
    public String index(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "count", defaultValue = "10") int count) {
        PageInfo<Article> pageInfo = articleService.getAllArticles(page, count);
        request.setAttribute("articles", pageInfo);
        return "back/article_list";
    }

    @GetMapping("/article/{id}")
    public String editArticle(HttpServletRequest request, @PathVariable("id") int id) {
        Article article = articleService.getArticleById(id);
        request.setAttribute("contents", article);
        request.setAttribute("categories", article.getCategories());
        return "back/article_edit";
    }

    @PostMapping("/article/modify")
    @ResponseBody
    public ResponseData modifyArticle(Article article) {
        try {
            articleService.updateArticle(article);
            return ResponseData.ok();
        } catch (Exception e) {
            return ResponseData.fail();
        }
    }

    @PostMapping("/article/delete")
    @ResponseBody
    public ResponseData deleteArticle(@RequestParam int id) {
        try {
            articleService.deleteArticleById(id);
            return ResponseData.ok();
        } catch (Exception e) {
            return ResponseData.fail();
        }
    }

}
