package com.example.vkr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(1)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UnauthorizedEntryPoint unauthorizedEntryPoint;

    @Autowired
    private AuthProvider authProvider;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable()
//                .requestMatcher(new OrRequestMatcher(requestMatchers))
                .authorizeRequests()
                    .antMatchers("/auth/**", "/ship/**", "/excel/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint)
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(blackListFilterBean(), JwtAuthenticationFilter.class);
    }

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtBlackListFilter blackListFilterBean() {
        return new JwtBlackListFilter();
    }

    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() {
        return new JwtAuthenticationFilter();
    }
}