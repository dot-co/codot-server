package com.dot.codot

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@ComponentScan("com.dot.codot")
@EnableJpaRepositories(basePackages = ["com.dot.codot.model"])
@EntityScan(basePackages = ["com.dot.codot.model"])
class CodotApplication
fun main(args: Array<String>) {
//    var context : ConfigurableApplicationContext =
    SpringApplication.run(CodotApplication::class.java, *args)
}
