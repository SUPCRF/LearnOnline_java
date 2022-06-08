package org.supcrf.common.util;

import org.apache.shiro.SecurityUtils;
import org.supcrf.shiro.AccountProfile;

/**
 * @title: ShiroUtil
 * @projectName supcrf_edu
 * @description: TODO
 * @Author supcrf
 * @Version 1.0
 * @Date 11/25/2020 23:27
 */
public class ShiroUtil {

    public static AccountProfile getProfile() {
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }
}
