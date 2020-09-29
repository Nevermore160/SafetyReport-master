package com.lt.entity.vo.asset;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/19 19:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiskAssetVo4 {
    @Excel(name = "安全域")
    private String securityDomain;
    @Excel(name = "资产名称")
    private String assetName;
    @Excel(name = "风险评级")
    private String riskRating;
    @Excel(name = "最近异常发生时间")
    private String latestExceptionTime;
}
