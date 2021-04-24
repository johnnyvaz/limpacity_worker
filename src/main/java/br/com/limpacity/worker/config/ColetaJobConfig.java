package br.com.limpacity.worker.config;

import br.com.limpacity.worker.job.ColetaJobListener;
import br.com.limpacity.worker.job.ColetaProcessor;
import br.com.limpacity.worker.job.ColetaReader;
import br.com.limpacity.worker.job.ColetaWriter;
import br.com.limpacity.worker.job.expired.ColetaFirstNotProcessor;
import br.com.limpacity.worker.job.expired.ColetaFirstNotReader;
import br.com.limpacity.worker.job.expired.ColetaFirstNotWriter;
import br.com.limpacity.worker.model.ColetaQrCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Slf4j
@Configuration
public class ColetaJobConfig {

    @Value("${spring.batch.chunk}")
    private Integer chunkSize;

    @Value("${spring.batch.limit-thread}")
    private Integer limitThread;

    @Bean
    public Job init(JobBuilderFactory jobBuilderFactory,
                    ColetaJobListener listener,
                    StepBuilderFactory stepBuilderFactory,
                    Step coletaStep,
                    Step firstNotStep) {

        log.info("--> Init Job");

        return jobBuilderFactory.get("sendingMsg")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(coletaStep)
                .next(firstNotStep)
                .build();

    }

    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

    @Bean
    public Step coletaStep(StepBuilderFactory stepBuilderFactory,
                           ColetaReader reader,
                           ColetaProcessor processor,
                           ColetaWriter writer, TaskExecutor taskExecutor) {

        return stepBuilderFactory.get("coletaStep")
                .<ColetaQrCode, ColetaQrCode>chunk(this.chunkSize)
                .reader(reader.reader())
                .processor(processor)
                .writer(writer)
                .taskExecutor(taskExecutor)
                .throttleLimit(limitThread)
                .build();

    }

    @Bean
    public Step firstNotStep(StepBuilderFactory stepBuilderFactory,
                            ColetaFirstNotReader reader,
                            ColetaFirstNotProcessor processor,
                            ColetaFirstNotWriter writer, TaskExecutor taskExecutor) {

        return stepBuilderFactory.get("expiredStep")
                .<ColetaQrCode, ColetaQrCode>chunk(this.chunkSize)
                .reader(reader.reader())
                .processor(processor)
                .writer(writer)
                .taskExecutor(taskExecutor)
                .throttleLimit(limitThread)
                .build();

    }
}
