package com.salmanelk.springaitest.D_FunctionCalls;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Consumer;
import java.util.function.Function;

@Configuration
public class FunctionConfiguration {

    @Bean
    @Description("Write in content in a local file")
    public Function<FunctionsRecords.Request, String> WriteInFile() {
        return new FunctionService();
    }
}
