package cn.zzzyuan.common.contant;

public enum  ResultContant {
    LOGIN_SUCCESS(200,"登录成功"),
    LOGIN_FAIL(400,"登录失败"),
    NAME_OR_PASSWORD_FAIL(401,"用户名或者密码错误"),
    ADD_CATEGORY_SUCCESS(201,"添加分类成功"),
    ADD_CATEGORY_FAIL(410,"添加分类失败"),
    SUCCESS(250,"操作成功"),
    FAIL(488,"操作失败");

    private int code;
    private String msg;

    ResultContant(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int code() {
        return code;
    }

    public String msg() {
        return msg;
    }
}
