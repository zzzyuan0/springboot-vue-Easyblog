package cn.zzzyuan.common.lang;

import cn.zzzyuan.common.contant.ResultContant;
import lombok.Data;

import java.io.Serializable;

/**
 * 返回格式封装
 * code：返回码 200-300正常 400+不正常
 * msg：返回信息
 * data：返回数据
 *
 * @author 86188
 */
@Data
public class Result implements Serializable {

    private int code;
    private String msg;
    private Object data;

    public static Result fail(String msg){
        return succ(ResultContant.FAIL.code(),msg,null);
    }

    public static Result fail(String msg,Object data){
        return succ(ResultContant.FAIL.code(),msg,data);
    }

    public static Result fail(int code,String msg,Object data){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result succ(Object data){
        return succ(ResultContant.SUCCESS.code(),ResultContant.SUCCESS.msg(),data);
    }

    public static Result succ(String msg){
        return succ(ResultContant.SUCCESS.code(),ResultContant.SUCCESS.msg(),msg);
    }

    public static Result succ(int code,Object data){
        return succ(code,"操作成功",data);
    }

    public static Result succ(int code,String msg,Object data){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}
