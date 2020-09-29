package com.lt.entity.vo.victim;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/17 17:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VictimVo1 {
    //总数
    private int sum;
    //国家
    private String destGeoCountry;
    //类型
    private String threatSeverity;
}
