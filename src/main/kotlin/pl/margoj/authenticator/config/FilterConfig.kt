package pl.margoj.authenticator.config

import org.springframework.context.annotation.Configuration
import pl.margoj.authenticator.filters.CorsFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean

@Configuration
open class FilterConfig
{
    fun corsFilter() = CorsFilter()

    @Bean
    open fun filterRegistration(): FilterRegistrationBean
    {
        val registration = FilterRegistrationBean()
        registration.filter = corsFilter()
        registration.addUrlPatterns("/*")
        registration.setName("corsFilter")
        registration.order = 1
        return registration
    }
}