package com.lstm.echarts.service;

import com.lstm.echarts.domain.UserStatistics;

import java.util.List;

public interface UserService {
    public List<UserStatistics> getUserStatistics();
}
