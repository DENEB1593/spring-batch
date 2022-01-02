package com.example.batch.part1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class HelloConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public HelloConfig(JobBuilderFactory jobBuilderFactory,
                       StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    // spring batch의 실행 단위
    @Bean
    public Job helloJob() {
        return jobBuilderFactory.get("helloJob")
                .incrementer(new RunIdIncrementer())
                .start(this.helloStep())
                .build();
    }

    // job의 실행 단위
    @Bean
    public Step helloStep() {
        return stepBuilderFactory.get("helloJob")
                .tasklet((contribution, chunkContext) -> {
                    log.info("hello spring batch");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

}
