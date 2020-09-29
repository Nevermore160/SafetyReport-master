package com.lt.service;

import com.lt.entity.vo.ReqVo;
import com.lt.entity.vo.RespAttackerTopNVo;
import com.lt.entity.vo.RespAttackerVo;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/19 12:49
 */
public interface AttackerTopService {

    RespAttackerTopNVo selectAttackerTopN(ReqVo reqVo);
}
