package cn.zzzyuan.common.contant;

/**
 * @author 86188
 */

public enum RedisContant {

    ARTICLE_HEAT("heat","redis中储存分类热度的字段");

    private String code;
    private String msg;

    RedisContant(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String code() {
        return code;
    }

    public String msg() {
        return msg;
    }

}
