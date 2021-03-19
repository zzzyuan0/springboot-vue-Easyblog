package cn.zzzyuan.service.impl;

import cn.hutool.json.JSONUtil;
import cn.zzzyuan.common.contant.ElasticSeachContant;
import cn.zzzyuan.entity.EsBlog;
import cn.zzzyuan.service.ElasticSeachService;
import cn.zzzyuan.util.StringUtil;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ElasticSeachServiceImpl implements ElasticSeachService {

    @Autowired
    RestHighLevelClient restHighLevelClient;
    @Override
    public Boolean addIndex(String key,String id,String data) throws IOException {
        IndexRequest request = new IndexRequest(key);
        request.timeout("1s");
        request.source(JSONUtil.toJsonStr(data));
        IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        return true;
    }

    @Override
    public Boolean addIndexList(String key, List<EsBlog> list) throws IOException {
        deleteIndex(key);
        BulkRequest bulkRequest = new BulkRequest();
        for (EsBlog esBlog : list) {
            bulkRequest.add(new IndexRequest(key).id(String.valueOf(esBlog.getId())).source(JSONUtil.toJsonStr(esBlog),XContentType.JSON));
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return !bulk.hasFailures();
    }

    @Override
    public  Boolean deleteIndex(String key) throws IOException {
        GetIndexRequest request = new GetIndexRequest(key);
        if(restHighLevelClient.indices().exists(request,RequestOptions.DEFAULT)){
         DeleteIndexRequest deleteRequest = new DeleteIndexRequest(key);
         restHighLevelClient.indices().delete(deleteRequest,RequestOptions.DEFAULT);
        }
        return true;
    }
    @Override
    public Boolean deleteDoc(String key,String id) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(key);
        deleteRequest.id(id);
        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest,RequestOptions.DEFAULT);
        return true;
    }

    @Override
    public List<Map<String, Object>> getList(String key,String keyWord) throws IOException {
        SearchRequest searchRequest = new SearchRequest(key);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

            QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                    .should(QueryBuilders.matchQuery("title",keyWord))
                    .should(QueryBuilders.matchQuery("content",keyWord));
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("*")
                .preTags(ElasticSeachContant.BEG_STR.code())
                .postTags(ElasticSeachContant.END_STR.code())
                .requireFieldMatch(false);
        searchSourceBuilder.highlighter(highlightBuilder);


        searchRequest.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>> ();
        for (SearchHit hit : search.getHits().getHits()) {
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField title = highlightFields.get("title");
            HighlightField content = highlightFields.get("content");
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            if(title != null){
                sourceAsMap.put("title",StringUtil.truncated(title.getFragments()));
            }
            if(content != null){
                sourceAsMap.put("content",StringUtil.truncated(content.getFragments(),1));
            }
            list.add(hit.getSourceAsMap());
        }
        return list;
    }

}
