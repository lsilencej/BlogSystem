package com.lsilencej.blog_system.mapper;

import com.lsilencej.blog_system.entity.Article;
import com.lsilencej.blog_system.entity.Statistic;
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

    long getTotalHit();

    long getTotalComment();

    List<Statistic> getStatistic();

    int updateArticleHits(Statistic statistic);

    int updateArticleComments(Statistic statistic);

    void addStatistic(Article article);
}