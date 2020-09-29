package com.lt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 风险资产类
 * @author teng.li
 * @version 1.0
 * @date 2020/8/19 18:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiskAsset {
    private int id;
    private String AssetName;
    private String AssetIP;//资产ip
    private String SecurityDomain;//安全域
    private String RiskRating;//安全等级
    private String LatestExceptionTime;//最近攻击攻击时间
}
