package com.lt.entity.vo.asset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/20 14:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiskAssetVo7 implements Comparable<RiskAssetVo7>{

    private String name;
    private int sum;

    public int compareTo(RiskAssetVo7 o) {
        return this.sum - o.sum;
    }
}
