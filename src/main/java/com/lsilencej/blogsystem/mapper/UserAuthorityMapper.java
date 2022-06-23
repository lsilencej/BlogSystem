package com.lsilencej.blogsystem.mapper;

import com.lsilencej.blogsystem.entity.UserAuthority;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAuthorityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserAuthority record);

    int insertSelective(UserAuthority record);

    UserAuthority selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserAuthority record);

    int updateByPrimaryKey(UserAuthority record);
}