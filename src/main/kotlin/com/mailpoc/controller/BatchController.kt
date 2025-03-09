package com.mailpoc.controller

import ch.qos.logback.core.net.server.ServerRunner
import com.mailpoc.batch.BatchJobManager
import org.apache.coyote.Response
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class BatchController (
    private val jobLauncher: JobLauncher,
    private val job: Job,
){
    @GetMapping("/batch")
    fun runBatch() : ResponseEntity<Any> {
        val jobParameters = JobParametersBuilder()
            .addString("JobID", System.currentTimeMillis().toString())
            .toJobParameters()
        val jobExecution = jobLauncher.run(job, jobParameters)
        return ResponseEntity.ok(jobExecution)
    }
}
