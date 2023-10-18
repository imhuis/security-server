package com.imhuis.securityserver.common.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imhuis.securityserver.common.enums.ResponseCodeEnum;
import com.imhuis.securityserver.common.util.JsonTools;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * @author: zyixh
 * @date: 2020/1/28
 * @description:
 */
@Slf4j
public class ResponseUtil {

    /**
     *
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> success(){
        return build(ResponseCodeEnum.SUCCESS);
    }

    /**
     * 成功
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> success(T data){
        return build(ResponseCodeEnum.SUCCESS, data);
    }

    public static ResponseResult fail(ResponseCodeEnum responseCodeEnum){
        return build(responseCodeEnum).setSuccess(false);
    }

    /**
     * 失败
     * @param responseCodeEnum
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> fail(ResponseCodeEnum responseCodeEnum, T data){
        return build(responseCodeEnum, data).setSuccess(false);
    }

    public static ResponseResult define(Integer code, String message){
        return build(code, message);
    }

    /**
     * 自定义异常码
     * @param code
     * @param message
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> define(Integer code, String message, T data){
        return build(code, message, data);
    }

    /**
     *
     * @param code
     * @param message
     * @param <T>
     * @return
     */
    private static <T> ResponseResult<T> build(Integer code, String message){
        ResponseResult<T> result = new ResponseResult(code, message);
        return result;
    }

    /**
     *
     * @param code
     * @param message
     * @param data
     * @param <T>
     * @return
     */
    private static <T> ResponseResult<T> build(Integer code, String message, T data){
        ResponseResult<T> result = new ResponseResult(code, message, data);
        return result;
    }

    /**
     *
     * @param httpStatus
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> build(HttpStatus httpStatus){
        ResponseResult<T> result = new ResponseResult(httpStatus);
        return result;
    }

    /**
     *
     * @param codeEnum
     * @param <T>
     * @return
     */
    private static <T> ResponseResult<T> build(ResponseCodeEnum codeEnum){
        ResponseResult<T> result = new ResponseResult(codeEnum);
        return result;
    }

    private static <T> ResponseResult<T> build(ResponseCodeEnum codeEnum, T data){
        ResponseResult<T> result = new ResponseResult(codeEnum, data);
        return result;
    }

    /**
     * response string.
     */
    public static void responseString(HttpServletResponse response, String str) {
        ResponseResult baseResponse = new ResponseResult(ResponseCodeEnum.SYSTEM_EXCEPTION);
        if (StringUtils.isNotBlank(str)) {
            baseResponse.setMessage(str);
        }

//        ResponseResult retCode;
//        if (JsonTools.isJson(str) && (retCode = JsonTools.toJavaObject(str, ResponseResult.class)) != null) {
//            baseResponse = new ResponseResult(retCode);
//        }

        try {
            response.getWriter().write(JsonTools.toJSONString(baseResponse));
        } catch (IOException e) {
            log.error("fail responseRetCodeException", e);
        }
    }

    public static void out(HttpServletResponse response, ResponseResult responseResult) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        try {
            response.getWriter().write(new ObjectMapper().writeValueAsString(responseResult));
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){

        }
    }

    public static void out(HttpServletRequest request, HttpServletResponse response, BufferedInputStream in) {
        response.setContentType(request.getContentType());
        try {
            response.getOutputStream().write(in.readAllBytes());
            response.getWriter().flush();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){

        }finally {

        }
    }
}
