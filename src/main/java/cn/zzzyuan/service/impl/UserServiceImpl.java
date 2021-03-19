package cn.zzzyuan.service.impl;

import cn.zzzyuan.entity.User;
import cn.zzzyuan.mapper.UserMapper;
import cn.zzzyuan.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 杂货店的阿猿
 * @since 2020-10-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
