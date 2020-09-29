package com.lt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//受害者
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Victim {
    private int id;  //主键约束，自增id
    private String destGeoCountry;  //受害国家
    private String eventCount;  //事件数量
    private String startTime;  //起始时间
    private String victimSecurityZone;  //受害者安全域
    private String subCategory;  //告警子类型
    private String threatSeverity;  //安全告警等级
}
