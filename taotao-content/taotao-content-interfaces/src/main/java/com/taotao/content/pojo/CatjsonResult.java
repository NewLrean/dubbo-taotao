package com.taotao.content.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 蒋志鹏 on 2018/7/16.
 */
public class CatjsonResult implements Serializable{
    private List<?> data;

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CatjsonResult{" +
                "data=" + data +
                '}';
    }
}
