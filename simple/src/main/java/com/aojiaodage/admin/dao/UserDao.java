package com.aojiaodage.admin.dao;

import com.aojiaodage.admin.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao extends BaseMapper<User> {
    User selectByUsernameAndPwd(@Param("username") String username, @Param("password") String password);
}
