package com.lt.controller;

import com.lt.entity.vo.ReqVo;
import com.lt.entity.vo.RespAttackerTopNVo;
import com.lt.entity.vo.RespAttackerVo;
import com.lt.service.AttackerService;
import com.lt.service.AttackerTopService;
import com.lt.utils.DataResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 攻击者相关Controller
 *
 * @author teng.li
 * @version 1.0
 * @date 2020/8/18 10:22
 */
@RestController
@RequestMapping("/Attacker")
@CrossOrigin
public class AttackerController {

    @Autowired
    private AttackerService attackerService;

    @Autowired
    private AttackerTopService attackerTopService;

    /**
     * 攻击者分析
     *
     * @return
     */
    @PostMapping("/analysis")
    public DataResultUtil<RespAttackerVo> AttackerAnalysis(@RequestBody ReqVo reqVo) {
        if (reqVo.getEndTime() == null || reqVo.getStartTime() == null)
            return new DataResultUtil<>(200, "响应成功", attackerService.selectAllByCountryName(reqVo));

        if (reqVo.getStartTime().equals("") || reqVo.getEndTime().equals(""))
            return new DataResultUtil<>(5002,"传入参数不能为空",new RespAttackerVo());

        RespAttackerVo respAttackerVo = attackerService.selectAllByCountryName(reqVo);
        DataResultUtil<RespAttackerVo> result = new DataResultUtil<>(200, "响应成功", respAttackerVo);
        return result;
    }

    /**
     * 攻击者TopN分析
     *
     * @return
     */
    @PostMapping("/topN")
    public DataResultUtil<RespAttackerTopNVo> topN(@RequestBody ReqVo reqVo) {
        if (reqVo.getEndTime() == null || reqVo.getStartTime() == null)
            return new DataResultUtil<>(200, "响应成功", attackerTopService.selectAttackerTopN(reqVo));

        if (reqVo.getStartTime().equals("") || reqVo.getEndTime().equals(""))
            return new DataResultUtil<>(5002,"传入参数不能为空",new RespAttackerTopNVo());

        RespAttackerTopNVo respAttackerTopNVo = attackerTopService.selectAttackerTopN(reqVo);
        DataResultUtil<RespAttackerTopNVo> result = new DataResultUtil<>(200, "响应成功", respAttackerTopNVo);
        return result;
    }
}
