package cn.zzzyuan.service.impl;

import cn.zzzyuan.entity.Category;
import cn.zzzyuan.mapper.CategoryMapper;
import cn.zzzyuan.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 杂货店的阿猿
 * @since 2020-12-02
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
