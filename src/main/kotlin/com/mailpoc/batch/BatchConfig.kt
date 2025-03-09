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
            corePoolSize = Runtime.getRuntime().availableProcessors() * 2 // CPU 코어 기반 설정
            maxPoolSize = Runtime.getRuntime().availableProcessors() * 4 // 확장 가능한 스레드 풀
            queueCapacity = 50 // 큐 크기 (처리량에 따라 조정)
            setThreadNamePrefix("batch-thread-")
            initialize()
        }
        return executor
    }


}
