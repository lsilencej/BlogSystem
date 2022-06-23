package com.lsilencej.blogsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lsilencej.blogsystem.entity.Comment;
import com.lsilencej.blogsystem.entity.Statistic;
import com.lsilencej.blogsystem.mapper.CommentMapper;
import com.lsilencej.blogsystem.mapper.StatisticMapper;
import com.lsilencej.blogsystem.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：lsilencej
 * @date ：Created in 2022/6/22 15:17
 * @description：
 * @modified By：
 * @version: $
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private StatisticMapper statisticMapper;

    @Override
    public PageInfo<Comment> getComments(int articleId, int page, int count) {
        PageHelper.startPage(page, count);
        List<Comment> comments = commentMapper.selectByArticleId(articleId);
        PageInfo<Comment> commentPageInfo = new PageInfo<>(comments);
        return commentPageInfo;
    }

    @Override
    public void addComment(Comment comment) {
        commentMapper.insert(comment);
        Statistic statistic = statisticMapper.selectByArticleId(comment.getArticleId());
        statistic.setCommentsNum(statistic.getCommentsNum() + 1);
        statisticMapper.updateArticleComments(statistic);
    }
}
