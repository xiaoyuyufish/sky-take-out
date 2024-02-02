package com.sky.mapper;

import com.sky.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User getByOpenId(String openid);

    void insert(User user);
}
