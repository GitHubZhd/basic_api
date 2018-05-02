package com.toefl.basic.dao;

import com.toefl.basic.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao extends CommonService<User> {

    @Override
    @Select("select * from t_common_user where username=#{username}")
    User queryDict(User user);
}
