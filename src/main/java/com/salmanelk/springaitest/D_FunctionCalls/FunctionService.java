package com.salmanelk.springaitest.D_FunctionCalls;

import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

public class FunctionService implements Function<FunctionsRecords.Request, String> {

    @Value("${function.calls.file.path}")
    String filePath;



    @Override
    public String apply(FunctionsRecords.Request request) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(request.content());
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
        return filePath;
    }
}
