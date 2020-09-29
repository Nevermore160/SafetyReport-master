package com.lt.entity.vo;

import com.lt.entity.vo.attacker.AttackerVo5;
import com.lt.entity.vo.attacker.AttackerVo6;
import com.lt.entity.vo.victim.*;
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
public class RespAttackerVo<T> {
    //返回国家、告警类型、热度
    private List<List<T>> maps = new ArrayList<>();

    //返回前五个国家和报警子类型
    AttackerVo5 topAttacker;

    //最新的前10条记录
    AttackerVo6 latestAttacker;
}
