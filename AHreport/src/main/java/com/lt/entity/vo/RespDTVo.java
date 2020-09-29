package com.lt.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/16 20:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespDTVo {
    private List<String> list;
    private int[] low;
    private int[] medium;
    private int[] high;
}
