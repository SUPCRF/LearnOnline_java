package org.supcrf.service.impl;

import org.supcrf.entity.StudentUser;
import org.supcrf.mapper.StudentUserMapper;
import org.supcrf.service.StudentUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学生信息 服务实现类
 * </p>
 *
 * @author supcrf
 * @since 2021-02-06
 */
@Service
public class StudentUserServiceImpl extends ServiceImpl<StudentUserMapper, StudentUser> implements StudentUserService {

}
