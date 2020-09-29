package com.lt.entity.vo.victim;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/17 16:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VictimCategoryVo {
    //国家
    private String name;
    //告警子类型
    private String value;
}
