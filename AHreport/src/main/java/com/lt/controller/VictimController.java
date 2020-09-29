package com.lt.controller;

import com.lt.entity.vo.ReqVo;
import com.lt.entity.vo.RespSuggestionVo;
import com.lt.entity.vo.RespVictimVo;
import com.lt.service.VictimService;
import com.lt.utils.DataResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 受害者分析Controller
 *
 * @author teng.li
 * @version 1.0
 * @date 2020/8/18 9:05
 */
@RestController
@RequestMapping("/Victim")
@CrossOrigin
public class VictimController {

    @Autowired
    private VictimService victimService;

    /**
     * 返回区域、热度、告警类型之间的关系
     *
     * @return
     */
    @PostMapping("/analysis")
    public DataResultUtil<RespVictimVo> VictimAnalysis(@RequestBody ReqVo reqVo) {

        if (reqVo.getEndTime() == null || reqVo.getStartTime() == null)
            return new DataResultUtil<>(200, "响应成功", victimService.selectAllByCountryName(reqVo));

        if (reqVo.getStartTime().equals("") || reqVo.getEndTime().equals(""))
            return new DataResultUtil<>(5002,"传入参数不能为空",new RespVictimVo());

        RespVictimVo respVictimVo = victimService.selectAllByCountryName(reqVo);
        DataResultUtil<RespVictimVo> result = new DataResultUtil<>(200, "响应成功", respVictimVo);
        return result;
    }
}
