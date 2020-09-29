package com.lt.mapper;


import com.lt.entity.vo.ReqVo;
import com.lt.entity.vo.asset.RiskAssetVo4;
import com.lt.entity.vo.asset.RiskAssetVo5;
import com.lt.entity.vo.asset.RiskAssetVo6;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/11 20:55
 */
public interface RiskAssetsMapper {

    List<RiskAssetVo6> getSecurityDomainNum(@Param("reqVo") ReqVo reqVo);//获取安全域攻击次数

    List<RiskAssetVo5> getRiskRatingNum(@Param("reqVo") ReqVo reqVo);//风险等级

    List<RiskAssetVo4> getLatestExceptionTime(@Param("reqVo") ReqVo reqVo);//最近攻击时间

}
