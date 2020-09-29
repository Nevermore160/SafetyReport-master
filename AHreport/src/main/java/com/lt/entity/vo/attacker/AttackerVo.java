package com.lt.entity.vo.attacker;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/18 11:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttackerVo {
    //国家
    private String name;
    //热度
    private int value;
}
