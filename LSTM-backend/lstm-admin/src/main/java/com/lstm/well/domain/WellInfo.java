package com.lstm.well.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.lstm.common.annotation.Excel;
import com.lstm.common.core.domain.BaseEntity;

/**
 * 井详细信息管理对象 well_info
 * 
 * @author zhanghaobo
 * @date 2025-01-14
 */
public class WellInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String wellId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String date;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private BigDecimal waterCutSc;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private BigDecimal oilCutSc;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private BigDecimal cumulativeOilSc;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setWellId(String wellId) 
    {
        this.wellId = wellId;
    }

    public String getWellId() 
    {
        return wellId;
    }
    public void setDate(String date) 
    {
        this.date = date;
    }

    public String getDate() 
    {
        return date;
    }
    public void setWaterCutSc(BigDecimal waterCutSc) 
    {
        this.waterCutSc = waterCutSc;
    }

    public BigDecimal getWaterCutSc() 
    {
        return waterCutSc;
    }
    public void setOilCutSc(BigDecimal oilCutSc) 
    {
        this.oilCutSc = oilCutSc;
    }

    public BigDecimal getOilCutSc() 
    {
        return oilCutSc;
    }
    public void setCumulativeOilSc(BigDecimal cumulativeOilSc) 
    {
        this.cumulativeOilSc = cumulativeOilSc;
    }

    public BigDecimal getCumulativeOilSc() 
    {
        return cumulativeOilSc;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("wellId", getWellId())
            .append("date", getDate())
            .append("waterCutSc", getWaterCutSc())
            .append("oilCutSc", getOilCutSc())
            .append("cumulativeOilSc", getCumulativeOilSc())
            .toString();
    }
}
