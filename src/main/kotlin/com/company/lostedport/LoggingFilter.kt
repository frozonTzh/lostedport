package com.company.lostedport

import jakarta.servlet.*
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import java.io.IOException

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
open class LoggingFilter : Filter {
    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpReq = request as HttpServletRequest
        val mutHttpReq = MutableHttpServletRequest(httpReq)
        logger.error("{}",request.requestURI)
        logger.warn("receive request with headers {}", mutHttpReq.getHeaderMap())
//        if (httpReq.getHeader("x-forwarded-host") != null) {
//            val forwardedHost = httpReq.getHeader("x-forwarded-host")
//            if (forwardedHost?.contains(":") == true) {
//                val a = forwardedHost.split(":")
//                val port = a[1]
//                mutHttpReq.rmHeaders("x-forwarded-port")
//                mutHttpReq.putHeader("x-forwarded-port", port)
//                logger.warn("receive request with headers {}", mutHttpReq.getHeaderMap())
//                chain.doFilter(mutHttpReq, response)
//                return
//            }
//        }
        chain.doFilter(request, response)
    }

    companion object {
        var logger: Logger = LoggerFactory.getLogger(LoggingFilter::class.java)
    }
}
