package com.salmanelk.springaitest.B_Embeddings;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.ai.document.Document;
import java.util.List;

@RestController
@RequestMapping("/v1/emb")
@RequiredArgsConstructor
public class EmbeddingsController {

    private final EmbeddingClient embeddingClient;
    private final OpenAiChatClient chatClient;
    private final VectorStore vectorStore;

    @Value("classpath:/ragPromptTemplate.txt")
    String ragPromptTemplate;

    @Value("${app.resource}")
    String documentRessource;


    @PostMapping("/ask")
    public Answer ask(@RequestBody Question question){
        TikaDocumentReader documentReader = new TikaDocumentReader(documentRessource);
        TextSplitter textSplitter = new TokenTextSplitter();
        vectorStore.add(textSplitter.apply(documentReader.get()));
        List<Document> vectorChosenDocument = vectorStore.similaritySearch(SearchRequest.query(question.question()));
        List<String> vectorChosenContents = vectorChosenDocument.stream().map(Document::getContent).toList();
        PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplate);
        promptTemplate.add("input", question.question());
        promptTemplate.add("documents", String.join("\n", vectorChosenContents));
        return new Answer(promptTemplate.render());
    }
}
