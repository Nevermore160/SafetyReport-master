package com.lt.entity.vo.asset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/19 18:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiskAssetVo3 {
    private int[] data = new int[5];
    private String[] titleName = new String[5];
    private int[] valData = {78, 78, 78, 78, 78};
}
