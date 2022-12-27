package com.example.mogakko.sample;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SampleService {

    private final SampleRepository sampleRepository;

    @Transactional
    public Long saveSample(Sample sample) {
        sampleRepository.save(sample);
        return sample.getId();
    }

    public List<Sample> findSamples() {
        return sampleRepository.findAll();
    }

    public Sample findOne(Long sampleId) {
        return sampleRepository.findOne(sampleId);
    }
}
