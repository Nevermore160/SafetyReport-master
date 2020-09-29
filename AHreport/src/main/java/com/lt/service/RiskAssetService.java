package com.lt.service;

import com.lt.entity.vo.ReqVo;
import com.lt.entity.vo.RespAssetVo;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/11 21:02
 */
public interface RiskAssetService {

    RespAssetVo getRiskAsset(ReqVo reqVo);
}
