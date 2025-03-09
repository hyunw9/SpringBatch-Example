package com.mailpoc.batch

import com.mailpoc.entity.User
import com.mailpoc.entity.UserRepository
import jakarta.transaction.Transactional
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JdbcCursorItemReader
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager
import java.sql.ResultSet
import javax.sql.DataSource
import kotlin.apply

@Component
class BatchJobManager (
    private val userRepository : UserRepository,
    private val platformTransactionManager: PlatformTransactionManager,
){

    @Bean
    fun firstJob(
        jobRepository : JobRepository,
        sampleStep : Step
    ) : Job {
        return JobBuilder("firstJob", jobRepository)
            .start(sampleStep)
            .build()
    }

    @Bean
    fun sampleStep (
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        itemReader: ItemReader<User>,
        itemWriter: ItemWriter<User>,
        itemProcessor: ItemProcessor<User, User>
    ): Step {
        return StepBuilder("sampleStep", jobRepository)
            .chunk<User, User>(10,transactionManager)
            .reader(itemReader)
            .processor(itemProcessor)
            .writer(itemWriter)
            .build()
    }

    @Bean
    fun simpleTasklet() : Tasklet {
        return Tasklet { _, _ ->
            println("Hello, World!")
            null
        }
    }

    @Bean
    fun itemReader(
        dataSource: DataSource
    ) : JdbcCursorItemReader<User> {
        return JdbcCursorItemReader<User>().apply {
            setDataSource(dataSource)
            sql = "SELECT * FROM user WHERE status = 'PENDING'"
            setFetchSize(10) // 성능 최적화 (10개씩 읽기)
            setRowMapper { rs, _ ->
                User(
                    id = rs.getLong("id"),
                    name = rs.getString("name"),
                    email = rs.getString("email"),
                    age = rs.getInt("age"),
                    status = rs.getString("status")
                )
            }
        }
    }

    @Bean
    fun itemProcessor() : ItemProcessor<User, User>{
        return ItemProcessor { user ->
            user.copy(status = "COMPLETE")
        }
    }

    @Bean
    @Transactional
    fun itemWriter(
        userRepository: UserRepository
    ) : ItemWriter<User> {
        return ItemWriter { users ->
            userRepository.saveAll(users)
            userRepository.flush()
        }
    }
}
