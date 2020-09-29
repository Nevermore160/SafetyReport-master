package com.lt.entity.vo.victim;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/18 13:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VictimVo4<T> {
    private String[] header = new String[]{"受害国家", "威胁等级", "告警子类型", "开始时间"};

    private List<List<T>> data = new ArrayList<>();
}
