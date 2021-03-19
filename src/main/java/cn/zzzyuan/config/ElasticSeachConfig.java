package cn.zzzyuan.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSeachConfig {
    @Bean
    public RestHighLevelClient restHighLevelClient(){
            return new RestHighLevelClient(RestClient.builder(new HttpHost("39.108.10.232",9200,"http")));
    }
}
