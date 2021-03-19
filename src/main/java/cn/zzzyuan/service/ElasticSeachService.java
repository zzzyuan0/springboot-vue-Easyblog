package cn.zzzyuan.service;

import cn.zzzyuan.entity.EsBlog;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ElasticSeachService{



    Boolean addIndex(String key,String id,String data) throws IOException;

    Boolean addIndexList(String key,List<EsBlog> list) throws IOException;

    Boolean deleteIndex(String key) throws IOException;

    Boolean deleteDoc(String key,String id) throws IOException;

    List<Map<String, Object>> getList(String key,String text) throws IOException;

}
