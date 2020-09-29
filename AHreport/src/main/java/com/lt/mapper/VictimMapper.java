package com.lt.mapper;

import com.lt.entity.vo.ReqVo;
import com.lt.entity.vo.victim.VictimVo1;
import com.lt.entity.vo.victim.VictimVo2;
import com.lt.entity.vo.victim.VictimVo3;
import com.lt.entity.vo.victim.VictimVo5;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/17 15:03
 */
public interface VictimMapper {
    //查询国家，总数，威胁等级
    List<VictimVo1> selectCountryHeat(@Param("reqVo") ReqVo reqVo);

    //查询国家，报警子类型
    List<VictimVo2> selectCountryCategory(@Param("reqVo") ReqVo reqVo);

    //排名前五的
    List<VictimVo5> selectCountryCategorySum(@Param("reqVo") ReqVo reqVo);

    //最新受害的前10条记录
    List<VictimVo3> selectLatestCountry(@Param("reqVo") ReqVo reqVo);
}
