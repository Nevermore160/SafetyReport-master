package com.lt.entity.vo.victim;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/17 15:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VictimVo {
    //国家
    private String name;
    //热度
    private int value;
}
