package com.taotao.common.pojo;

import java.io.Serializable;

/**
 * Created by 蒋志鹏 on 2018/7/4.
 */
public class SolrItems implements Serializable{
    private String id;

    private String title;

    private String sellPoint;

    private Long price;

    private String image;

    private String name;

    private String itemDesc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSellPoint() {
        return sellPoint;
    }

    public void setSellPoint(String sellPoint) {
        this.sellPoint = sellPoint;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String catagoryName) {
        this.name = catagoryName;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String[] getImages(){
        if(image!=null||!"".equals(image)){
            String[] images = image.split(",");
            return  images;
        }
        return null;
    }

    @Override
    public String toString() {
        return "SolrItems{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", sellPoint='" + sellPoint + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", catagoryName='" + name + '\'' +
                ", itemDesc='" + itemDesc + '\'' +
                '}';
    }
}
