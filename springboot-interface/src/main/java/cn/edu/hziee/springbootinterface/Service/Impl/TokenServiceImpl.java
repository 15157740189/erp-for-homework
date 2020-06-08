package cn.edu.hziee.springbootinterface.Service.Impl;

import cn.edu.hziee.springbootinterface.Service.TokenService;


import cn.edu.hziee.springbootinterface.Util.RedisService;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.UUID;
@Slf4j
@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
            RedisService redisService;
    String REDIS_TOKEN_PREFIX = "token:";
    String TOKEN_NAME="TOKEN_NAME";

    /**
     * token的服务实现类：token引用了redis服务，创建token采用随机算法工具类生成随机uuid字符串,然后放入到redis中
     * 为了防止数据的冗余保留,设置过期时间为10000秒
     * 如果放入成功，最后返回这个token值。
     * checkToken方法就是从header中获取token到值(如果header中拿不到，就从parameter中获取)，如若不存在,直接抛出异常。
     * 这个异常信息可以被拦截器捕捉到，然后返回给前端。
     */

    /**
     * 创建token
     * @return
     */
    @Override
    public String createToken() {
        String uid= String.valueOf(UUID.randomUUID());
        StringBuilder token=new StringBuilder();
        try {
            token.append(REDIS_TOKEN_PREFIX).append(uid);
            redisService.setEx(token.toString(),token.toString(), 10000L);
            boolean empty= StringUtils.isEmpty(token.toString());
            if (!empty){
                return token.toString();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 校验token
     * @return
     */
    @Override
    public boolean checkToken(HttpServletRequest request) throws Exception {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            //根据名称获取请求头的值
            String value = request.getHeader(name);
            System.out.println(name+"---"+value);
        }
        String token=request.getHeader("token");
        if (StringUtils.isEmpty(token)){
            token=request.getParameter("token");
           log.info("Token:   "+token);
            throw new RuntimeException("ILLEGAL_ARGUMENT");
        }
        if(!redisService.exists(token)){
            throw new RuntimeException("REPETITIVE_OPERATION");
        }
        boolean remove=redisService.remove(token);
        if (!remove){
            throw new RuntimeException("REPETITIVE_OPERATION");
        }
        return false;
    }
}
