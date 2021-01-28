package com.example.vkr.config;

import com.example.vkr.auth.service.TokenService;
import com.example.vkr.exception.BlackListTokenException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtBlackListFilter extends OncePerRequestFilter {

    @Value("${jwt.header.string}")
    public String HEADER_STRING;

    @Value("${jwt.token.prefix}")
    public String TOKEN_PREFIX;

    @Resource(name = "tokenService")
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        String header = req.getHeader(HEADER_STRING);
        String authToken = null;
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            authToken = header.replace(TOKEN_PREFIX,"");
            try {
                if (tokenService.isBlackList(authToken.trim())) {
                    throw new BlackListTokenException("blacklisted token");
                }
            } catch (BlackListTokenException e) {
                logger.error(e.getMessage());
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                return;
            }
        } else {
            logger.warn("Couldn't find bearer string, header will be ignored");
        }

        chain.doFilter(req, res);
    }
}
