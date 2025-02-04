package com.lstm.well.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lstm.well.mapper.WellInfoMapper;
import com.lstm.well.domain.WellInfo;
import com.lstm.well.service.IWellInfoService;

/**
 * 井详细信息管理Service业务层处理
 * 
 * @author zhanghaobo
 * @date 2025-01-14
 */
@Service
public class WellInfoServiceImpl implements IWellInfoService 
{
    @Autowired
    private WellInfoMapper wellInfoMapper;

    /**
     * 查询井详细信息管理
     * 
     * @param id 井详细信息管理主键
     * @return 井详细信息管理
     */
    @Override
    public WellInfo selectWellInfoById(Long id)
    {
        return wellInfoMapper.selectWellInfoById(id);
    }

    /**
     * 查询井详细信息管理列表
     * 
     * @param wellInfo 井详细信息管理
     * @return 井详细信息管理
     */
    @Override
    public List<WellInfo> selectWellInfoList(WellInfo wellInfo)
    {
        return wellInfoMapper.selectWellInfoList(wellInfo);
    }

    /**
     * 新增井详细信息管理
     * 
     * @param wellInfo 井详细信息管理
     * @return 结果
     */
    @Override
    public int insertWellInfo(WellInfo wellInfo)
    {
        return wellInfoMapper.insertWellInfo(wellInfo);
    }

    /**
     * 修改井详细信息管理
     * 
     * @param wellInfo 井详细信息管理
     * @return 结果
     */
    @Override
    public int updateWellInfo(WellInfo wellInfo)
    {
        return wellInfoMapper.updateWellInfo(wellInfo);
    }

    /**
     * 批量删除井详细信息管理
     * 
     * @param ids 需要删除的井详细信息管理主键
     * @return 结果
     */
    @Override
    public int deleteWellInfoByIds(Long[] ids)
    {
        return wellInfoMapper.deleteWellInfoByIds(ids);
    }

    /**
     * 删除井详细信息管理信息
     * 
     * @param id 井详细信息管理主键
     * @return 结果
     */
    @Override
    public int deleteWellInfoById(Long id)
    {
        return wellInfoMapper.deleteWellInfoById(id);
    }
}
