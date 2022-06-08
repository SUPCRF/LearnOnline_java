package org.supcrf.shiro;

import cn.hutool.core.bean.BeanUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.supcrf.common.util.JwtUtils;
import org.supcrf.entity.AdminUser;
import org.supcrf.service.AdminUserService;

/**
 * @title: AccountRealm
 * @projectName learn-online_java
 * @description: TODO
 * @Author supcrf
 * @Version 1.0
 * @Date 2/13/2021 19:24
 */
@Component
public class  AccountRealm extends AuthorizingRealm {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AdminUserService adminUserService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) authenticationToken;
        String adminId = jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();
        AdminUser adminUser = adminUserService.getById(Long.valueOf(adminId));
        if (adminUser == null) {
            throw new UnknownAccountException("账户不存在");
        }
        if (adminUser.getState() == -1) {
            throw new LockedAccountException("账户已被锁定");
        }
        AccountProfile profile = new AccountProfile();
        BeanUtil.copyProperties(adminUser, profile);
        return new SimpleAuthenticationInfo(profile, jwtToken.getCredentials(),getName());
    }
}
