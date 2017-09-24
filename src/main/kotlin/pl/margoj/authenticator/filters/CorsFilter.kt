package pl.margoj.authenticator.filters

import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CorsFilter : OncePerRequestFilter()
{
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain)
    {
        response.addHeader("Access-Control-Allow-Origin", "*");

        if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            response.addHeader("Access-Control-Allow-Headers", "X-Requested-With,Origin,Content-Type, Accept");
        }

        filterChain.doFilter(request, response);
    }
}