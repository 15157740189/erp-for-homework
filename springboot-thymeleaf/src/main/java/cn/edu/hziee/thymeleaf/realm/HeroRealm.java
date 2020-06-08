package cn.edu.hziee.thymeleaf.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import sun.security.provider.MD5;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HeroRealm extends AuthorizingRealm {
    Map<String,String> userMap=new HashMap<>(16);
    {
        userMap.put("shiro","123456");
        userMap.put("战栗的龙卷","o83");
        userMap.put("琦玉老师","8848");
        super.setName("heroRealm");
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username= (String) principals.getPrimaryPrincipal();
        //从数据库或者缓存中获取数据
        Set<String> roles=getRolesByUsername(username);
        Set<String> permissions=getPermissionsByUsername(username);
        SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    private Set<String> getPermissionsByUsername(String username) {
        Set<String> set=new HashSet<>();
        set.add("add");
        set.add("delete");
        set.add("query");
        return set;
    }

    private Set<String> getRolesByUsername(String username) {
        Set<String> set=new HashSet<>();
        set.add("admin");
        set.add("user");
        return set;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //1.从主体传来的认证信息中，获得用户名
        String userName=(String)token.getPrincipal();
        //2.通过用户名在数据库中获取凭证
        String password=getPasswordByUsername(userName);
        if (password==null)
            return null;
        Md5Hash md5Hash=new Md5Hash(password,userName);
        SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo("琦玉老师", md5Hash.toString(),"heroRealm");
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(userName));
        return authenticationInfo;
    }

    private String getPasswordByUsername(String name) {
        return userMap.get(name);
    }
}
