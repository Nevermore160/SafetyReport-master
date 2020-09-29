package com.lt.mapper;

import com.lt.entity.vo.ReqVo;
import com.lt.entity.vo.attacker.AttackerVo1;
import com.lt.entity.vo.attacker.AttackerVo2;
import com.lt.entity.vo.attacker.AttackerVo3;
import com.lt.entity.vo.attacker.AttackerVo4;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/18 10:28
 */
public interface AttackerMapper {

    //查询国家，总数，威胁等级
    List<AttackerVo1> selectCountrySumSeverity(@Param("reqVo") ReqVo reqVo);

    //查询国家，攻击链
    List<AttackerVo2> selectCountryKillChain(@Param("reqVo")ReqVo reqVo);

    //排名前五的
    List<AttackerVo3> selectCountryKillChainSum(@Param("reqVo")ReqVo reqVo);

    //最新攻击的前10条记录
    List<AttackerVo4> selectLatestCountry(@Param("reqVo")ReqVo reqVo);
}
