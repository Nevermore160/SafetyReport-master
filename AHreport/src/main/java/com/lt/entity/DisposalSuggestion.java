package com.lt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/19 15:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisposalSuggestion {
    private int id;
    private String subCategory;
    private String alarmDescription;
    private String suggestion;
}
