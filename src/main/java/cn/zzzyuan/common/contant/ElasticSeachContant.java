package cn.zzzyuan.common.contant;

public enum ElasticSeachContant {

    BLOGS_INDEX("blogs","ES中储存文章搜索的索引"),
    BEG_STR("<span class = 'highlight'>","高亮前缀"),
    END_STR("</span>","高亮后缀");

    private String code;
    private String msg;

    ElasticSeachContant(String code, String msg) {
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
