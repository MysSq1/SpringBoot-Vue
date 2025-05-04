package cn.smxy.newsserver.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import  cn.smxy.newsserver.common.Code;
import  cn.smxy.newsserver.entity.User;
import  cn.smxy.newsserver.exception.ServiceException;
import  cn.smxy.newsserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        //如果不是映射（请求）到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        if (StrUtil.isBlank(token)) {
            throw new ServiceException(Code.ERROR, "无token，请重新登录");
        }
        //获取token的userId
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new ServiceException(Code.ERROR,"token验证失败，请重新登录");
        }
        User user = userService.getById(userId);
        if(user == null){
            throw new ServiceException(Code.ERROR,"用户不存在，请重新登录");
        }

        //用户密码加签验证token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try{
            jwtVerifier.verify(token);
        }catch (JWTVerificationException e){
            throw new ServiceException(Code.ERROR,"token验证失败，请重新登录");
        }
        return true;
    }
}
