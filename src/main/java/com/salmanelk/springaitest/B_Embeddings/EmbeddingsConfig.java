package com.salmanelk.springaitest.B_Embeddings;


import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@Slf4j
public class EmbeddingsConfig {




    @Bean
    VectorStore vectorStore (EmbeddingClient embeddingClient){
        log.info("creating the vector store bean");
        SimpleVectorStore store = new SimpleVectorStore(embeddingClient);
        return store;
    }
}
