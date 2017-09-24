package pl.margoj.authenticator.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
open class HttpConfig
{
    @Bean
    open fun restTemplate(): RestTemplate = RestTemplate()
}