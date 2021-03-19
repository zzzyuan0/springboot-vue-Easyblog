package cn.zzzyuan.shiro;

import cn.hutool.core.bean.BeanUtil;
import cn.zzzyuan.entity.User;
import cn.zzzyuan.service.UserService;
import cn.zzzyuan.util.JwtUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountRealm  extends AuthorizingRealm {

    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken)authenticationToken;
        String userId = jwtUtil.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();
        User user = userService.getById(Long.valueOf(userId));
        if(user == null){
            throw new UnknownAccountException("账号不存在");
        }
        if(user.getStatus() == -1){
            throw new LockedAccountException("账号已被锁定");
        }
        AccountProfile accountProfile = new AccountProfile();
        BeanUtil.copyProperties(user,accountProfile);

        return new SimpleAuthenticationInfo(accountProfile,jwtToken.getCredentials(),getName());
    }

    /**
     * 修改之后报错消失
     * does not support authentication token [cn.zzzyuan.shiro.JwtToken@5c8b68ef].
     * Please ensure that the appropriate Realm implementation is configured correctly or that the realm accepts
     * AuthenticationTokens of this type
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }
}
