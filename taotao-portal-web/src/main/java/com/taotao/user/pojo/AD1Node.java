package com.taotao.user.pojo;

import java.io.Serializable;

/**
 * Created by 蒋志鹏 on 2018/7/2.
 */
public class AD1Node implements Serializable{
    private String srcB;
    private Integer height;
    private String alt;
    private Integer width;
    private String src;
    private Integer widthB;
    private String href;
    private Integer heightB;

    public String getSrcB() {
        return srcB;
    }

    public void setSrcB(String srcB) {
        this.srcB = srcB;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getWidthB() {
        return widthB;
    }

    public void setWidthB(Integer widthB) {
        this.widthB = widthB;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Integer getHeightB() {
        return heightB;
    }

    public void setHeightB(Integer heightB) {
        this.heightB = heightB;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    @Override
    public String toString() {
        return "AD1Node{" +
                "srcB='" + srcB + '\'' +
                ", height=" + height +
                ", alt='" + alt + '\'' +
                ", width=" + width +
                ", src='" + src + '\'' +
                ", widthB=" + widthB +
                ", href='" + href + '\'' +
                ", heightB=" + heightB +
                '}';
    }
}
