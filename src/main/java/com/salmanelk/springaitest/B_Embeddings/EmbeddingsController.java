package com.salmanelk.springaitest.B_Embeddings;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.prompt.Prompt;
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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/v1/emb")
@RequiredArgsConstructor
@Slf4j
public class EmbeddingsController {

    private final OpenAiChatClient chatClient;
    private final VectorStore vectorStore;


    @Value("${app.resource}")
    String documentRessource;

    @Value("${prompt.template}")
    String promptString;


    @PostMapping("/ask")
    public Answer ask(@RequestBody Question question) throws IOException {


        log.info("Extracting document :{}", documentRessource);
//      map the ressource into a list of documents
        TikaDocumentReader documentReader = new TikaDocumentReader(documentRessource);
        TextSplitter textSplitter = new TokenTextSplitter();

        log.info("Creating the list of documents using the vector Service");
//      add the list of documents to the vector store, search for the documents with similarity to the question, map the results to list of strings
        vectorStore.add(textSplitter.apply(documentReader.get()));
        List<Document> vectorChosenDocument = vectorStore.similaritySearch(SearchRequest.query(question.question()).withTopK(2));
        List<String> vectorChosenContents = vectorChosenDocument.stream().map(Document::getContent).toList();


        // create prompt template and define its values
        PromptTemplate promptTemplate = new PromptTemplate(promptString,
                Map.of("input", question.question(),
                        "documents", String.join("\n", vectorChosenContents)));


        Prompt prompt = promptTemplate.create();
        String answer = chatClient.call(prompt).getResult().getOutput().getContent();
        return new Answer(answer);
    }
}
