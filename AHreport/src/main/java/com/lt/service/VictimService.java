package com.lt.service;

import com.lt.entity.vo.ReqVo;
import com.lt.entity.vo.RespVictimVo;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/17 17:28
 */
public interface VictimService {

    RespVictimVo selectAllByCountryName(ReqVo reqVo);
}
