package com.ghtk.social_network.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghtk.social_network.application.response.RestResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class CustomAccessDeniedHandle implements AccessDeniedHandler {
    private final AccessDeniedHandler accessDeniedHandler = new BearerTokenAccessDeniedHandler();
    public final ObjectMapper objectMapper;

    public CustomAccessDeniedHandle(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        this.accessDeniedHandler.handle(request, response, accessDeniedException);
        response.setContentType("application/json; charset=utf-8");
        RestResponse<Object> er = new RestResponse<Object>();
        er.setStatusCode(HttpStatus.FORBIDDEN.value());
        er.setMessage(accessDeniedException.getMessage());
        er.setError("You don not have permission to access this resource");
        objectMapper.writeValue(response.getWriter(), er);
    }
}
