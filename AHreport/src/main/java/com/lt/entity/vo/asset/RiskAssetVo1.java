package com.lt.entity.vo.asset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/19 18:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiskAssetVo1<T> {

    private String [] header={"安全域","资产名称","风险评级","最近异常发生时间"};

    private List<List<T>> data;
}
