package com.lsilencej.blog_system.controller;

import com.lsilencej.blog_system.entity.Comment;
import com.lsilencej.blog_system.entity.User;
import com.lsilencej.blog_system.service.ICommentService;
import com.lsilencej.blog_system.utils.MyUtils;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author ：lsilencej
 * @date ：Created in 2021/12/3 18:13
 * @description：
 * @modified By：
 * @version: $
 */
@Controller
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private ICommentService commentService;

    @PostMapping("/publish")
    @ResponseBody
    public ResponseData pushComment(HttpServletRequest request, @RequestParam Integer aid, @RequestParam String text) {
        text = MyUtils.cleanXSS(text);
        text = EmojiParser.parseToAliases(text);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment comment = new Comment();
        comment.setArticleId(aid);
        comment.setAuthor(user.getUsername());
        comment.setContent(text);
        comment.setCreated(new Date());
        comment.setStatus("approved");
        comment.setIp(request.getRemoteAddr());
        try {
            commentService.pushComment(comment);
            return ResponseData.ok();
        } catch (Exception e) {
            return ResponseData.fail();
        }
    }
}
