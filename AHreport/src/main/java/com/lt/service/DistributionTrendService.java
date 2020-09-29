package com.lt.service;

import com.lt.entity.vo.ReqVo;
import com.lt.entity.vo.RespDTVo;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/16 17:13
 */
public interface DistributionTrendService {

    RespDTVo selectAllByThreatSeverity(ReqVo reqVo);
}
