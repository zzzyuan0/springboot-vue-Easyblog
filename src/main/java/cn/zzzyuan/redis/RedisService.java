package cn.zzzyuan.redis;

import cn.zzzyuan.entity.ArticleHeat;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Set;

/**
 * @author 86188
 */
public interface RedisService {



           void add(String key,String value,Long score);

           void clear();

            Long getScore(String key,String value);

            Long size(String key);

            void updateRank(String key,String value);

            List<ArticleHeat> getHeat(String key,Integer n);

            List<ArticleHeat> setToList(Set<ZSetOperations.TypedTuple<String>> result) ;
}
