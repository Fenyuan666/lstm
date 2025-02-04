package com.lstm.echarts.service.impl;

import com.lstm.echarts.domain.UserStatistics;
import com.lstm.echarts.mapper.UserMapper;
import com.lstm.echarts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    public List<UserStatistics> getUserStatistics() {
        return userMapper.selectUserStatistics();
    }

}
