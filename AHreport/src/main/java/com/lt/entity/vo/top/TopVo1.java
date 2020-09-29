package com.lt.entity.vo.top;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/19 10:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopVo1 {
    private int[] data = new int[5];
    private String[] titleName = new String[5];
    private int[] valdata = {1214797, 1214797, 1214797, 1214797, 1214797};
}
