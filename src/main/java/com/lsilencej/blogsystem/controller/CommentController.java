package com.lsilencej.blogsystem.controller;

import com.lsilencej.blogsystem.entity.Comment;
import com.lsilencej.blogsystem.entity.User;
import com.lsilencej.blogsystem.service.CommentService;
import com.lsilencej.blogsystem.utils.MyUtils;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author ：lsilencej
 * @date ：Created in 2022/6/22 15:51
 * @description：
 * @modified By：
 * @version: $
 */
@Controller
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/add")
    @ResponseBody
    public ResponseData addComment(HttpServletRequest request, @RequestParam int articleId, @RequestParam String text) {
        text = MyUtils.cleanXSS(text);
        text = EmojiParser.parseToAliases(text);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment comment = new Comment();
        comment.setArticleId(articleId);
        comment.setAuthor(user.getUsername());
        comment.setContent(text);
        comment.setCreated(new Date());
        comment.setStatus("approved");
        comment.setIp(request.getRemoteAddr());

        try {
            commentService.addComment(comment);
            return ResponseData.ok();
        } catch (Exception e) {
            return ResponseData.fail();
        }
    }

}
