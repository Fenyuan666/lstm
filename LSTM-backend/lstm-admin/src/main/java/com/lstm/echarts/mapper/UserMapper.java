package com.lstm.echarts.mapper;

import com.lstm.echarts.domain.UserStatistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface UserMapper {
    @Select("SELECT cumulative_oil_sc,`date` FROM well_info ")
    List<UserStatistics> selectUserStatistics();
}
