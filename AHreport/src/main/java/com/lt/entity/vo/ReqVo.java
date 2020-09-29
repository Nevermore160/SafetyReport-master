package com.lt.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/22 13:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqVo {
    private String startTime;
    private String endTime;
}
