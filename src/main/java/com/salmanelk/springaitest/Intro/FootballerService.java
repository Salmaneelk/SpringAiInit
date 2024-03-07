package com.salmanelk.springaitest.Intro;


import com.salmanelk.springaitest.Intro.Footballer;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FootballerService {

    private final OpenAiChatClient chatClient;
    public Footballer getFootballer(int year) {
        String promptString = """
        Give the ballon d'or winner of {year} and return the response like this {format} 
        """;

//          creating a parser to map the output call to our predefined structure
        BeanOutputParser<Footballer> parser = new BeanOutputParser<>(Footballer.class);

//          creating a prompt template and defining its properties values
        PromptTemplate promptTemplate = new PromptTemplate(promptString);
        promptTemplate.add("year", year);
        promptTemplate.add("format", parser.getFormat());

        Prompt prompt = promptTemplate.create();

//          calling and mapping
        String response = chatClient.call(prompt).getResult().getOutput().getContent();
        Footballer responseFootballer = parser.parse(response);
        return responseFootballer;
    }
}
