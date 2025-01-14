package org.example.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class RateLimitingFilter implements Filter {

    private static final RateLimiter rateLimiter = new RateLimiter(100,60000);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String clientIP = httpRequest.getRemoteAddr();
        if (!rateLimiter.checkRateLimit(clientIP)) {
            httpResponse.setStatus(429); // Too Many Requests
            httpResponse.getWriter().write("Limite de pedidos excedido, tente mais tarde!");
            return;
        }

        chain.doFilter(request, response);
    }
}
