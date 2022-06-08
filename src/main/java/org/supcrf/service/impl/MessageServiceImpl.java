package org.supcrf.service.impl;

import org.supcrf.entity.Message;
import org.supcrf.mapper.MessageMapper;
import org.supcrf.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 留言 服务实现类
 * </p>
 *
 * @author supcrf
 * @since 2021-02-28
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

}
