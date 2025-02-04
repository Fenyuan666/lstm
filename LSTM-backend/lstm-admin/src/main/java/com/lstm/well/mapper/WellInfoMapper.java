package com.lstm.well.mapper;

import java.util.List;
import com.lstm.well.domain.WellInfo;

/**
 * 井详细信息管理Mapper接口
 * 
 * @author zhanghaobo
 * @date 2025-01-14
 */
public interface WellInfoMapper 
{
    /**
     * 查询井详细信息管理
     * 
     * @param id 井详细信息管理主键
     * @return 井详细信息管理
     */
    public WellInfo selectWellInfoById(Long id);

    /**
     * 查询井详细信息管理列表
     * 
     * @param wellInfo 井详细信息管理
     * @return 井详细信息管理集合
     */
    public List<WellInfo> selectWellInfoList(WellInfo wellInfo);

    /**
     * 新增井详细信息管理
     * 
     * @param wellInfo 井详细信息管理
     * @return 结果
     */
    public int insertWellInfo(WellInfo wellInfo);

    /**
     * 修改井详细信息管理
     * 
     * @param wellInfo 井详细信息管理
     * @return 结果
     */
    public int updateWellInfo(WellInfo wellInfo);

    /**
     * 删除井详细信息管理
     * 
     * @param id 井详细信息管理主键
     * @return 结果
     */
    public int deleteWellInfoById(Long id);

    /**
     * 批量删除井详细信息管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWellInfoByIds(Long[] ids);
}
