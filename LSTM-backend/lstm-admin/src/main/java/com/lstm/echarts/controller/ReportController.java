package com.lstm.echarts.controller;


import com.lstm.common.core.domain.AjaxResult;
import com.lstm.common.core.domain.R;
import com.lstm.echarts.domain.UserStatistics;
import com.lstm.echarts.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Api("ECharts")
@RestController
@RequestMapping("/api/report")
public class ReportController {


    @Autowired
    private UserService userService;

    @ApiOperation("获取图表信息")
    @GetMapping("/userStatistics")
    public R<List<UserStatistics>> getUserStatistics() {
        List<UserStatistics> stats = userService.getUserStatistics();
        return R.ok(stats);
    }

}
