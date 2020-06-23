package com.luotao.enums;

/**
 * @Desc: 商品评价等级 枚举,数量的东西尽量用枚举来规范化
 */
public enum CommentLevel {
    GOOD(1, "好评"),
    NORMAL(2, "中评"),
    BAD(3, "差评");

    public final Integer type;
    public final String value;

    CommentLevel(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
