package com.taotao.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 蒋志鹏 on 2018/7/4.
 */
public class SolrResult implements Serializable{
    private long total;
    private Integer pages;
    private List<SolrItems> items;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public List<SolrItems> getItems() {
        return items;
    }

    public void setItems(List<SolrItems> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "SolrResult{" +
                "total=" + total +
                ", pages=" + pages +
                ", items=" + items +
                '}';
    }
}
