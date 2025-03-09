package com.mailpoc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MailpocApplication

fun main(args: Array<String>) {
    runApplication<MailpocApplication>(*args)
}
