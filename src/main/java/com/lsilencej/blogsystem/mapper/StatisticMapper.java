package com.lsilencej.blogsystem.mapper;

import com.lsilencej.blogsystem.entity.Article;
import com.lsilencej.blogsystem.entity.Statistic;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StatisticMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Statistic record);

    int insertSelective(Statistic record);

    Statistic selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Statistic record);

    int updateByPrimaryKey(Statistic record);

    int getTotalHitsNum();

    int getTotalCommentsNum();

    List<Statistic> getOrderByHitsComments();

    Statistic selectByArticleId(Integer articleId);

    void updateArticleHits(Statistic statistic);

    void updateArticleComments(Statistic statistic);

    void addStatistic(Article article);

    void deleteByArticleId(Integer articleId);
}