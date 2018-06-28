package com.taotao.common.pojo;

import java.io.Serializable;

public class TaotaoResult implements Serializable{
	//响应业务的状态
	private Integer status;
	
	//响应消息
	private String msg;
	//相应数据
	private Object data;
	
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public TaotaoResult(Integer status, String msg, Object data) {
		super();
		this.status = status;
		this.msg = msg;
		this.data = data;
	}
	
	public TaotaoResult(Integer status, String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}

	public TaotaoResult(Object data) {
		super();
		this.status=200;
		this.msg="成功";
		this.data = data;
	}

	public TaotaoResult() {
		super();
	}
	
	
	public static TaotaoResult bulid(Integer status, String msg, Object data){
		return new TaotaoResult(status, msg, data);
	}
	
	public static TaotaoResult bulid(Integer status, String msg){
		return new TaotaoResult(status, msg);
	}
	
	public static TaotaoResult bulid(Object data){
		return new TaotaoResult(data);
	}
	
	
	public static TaotaoResult bulid(){
		return new TaotaoResult(null);
	}
	
}
