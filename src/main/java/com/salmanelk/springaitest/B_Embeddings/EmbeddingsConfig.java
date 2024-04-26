package com.salmanelk.springaitest.B_Embeddings;


import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddingsConfig {




    @Bean
    VectorStore vectorStore (EmbeddingClient embeddingClient){
        SimpleVectorStore store = new SimpleVectorStore(embeddingClient);
//        TikaDocumentReader documentReader = new TikaDocumentReader(documentRessource);
//        TextSplitter textSplitter = new TokenTextSplitter();
//        store.add(textSplitter.apply(documentReader.get()));
        return store;
    }
}
