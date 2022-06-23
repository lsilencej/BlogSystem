package com.lsilencej.blogsystem.service;

import com.github.pagehelper.PageInfo;
import com.lsilencej.blogsystem.entity.Comment;

/**
 * @author ：lsilencej
 * @date ：Created in 2022/6/22 15:15
 * @description：
 * @modified By：
 * @version: $
 */
public interface CommentService {

    PageInfo<Comment> getComments(int articleId, int page, int count);

    void addComment(Comment comment);

}
