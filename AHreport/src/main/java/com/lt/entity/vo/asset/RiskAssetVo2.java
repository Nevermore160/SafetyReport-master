package com.lt.entity.vo.asset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/19 18:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiskAssetVo2<T> {
    List<T> list;
}
