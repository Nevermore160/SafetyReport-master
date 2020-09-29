package com.lt.mapper;

import com.lt.entity.vo.ReqVo;
import com.lt.entity.vo.top.TopVo2;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/19 10:52
 */
public interface AttackerTopMapper {

    List<TopVo2> selectTopByKillChain(@Param("reqVo") ReqVo reqVo);

    List<TopVo2> selectTopBySubCategory(@Param("reqVo") ReqVo reqVo);

    List<TopVo2> selectTopByModelType(@Param("reqVo") ReqVo reqVo);

    List<TopVo2> selectTopByDeviceAST(@Param("reqVo") ReqVo reqVo);
}
