package cn.zzzyuan.mapper;

import cn.zzzyuan.entity.Category;
import cn.zzzyuan.entity.CategoryBlog;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 杂货店的阿猿
 * @since 2020-12-02
 */
public interface CategoryBlogMapper extends BaseMapper<CategoryBlog> {
    /**
     * 通过文章id查找分类
     * @param page
     * @param wrappers
     * @return
     */
    @Select("SELECT t1.category,t1.category_name FROM t_category t1 INNER JOIN t_category_blog t2 ON t1.category =t2.category_id  ${ew.customSqlSegment}")
    IPage<Category> selectCategoryByTagIPage(IPage<Category> page, @Param(Constants.WRAPPER) QueryWrapper<CategoryBlog> wrappers);
}
