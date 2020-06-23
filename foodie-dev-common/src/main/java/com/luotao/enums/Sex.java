package com.luotao.enums;

/**
 * @author : luo
 * @date : 2020/3/15 14:22
 */

/**
 * @desc:性别 枚举
 */
public enum Sex {
    woman(0,"女"),
    man(1,"男"),
    secret(2,"保密");
    public final Integer type;
    public final String value;

    Sex(Integer type,String value){
        this.type=type;
        this.value=value;
    }
}
