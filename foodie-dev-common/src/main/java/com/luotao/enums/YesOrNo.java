package com.luotao.enums;

/**
 * @author : luo
 * @date : 2020/3/15 14:22
 */

/**
 * @desc:是否 枚举
 */
public enum YesOrNo {
    NO(0,"否"),
    YES(1,"是");
    public final Integer type;
    public final String value;

    YesOrNo(Integer type, String value){
        this.type=type;
        this.value=value;
    }
}
