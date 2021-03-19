package cn.zzzyuan.mapper;

import cn.zzzyuan.entity.Blog;
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
 * @author 关注公众号：MarkerHub
 * @since 2020-05-25
 */
public interface BlogMapper extends BaseMapper<Blog> {

    @Select("SELECT t3.id,t3.user_id,t3.title,t3.category,t3.content,t3.created FROM t_category t1 INNER JOIN t_category_blog t2 ON t1.category =t2.category_id INNER JOIN t_blog t3 ON t3.id=t2.article_id ${ew.customSqlSegment}")
    IPage<Blog> selectBlogByTagIPage(IPage<Blog> page, @Param(Constants.WRAPPER) QueryWrapper<CategoryBlog>  wrappers);
}
