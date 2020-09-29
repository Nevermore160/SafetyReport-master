package com.lt.mapper;


import com.lt.entity.DisposalSuggestion;
import com.lt.entity.vo.ReqVo;
import com.lt.entity.vo.suggestions.SuggestionVo1;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/18 10:28
 */

public interface DisposalSuggestionMapper {

    List<SuggestionVo1> selectSuggestion(@Param("reqVo") ReqVo reqVo);
}
