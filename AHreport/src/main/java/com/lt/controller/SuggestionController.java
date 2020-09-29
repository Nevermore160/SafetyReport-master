package com.lt.controller;

import com.lt.entity.vo.ReqVo;
import com.lt.entity.vo.RespAssetVo;
import com.lt.entity.vo.RespSuggestionVo;
import com.lt.service.SuggestionService;
import com.lt.utils.DataResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 整体建议Controller
 *
 * @author teng.li
 * @version 1.0
 * @date 2020/8/19 16:34
 */
@RestController
@RequestMapping("/suggestion")
@CrossOrigin
public class SuggestionController {

    @Autowired
    private SuggestionService suggestionService;

    /**
     * 根据前三的告警子类型返回处置建议
     *
     * @return
     */
    @PostMapping("/all")
    @ResponseBody
    public DataResultUtil<RespSuggestionVo> getSuggestions(@RequestBody ReqVo reqVo) {

        if (reqVo.getEndTime() == null || reqVo.getStartTime() == null)
            return new DataResultUtil<>(200, "响应成功", suggestionService.selectSuggestion(reqVo));

        if (reqVo.getStartTime().equals("") || reqVo.getEndTime().equals(""))
            return new DataResultUtil<>(5002,"传入参数不能为空",new RespSuggestionVo());

        RespSuggestionVo respSuggestionVo = suggestionService.selectSuggestion(reqVo);
        DataResultUtil<RespSuggestionVo> result = new DataResultUtil<>(200, "响应成功", respSuggestionVo);
        return result;
    }
}
