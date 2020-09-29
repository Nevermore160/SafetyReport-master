package com.lt.entity.vo.attacker;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/18 16:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttackerVo6<T> {
    private String[] header = new String[]{"攻击国家", "攻击链路", "威胁等级", "开始时间"};

    private List<List<T>> data = new ArrayList<>();
}
