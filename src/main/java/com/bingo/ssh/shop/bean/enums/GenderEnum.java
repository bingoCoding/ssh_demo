package com.bingo.ssh.shop.bean.enums;

/**
 * Created by 26685 on 2017/7/5.
 */
public enum GenderEnum {
    Man {
        public String getName() {
            return "男";
        }
    },
    WOMAN {
        public String getName() {
            return "女";
        }
    };

    public abstract String getName();
}
