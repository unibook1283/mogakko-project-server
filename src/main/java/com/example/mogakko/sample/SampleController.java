package com.example.mogakko.sample;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SampleController {

    private final SampleService sampleService;

    @GetMapping
    public String Hello() {
        return "hello!";
    }

    @GetMapping("/samples")
    public List<Sample> getSamples() {
        return sampleService.findSamples();
    }

    @PostMapping("/samples")
    public Long saveSample2(@RequestBody Sample sample) {
        Long sampleId = sampleService.saveSample(sample);
        return sampleId;
    }

}
