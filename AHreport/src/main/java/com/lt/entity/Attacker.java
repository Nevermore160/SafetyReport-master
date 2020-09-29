package com.lt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/18 10:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attacker {
    private int id;
    private String srcAddress;
    private String srcGeoCountry;
    private String srcGeoAddress;
    private String eventCount;
    private String category;
    private String startTime;
    private String threatSeverity;
    private String killChain;
}
