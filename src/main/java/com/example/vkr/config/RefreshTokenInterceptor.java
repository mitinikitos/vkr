package com.example.vkr.config;

import com.example.vkr.auth.model.AuthToken;
import com.example.vkr.auth.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RefreshTokenInterceptor implements HandlerInterceptor {

    @Value("${jwt.header.string}")
    public String HEADER_STRING;

    @Value("${jwt.token.prefix}")
    public String TOKEN_PREFIX;

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        String header = req.getHeader(HEADER_STRING);
        String token = header.replace(TOKEN_PREFIX, "");
        AuthToken oldToken = (AuthToken) req.getAttribute("token");
        AuthToken authToken = tokenService.refreshToken(oldToken.getAccessToken());
        if (authToken != null) {
            req.setAttribute("token", authToken);
            return true;
        }
        res.sendError(401, "");
        return false;
    }
}
