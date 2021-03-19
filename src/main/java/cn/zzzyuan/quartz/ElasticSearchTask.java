package cn.zzzyuan.quartz;

import cn.zzzyuan.common.contant.ElasticSeachContant;
import cn.zzzyuan.entity.Blog;
import cn.zzzyuan.entity.EsBlog;
import cn.zzzyuan.service.BlogService;
import cn.zzzyuan.service.ElasticSeachService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Slf4j
@Component
public class ElasticSearchTask {
    @Autowired
    ElasticSeachService elasticSeachService;
    @Autowired
    BlogService blogService;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private ModelMapper modelMapper;

    public void elasticSearchInit() throws IOException {
        List<Blog> list = blogService.list();
        List<EsBlog> esBloglist = new ArrayList<EsBlog>();
        for(Blog blog:list){
            EsBlog esBlog = modelMapper.map(blog, EsBlog.class);
            esBloglist.add(esBlog);
        }
        elasticSeachService.addIndexList(ElasticSeachContant.BLOGS_INDEX.code(), esBloglist);
        log.info("elasticSearchInit-------------{}",sdf.format(new Date()));
    }
}
