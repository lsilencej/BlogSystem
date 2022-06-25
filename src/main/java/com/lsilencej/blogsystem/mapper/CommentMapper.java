package com.lsilencej.blogsystem.mapper;

import com.lsilencej.blogsystem.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKeyWithBLOBs(Comment record);

    int updateByPrimaryKey(Comment record);

    List<Comment> selectByArticleId(Integer articleId);

    List<Comment> getAllComments();

    int countAllComments();

    void deleteByArticleId(Integer articleId);
}