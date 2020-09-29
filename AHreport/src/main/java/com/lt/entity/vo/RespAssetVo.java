package com.lt.entity.vo;

import com.lt.entity.vo.asset.RiskAssetVo1;
import com.lt.entity.vo.asset.RiskAssetVo2;
import com.lt.entity.vo.asset.RiskAssetVo3;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/19 19:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespAssetVo {
    RiskAssetVo1 riskAssetVo1;
    RiskAssetVo2 riskAssetVo2;
    RiskAssetVo3 riskAssetVo3;
}
