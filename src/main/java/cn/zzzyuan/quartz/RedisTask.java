package cn.zzzyuan.quartz;

import cn.zzzyuan.common.contant.RedisContant;
import cn.zzzyuan.entity.ArticleHeat;
import cn.zzzyuan.redis.RedisService;
import cn.zzzyuan.service.ArticleHeatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class RedisTask{

    @Autowired
    RedisService redisService;
    @Autowired
    ArticleHeatService articleHeatService;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void redisInit() {
        redisService.clear();
        List<ArticleHeat> list = articleHeatService.list();
        for (ArticleHeat articleHeat : list) {
            redisService.add(RedisContant.ARTICLE_HEAT.code(),String.valueOf(articleHeat.getId()),articleHeat.getScore());
        }
        log.info("RedisInitSuccess-------------{}",sdf.format(new Date()));
    }
    public void persistenceToMysql() {
        List<ArticleHeat> heats = redisService.getHeat(RedisContant.ARTICLE_HEAT.code(), -1);
        for (ArticleHeat heat : heats) {
            articleHeatService.updateById(heat);
        }
        log.info("persistenceToMysql-------------{}",sdf.format(new Date()));
    }
}
