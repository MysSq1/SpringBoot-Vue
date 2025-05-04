package cn.smxy.newsserver.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer code;
    private String msg;
    private Object data;

//    success()：返回一个默认的成功结果，不包含附加数据。
//    success(Object data)：返回一个成功的结果，并携带数据 data。
//    success(String msg, Object data)：返回一个成功的结果，并携带自定义消息 msg 和数据 data。
    public static Result success(){
        return new Result(Code.SUCCESS,"请求成功",null);
    }
    public static Result success(Object data){
        return new Result(Code.SUCCESS,"请求成功",data);
    }
    public static Result success(String msg,Object data){
        return new Result(Code.SUCCESS,msg,data);
    }

    public static Result error(){
        return new Result(Code.ERROR,"请求失败",null);
    }
    public static Result error(String msg){
        return new Result(Code.ERROR,msg,null);
    }
    public static Result error(Integer code, String msg){
        return new Result(Code.ERROR,msg,null);
    }



}
