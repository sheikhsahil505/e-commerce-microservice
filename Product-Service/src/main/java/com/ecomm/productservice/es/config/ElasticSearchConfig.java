package com.ecomm.productservice.es.config;

import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import javax.net.ssl.SSLContext;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.ecomm.productservice.es.repository")
public class ElasticSearchConfig extends ElasticsearchConfiguration {

    @SuppressWarnings("NullableProblems")
    @Override
    public ClientConfiguration clientConfiguration() {
        try {
            SSLContextBuilder sslContextBuilder = SSLContexts.custom()
                    .loadTrustMaterial(null, ((x509Certificates, s) -> true));
            final SSLContext sslContext = sslContextBuilder.build();
            return ClientConfiguration.builder()
                    .connectedTo("localhost" + ":" + "9200")
                    .usingSsl(sslContext)
                    .withBasicAuth("elastic", "x=2IAP=-5bwhPT95TL98")
                    .build();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
