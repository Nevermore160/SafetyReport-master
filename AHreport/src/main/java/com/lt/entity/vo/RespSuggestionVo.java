package com.lt.entity.vo;

import com.lt.entity.vo.suggestions.SuggestionVo1;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/19 16:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespSuggestionVo {
    List<SuggestionVo1> suggestionVo1s;
}
