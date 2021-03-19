package cn.zzzyuan.redis;

import cn.zzzyuan.entity.ArticleHeat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author 86188
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void add(String key,String value,Long score){
        redisTemplate.opsForZSet().add(key,value,score);
    }
    @Override
    public void clear(){
        Set<String> keys = redisTemplate.keys("*");
        assert keys != null;
        redisTemplate.delete(keys);
    }
    @Override
    public Long getScore(String key,String value){
        return  Objects.requireNonNull(redisTemplate.opsForZSet().score(key, value)).longValue();
    }
    @Override
    public Long size(String key){
        return redisTemplate.opsForZSet().size(key);
   }
    @Override
    public void updateRank(String key,String value){
        redisTemplate.opsForZSet().incrementScore(key,value,1);
    }
    @Override
    public List<ArticleHeat> getHeat(String key,Integer n){
        Set<ZSetOperations.TypedTuple<String> > typedTuples = redisTemplate.opsForZSet().reverseRangeWithScores(key,0,n);
        assert typedTuples != null;
        return setToList(typedTuples);
    }

    @Override
    public List<ArticleHeat> setToList(Set<ZSetOperations.TypedTuple<String>> result) {
        List<ArticleHeat> rankList = new ArrayList<>(result.size());
        for (ZSetOperations.TypedTuple<String> sub : result) {
            rankList.add(new ArticleHeat(Long.parseLong(Objects.requireNonNull(sub.getValue())), Objects.requireNonNull(sub.getScore()).longValue()));
        }
        return rankList;
    }
}
