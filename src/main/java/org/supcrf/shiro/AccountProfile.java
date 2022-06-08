package org.supcrf.shiro;

import lombok.Data;

import java.io.Serializable;

/**
 * @title: AccountProfile
 * @projectName learn-online_java
 * @description: TODO
 * @Author supcrf
 * @Version 1.0
 * @Date 2/13/2021 20:37
 */
@Data
public class AccountProfile implements Serializable {
    private Long id;
    private String username;
    private String avatar;
}
