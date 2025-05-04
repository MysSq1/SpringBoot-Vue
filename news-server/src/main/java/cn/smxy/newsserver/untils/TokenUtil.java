package cn.smxy.newsserver.untils;

import cn.hutool.core.util.StrUtil;
import cn.smxy.newsserver.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import cn.smxy.newsserver.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

@Component
public class TokenUtil {
    private static UserService staticUserService;

    @Resource
    private UserService userService;

    @PostConstruct
    //在项目启动的时候执行该方法，也可以理解为在spring容器初始化的时候执行该方法。
    public void setUserService() {
        staticUserService = userService;
    }

    public static String getToken(User user) {
        String userId = user.getId().toString();
        String username = user.getUsername();
        String password = user.getPassword();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 24);

        //DateUtil.offsetHour(new Date(),2)
        return JWT.create()
                .withAudience(userId)//将userId保存到Token里面，作为载荷
                .withClaim("username", username)
                .withExpiresAt(calendar.getTime()) //24小时后过期
                .sign(Algorithm.HMAC256(password)); //以password作为token的密钥
    }

    public static User getCurrentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if (StrUtil.isNotBlank(token)) {
                String userId = JWT.decode(token).getAudience().get(0);
                return staticUserService.getById(Integer.valueOf(userId));
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }


}
