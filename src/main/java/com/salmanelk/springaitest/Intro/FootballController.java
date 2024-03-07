package com.salmanelk.springaitest.Intro;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/football")
@RequiredArgsConstructor
public class FootballController {

    private final FootballerService footballerService;

    @GetMapping("/{year}")
    public Footballer getFootballer (@PathVariable int year) {
        return footballerService.getFootballer (year);
    }

}
