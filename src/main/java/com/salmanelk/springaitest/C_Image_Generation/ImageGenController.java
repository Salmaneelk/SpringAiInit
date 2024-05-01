package com.salmanelk.springaitest.C_Image_Generation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.image.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/img")
public class ImageGenController {

//  image ai client
    private final ImageClient imageClient;

    @PostMapping("/imagen")
    private String generateImage (@RequestBody ImageGenRequest imageGenRequest){

//      define which image model you want to work with, you can also define image properties like height, format, pixels ...
        ImageOptions imageOptions = ImageOptionsBuilder.builder()
                .withModel("dall-e-3")
                .build();

//      create prompt, pass the image options configuration and call it using the image client
        ImagePrompt imagePrompt = new ImagePrompt(imageGenRequest.prompt(), imageOptions);
        ImageResponse iResponse = imageClient.call(imagePrompt);

//      returns image URL
        String url = iResponse.getResult().getOutput().getUrl();
        log.info("image created URL :{}", url);

        return url;
    }
}
