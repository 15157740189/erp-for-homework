package cn.edu.hziee.springbootinterface.Service;

import javax.servlet.http.HttpServletRequest;

public interface TokenService {
    /**
     *创建token
     * @return
     */
    String createToken();

    /**
     *校验token
     * @param request
     *@return
     */
    boolean checkToken(HttpServletRequest request)throws Exception;
}
