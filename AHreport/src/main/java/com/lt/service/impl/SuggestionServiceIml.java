package com.lt.service.impl;

import com.lt.entity.vo.ReqVo;
import com.lt.entity.vo.RespSuggestionVo;
import com.lt.entity.vo.suggestions.SuggestionVo1;
import com.lt.mapper.DisposalSuggestionMapper;
import com.lt.service.SuggestionService;
import com.lt.utils.IsMatchUtil;
import com.lt.utils.ResultMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/19 16:28
 */
@Service
public class SuggestionServiceIml implements SuggestionService {
    @Autowired
    private DisposalSuggestionMapper disposalSuggestionMapper;

    @Override
    public RespSuggestionVo selectSuggestion(ReqVo reqVo) {

        List<SuggestionVo1> suggestionVo1s = disposalSuggestionMapper.selectSuggestion(reqVo);
        for (SuggestionVo1 suggestionVo1 : suggestionVo1s) {
            suggestionVo1.setSuggestion(IsMatchUtil.isMatch(suggestionVo1.getSuggestion()));
        }

        ResultMapUtil.method("suggestionVo1s",suggestionVo1s);

        return new RespSuggestionVo(suggestionVo1s);
    }
}
