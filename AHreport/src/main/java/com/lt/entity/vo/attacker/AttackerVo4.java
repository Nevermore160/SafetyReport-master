package com.lt.entity.vo.attacker;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/18 16:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttackerVo4 {
    @Excel(name = "攻击国家")
    private String srcGeoCountry;
    @Excel(name = "攻击链路")
    private String killChain;
    @Excel(name = "威胁等级")
    private String threatSeverity;
    @Excel(name = "开始时间")
    private String startTime;
}
