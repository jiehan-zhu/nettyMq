package com.netty.mq.util;

import java.io.Serializable;

/**
 * 封装返回
 *
 * @author 祝杰汉
 * @date 2024-01-18
 */
public class ReturnT<T> implements Serializable {
	public static final long serialVersionUID = 42L;

	public static final int SUCCESS_CODE = 200;
	public static final int FAIL_CODE = 500;

	public static final ReturnT<String> SUCCESS = new ReturnT<>(null);
	public static final ReturnT<String> FAIL = new ReturnT<>(FAIL_CODE, null);

	private int code;
	private String msg;
	private T data;

	public ReturnT(){}
	public ReturnT(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	public ReturnT(T data) {
		this.code = SUCCESS_CODE;
		this.data = data;
	}

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ReturnT [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}

}
