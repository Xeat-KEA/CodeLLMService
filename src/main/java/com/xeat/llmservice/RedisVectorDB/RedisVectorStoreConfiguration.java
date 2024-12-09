package com.xeat.llmservice.RedisVectorDB;

import lombok.AllArgsConstructor;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.vectorstore.RedisVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.search.Schema;

@Configuration
@AllArgsConstructor
public class RedisVectorStoreConfiguration {

    @Bean
    public RedisVectorStore.RedisVectorStoreConfig redisVectorStoreConfig() {
        return RedisVectorStore.RedisVectorStoreConfig.builder()
                .withPrefix("vector:")
                .withContentFieldName("content")
                .withEmbeddingFieldName("embedding")
                .withVectorAlgorithm(RedisVectorStore.Algorithm.HSNW)
                .withMetadataFields(
                        new RedisVectorStore.MetadataField("type", Schema.FieldType.TEXT),
                        new RedisVectorStore.MetadataField("userId", Schema.FieldType.TEXT)
                )
                .build();
    }

    @Bean
    public JedisPooled jedisPooled(@Value("${spring.data.redis.host}") String host, @Value("${spring.data.redis.port}") Integer port, @Value("${spring.data.redis.user}") String user, @Value("${spring.data.redis.password}") String password) {
        return new JedisPooled(host, port, user, password);
    }

    @Bean
    public EmbeddingModel embeddingModel(@Value("${spring.ai.openai.base-url}") String baseUrl, @Value("${spring.ai.openai.api-key}") String apiKey) {
        return new OpenAiEmbeddingModel(new OpenAiApi(baseUrl, apiKey),
                MetadataMode.EMBED,
                OpenAiEmbeddingOptions.builder()
                        .withModel("text-embedding-ada-002")
                        .build());
    }

    @Bean
    public RedisVectorStore redisVectorStore(@Value("${spring.ai.openai.base-url}") String baseUrl, @Value("${spring.ai.openai.api-key}") String apiKey,@Value("${spring.data.redis.host}") String host, @Value("${spring.data.redis.port}") Integer port, @Value("${spring.data.redis.user}") String user, @Value("${spring.data.redis.password}") String password) {
        return new RedisVectorStore(redisVectorStoreConfig(), embeddingModel(baseUrl, apiKey), jedisPooled(host, port, user, password), false);
    }

    @Bean
    public VectorStore vectorStore(RedisVectorStore redisVectorStore) {
        return redisVectorStore;
    }
}
