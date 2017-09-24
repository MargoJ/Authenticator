package pl.margoj.authenticator.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
import pl.margoj.authenticator.entities.response.MappingsEntity

@RestController
class IndexController @Autowired constructor
(
        val requestMappingHandlerMapping: RequestMappingHandlerMapping
)
{
    @RequestMapping("/")
    fun index(): MappingsEntity
    {
        val out = arrayListOf<String>()
        this.requestMappingHandlerMapping.handlerMethods.forEach {
            it.key.patternsCondition.patterns.forEach {
                out.add(it)
            }
        }
        return MappingsEntity(out)
    }
}