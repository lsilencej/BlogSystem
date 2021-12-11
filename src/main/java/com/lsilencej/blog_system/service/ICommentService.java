package com.lsilencej.blog_system.service;

import com.github.pagehelper.PageInfo;
import com.lsilencej.blog_system.entity.Comment;

/**
 * @author ：lsilencej
 * @date ：Created in 2021/12/3 13:07
 * @description：
 * @modified By：
 * @version: $
 */
public interface ICommentService {
    public PageInfo<Comment> getComment(Integer aid, int page, int count);

    public void pushComment(Comment comment);
}
