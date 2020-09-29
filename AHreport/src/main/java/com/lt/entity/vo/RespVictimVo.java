package com.lt.entity.vo;

import com.lt.entity.vo.victim.VictimVo4;
import com.lt.entity.vo.victim.VictimVo6;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/17 15:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespVictimVo<T> {

    //返回国家、告警类型、热度
    private ArrayList<List<T>> maps = new ArrayList<>();

    //返回前五个国家和报警子类型
    VictimVo6 topVictim;

    //最新的前10条记录
    VictimVo4 latestVictim;
}
