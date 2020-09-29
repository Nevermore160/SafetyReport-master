package com.lt.service;

import com.lt.entity.vo.ReqVo;
import com.lt.entity.vo.RespSuggestionVo;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/19 16:24
 */
public interface SuggestionService {
    RespSuggestionVo selectSuggestion(ReqVo reqVo);
}
