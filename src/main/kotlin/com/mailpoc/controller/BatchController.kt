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
import org.springframework.web.bind.annotation.PathVariable

@Controller
class BatchController (
    private val jobLauncher: JobLauncher,
    private val job: Job,
){
    @GetMapping("/batch/{status}/{toChange}")
    fun runBatch(
        @PathVariable status: String,
        @PathVariable toChange: String
    ) : ResponseEntity<Any> {
        val jobParameters = JobParametersBuilder()
            .addString("status", status)
            .addString("toChange", toChange)
            .toJobParameters()
        val jobExecution = jobLauncher.run(job, jobParameters)
        return ResponseEntity.ok(jobExecution)
    }
}
