package com.lt.entity.vo.asset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/19 19:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiskAssetVo6 implements Comparable<RiskAssetVo6>{
    private String riskRating;
    private String name;

    @Override
    public int compareTo(RiskAssetVo6 o) {
        return 0;
    }
}
