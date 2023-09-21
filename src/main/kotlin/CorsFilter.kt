import com.google.common.net.HttpHeaders
import jakarta.servlet.*
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import java.io.IOException

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@WebFilter("/*") //TODO Exclude API endpoints when possible

class CorsFilter : Filter {
    @Throws(IOException::class, ServletException::class)
    override fun doFilter(req: ServletRequest, resp: ServletResponse, chain: FilterChain) {
        val response = resp as HttpServletResponse
        val request = req as HttpServletRequest
        response.setHeader(HttpHeaders.CONTENT_SECURITY_POLICY, "frame-ancestors *")
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
        //        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST, GET, OPTIONS, DELETE, PUT")
        response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600")
        response
            .setHeader(
                HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Origin, origin, x-requested-with, authorization, " +
                        "Content-Type, Authorization, credential"
            )
        if (HttpMethod.OPTIONS.name().equals(request.method, ignoreCase = true)) {
            response.status = HttpServletResponse.SC_OK
        } else {
            chain.doFilter(req, resp)
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(CorsFilter::class.java)
    }
}
