package com.lsilencej.blog_system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lsilencej.blog_system.entity.Comment;
import com.lsilencej.blog_system.entity.Statistic;
import com.lsilencej.blog_system.mapper.CommentMapper;
import com.lsilencej.blog_system.mapper.StatisticMapper;
import com.lsilencej.blog_system.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：lsilencej
 * @date ：Created in 2021/12/3 13:07
 * @description：
 * @modified By：
 * @version: $
 */
@Service
public class CommentServiceImpl implements ICommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private StatisticMapper statisticMapper;

    @Override
    public PageInfo<Comment> getComment(Integer aid, int page, int count) {
        PageHelper.startPage(page, count);
        List<Comment> comments = commentMapper.selectByAid(aid);
        PageInfo<Comment> commentPageInfo = new PageInfo<>(comments);
        return commentPageInfo;
    }

    @Override
    public void pushComment(Comment comment) {
        commentMapper.insert(comment);
        Statistic statistic = statisticMapper.selectByPrimaryKey(comment.getArticleId());
        statistic.setCommentsNum(statistic.getCommentsNum() + 1);
        statisticMapper.updateArticleComments(statistic);
    }
}
