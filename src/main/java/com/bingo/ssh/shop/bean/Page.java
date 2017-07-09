package com.bingo.ssh.shop.bean;

import java.util.List;

/**
 * Created by 26685 on 2017/7/5.
 */
public class Page<T> {
    private List<T> result;
    private long acount;

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public long getAcount() {
        return acount;
    }

    public void setAcount(long acount) {
        this.acount = acount;
    }
}
