package com.example.securitydemo.common.lang;

/**
 * controller响应结果
 * @author wangzg
 * @date 2021/7/5 16:04
 */
public class ResponseResult {
    private int code;
    private String msg;
    private Object data;

    public static ResponseResult succ(Object data) {
        return succ(200, "操作成功", data);
    }

    public static ResponseResult succ(int code, String msg, Object data) {
        ResponseResult r = new ResponseResult();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static ResponseResult fail(String msg) {
        return fail(400, msg, null);
    }

    public static ResponseResult fail(int code, String msg, Object data) {
        ResponseResult r = new ResponseResult();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
