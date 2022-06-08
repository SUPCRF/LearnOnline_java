package org.supcrf.service.impl;

import org.supcrf.entity.Exam;
import org.supcrf.mapper.ExamMapper;
import org.supcrf.service.ExamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 考试信息 服务实现类
 * </p>
 *
 * @author supcrf
 * @since 2021-02-27
 */
@Service
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam> implements ExamService {

}
