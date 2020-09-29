package com.lt.service;

import com.lt.entity.vo.ReqVo;
import com.lt.entity.vo.RespAttackerVo;
import com.lt.entity.vo.RespVictimVo;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/18 16:49
 */
public interface AttackerService {

    RespAttackerVo selectAllByCountryName(ReqVo reqVo);
}
