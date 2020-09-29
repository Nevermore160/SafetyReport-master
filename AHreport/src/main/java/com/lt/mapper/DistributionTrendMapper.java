package com.lt.mapper;

import com.lt.entity.vo.ReqVo;
import com.lt.entity.vo.dt.DTVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/16 16:46
 */
public interface DistributionTrendMapper {
    //根据告警类型查询出时间与数量关系
    List<DTVo> selectAllByThreatSeverity(@Param("severity") String severity, @Param("reqVo")ReqVo reqVo);

    //查询出所有时间节点，分钟
    List<String> selectAllTimeNode(@Param("reqVo") ReqVo reqVo);
}
