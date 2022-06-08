package org.supcrf.service.impl;

import org.supcrf.entity.AdminUser;
import org.supcrf.mapper.AdminUserMapper;
import org.supcrf.service.AdminUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员信息 服务实现类
 * </p>
 *
 * @author supcrf
 * @since 2021-02-06
 */
@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements AdminUserService {

}
