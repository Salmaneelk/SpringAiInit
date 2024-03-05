package com.salmanelk.springaitest.Controller;


import com.salmanelk.springaitest.Model.Footballer;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/football")
@RequiredArgsConstructor
public class FootballController {

    private final OpenAiChatClient chatClient;


    @GetMapping("/{year}")
    public Footballer getFootballer (@PathVariable int year) {

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
