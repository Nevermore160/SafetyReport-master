package com.lt.entity.vo.victim;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/18 13:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VictimVo3 {
    @Excel(name="受害国家")
    private String destGeoCountry;  //受害国家
    @Excel(name="威胁等级")
    private String threatSeverity;  //安全告警等级
    @Excel(name="告警子类型")
    private String subCategory;  //告警子类型
    @Excel(name="开始时间")
    private String startTime;  //起始时间
}
