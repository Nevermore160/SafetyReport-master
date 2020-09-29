package com.lt.entity.vo.attacker;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/18 11:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttackerVo1 {
    //总数
    private int sum;
    //国家
    private String srcGeoCountry;
    //威胁等级
    private String threatSeverity;
}
