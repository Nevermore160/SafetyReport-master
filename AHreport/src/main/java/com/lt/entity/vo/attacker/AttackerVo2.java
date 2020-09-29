package com.lt.entity.vo.attacker;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/18 16:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttackerVo2 {
    //总数
    private int sum;
    //国家
    private String srcGeoCountry;
    //攻击链
    private String killChain;
}
