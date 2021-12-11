package com.lsilencej.blog_system.controller;

import com.github.pagehelper.PageInfo;
import com.lsilencej.blog_system.entity.Article;
import com.lsilencej.blog_system.entity.Comment;
import com.lsilencej.blog_system.entity.responseData.StatisticBo;
import com.lsilencej.blog_system.service.IArticleService;
import com.lsilencej.blog_system.service.ISiteService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author ：lsilencej
 * @date ：Created in 2021/12/11 16:33
 * @description：
 * @modified By：
 * @version: $
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ISiteService siteService;

    @Autowired
    private IArticleService articleService;

    @GetMapping(value = {"", "/index"})
    public String index(HttpServletRequest request) {
        List<Article> articles = siteService.recentArticles(5);
        List<Comment> comments = siteService.recentComments(5);
        StatisticBo statisticBo = siteService.getStatistics();
        request.setAttribute("articles", articles);
        request.setAttribute("comments", comments);
        request.setAttribute("statistics", statisticBo);
        return "back/index";
    }

    @GetMapping("/article/toEditPage")
    public String newArticle() {
        return "back/article_edit";
    }

    @PostMapping("/article/publish")
    @ResponseBody
    public ResponseData publishArticle(Article article) {
        if (StringUtils.isBlank(article.getCategories())) {
            article.setCategories("默认分类");
        }
        try {
            articleService.publishArticle(article);
            return ResponseData.ok();
        } catch (Exception e) {
            return ResponseData.fail();
        }
    }

    @GetMapping("/article")
    public String index(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "count", defaultValue = "10") int count, HttpServletRequest request) {
        PageInfo<Article> pageInfo = articleService.getArticles(page, count);
        request.setAttribute("articles", pageInfo);
        return "back/article_list";
    }

    @GetMapping("/article/{id}")
    public String editArticle(@PathVariable("id") Integer id, HttpServletRequest request) {
        Article article = articleService.selectArticleById(id);
        request.setAttribute("contents", article);
        request.setAttribute("categories", article.getCategories());
        return "back/article_edit";
    }

    @PostMapping("/article/modify")
    @ResponseBody
    public ResponseData modifyArticle(Article article) {
        try {
            articleService.updateArticleById(article);
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
