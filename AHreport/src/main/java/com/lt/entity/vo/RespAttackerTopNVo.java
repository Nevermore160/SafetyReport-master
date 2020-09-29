package com.lt.entity.vo;

import com.lt.entity.vo.top.TopVo1;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/19 10:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespAttackerTopNVo {
    List<TopVo1> topN;
}
