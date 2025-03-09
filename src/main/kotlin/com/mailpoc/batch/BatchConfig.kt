package com.mailpoc.batch

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.batch.BatchTaskExecutor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

@Configuration
@EnableBatchProcessing

class BatchConfig {

    @Bean
    fun batchTaskExecutor() : TaskExecutor {
        val executor : ThreadPoolTaskExecutor = ThreadPoolTaskExecutor ().apply{
            corePoolSize = 10
            maxPoolSize = 10
            queueCapacity = 10
            setThreadNamePrefix("batch-thread-")
            initialize()
        }
        return executor
    }


}
