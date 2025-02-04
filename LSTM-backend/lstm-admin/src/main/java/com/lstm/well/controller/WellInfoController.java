package com.lstm.well.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lstm.common.annotation.Log;
import com.lstm.common.core.controller.BaseController;
import com.lstm.common.core.domain.AjaxResult;
import com.lstm.common.enums.BusinessType;
import com.lstm.well.domain.WellInfo;
import com.lstm.well.service.IWellInfoService;
import com.lstm.common.utils.poi.ExcelUtil;
import com.lstm.common.core.page.TableDataInfo;

/**
 * 井详细信息管理Controller
 * 
 * @author zhanghaobo
 * @date 2025-01-14
 */
@RestController
@RequestMapping("/well_info/info")
public class WellInfoController extends BaseController
{
    @Autowired
    private IWellInfoService wellInfoService;

    /**
     * 查询井详细信息管理列表
     */
    @PreAuthorize("@ss.hasPermi('well_info:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(WellInfo wellInfo)
    {
        startPage();
        List<WellInfo> list = wellInfoService.selectWellInfoList(wellInfo);
        return getDataTable(list);
    }

    /**
     * 导出井详细信息管理列表
     */
    @PreAuthorize("@ss.hasPermi('well_info:info:export')")
    @Log(title = "井详细信息管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WellInfo wellInfo)
    {
        List<WellInfo> list = wellInfoService.selectWellInfoList(wellInfo);
        ExcelUtil<WellInfo> util = new ExcelUtil<WellInfo>(WellInfo.class);
        util.exportExcel(response, list, "井详细信息管理数据");
    }

    /**
     * 获取井详细信息管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('well_info:info:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wellInfoService.selectWellInfoById(id));
    }

    /**
     * 新增井详细信息管理
     */
    @PreAuthorize("@ss.hasPermi('well_info:info:add')")
    @Log(title = "井详细信息管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WellInfo wellInfo)
    {
        return toAjax(wellInfoService.insertWellInfo(wellInfo));
    }

    /**
     * 修改井详细信息管理
     */
    @PreAuthorize("@ss.hasPermi('well_info:info:edit')")
    @Log(title = "井详细信息管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WellInfo wellInfo)
    {
        return toAjax(wellInfoService.updateWellInfo(wellInfo));
    }

    /**
     * 删除井详细信息管理
     */
    @PreAuthorize("@ss.hasPermi('well_info:info:remove')")
    @Log(title = "井详细信息管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wellInfoService.deleteWellInfoByIds(ids));
    }
}
