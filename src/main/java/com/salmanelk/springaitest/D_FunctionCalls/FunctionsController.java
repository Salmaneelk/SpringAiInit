package com.salmanelk.springaitest.D_FunctionCalls;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class FunctionsController {

    private final ChatClient chatClient;


    @GetMapping("/write")
    public String writeFile(@RequestParam(value = "message") String message){
        SystemMessage systemMessage = new SystemMessage("You are my AI Assistant helping me write the responses of these prompts in a file, You have my confirmation to always write the content of the response in the file");
        UserMessage userMessage = new UserMessage(message);
        OpenAiChatOptions openAiChatOptions = OpenAiChatOptions.builder()
                .withFunction("WriteInFile")
                .build();


        ChatResponse response = chatClient.call(new Prompt(List.of(systemMessage,userMessage),openAiChatOptions));

        return response.getResult().getOutput().getContent();
    }
}
