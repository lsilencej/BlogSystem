package com.lsilencej.blogsystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.RequestDispatcher;
import javax.sql.DataSource;
import java.net.URL;
import java.util.Collection;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    @Value("${COOKIE.VALIDITY}")
    private Integer COOKIE_VALIDITY;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/","/page/**","/article/**","/login").permitAll()
                .antMatchers("/back/**","/assets/**","/user/**","/article_img/**").permitAll()
                .antMatchers("/admin/**").hasRole("admin")
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login")
                .usernameParameter("username").passwordParameter("password")
                .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    String url = httpServletRequest.getParameter("url");
                    RequestCache requestCache = new HttpSessionRequestCache();
                    SavedRequest savedRequest = requestCache.getRequest(httpServletRequest,httpServletResponse);
                    if(savedRequest !=null){
                        httpServletResponse.sendRedirect(savedRequest.getRedirectUrl());
                    } else if(url != null && !url.equals("")){
                        URL fullURL = new URL(url);
                        httpServletResponse.sendRedirect(fullURL.getPath());
                    }else {
                        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                        boolean isAdmin = authorities.contains(new SimpleGrantedAuthority("ROLE_admin"));
                        if(isAdmin){
                            httpServletResponse.sendRedirect("/admin");
                        }else {
                            httpServletResponse.sendRedirect("/");
                        }
                    }
                })
                .failureHandler((httpServletRequest, httpServletResponse, e) -> {
                    String url = httpServletRequest.getParameter("url");
                    httpServletResponse.sendRedirect("/login?error&url="+url);
                });
        http.rememberMe().alwaysRemember(true).tokenValiditySeconds(COOKIE_VALIDITY);
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/");
        http.exceptionHandling().accessDeniedHandler((httpServletRequest, httpServletResponse, e) -> {
            RequestDispatcher dispatcher = httpServletRequest.getRequestDispatcher("/errorPage/comm/error_403");
            dispatcher.forward(httpServletRequest, httpServletResponse);
        });
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String userSQL ="select username,password,valid from t_user where username = ?";
        String authoritySQL ="select u.username,a.authority from t_user u,t_authority a," +
                             "t_user_authority ua where ua.user_id=u.id " +
                             "and ua.authority_id=a.id and u.username =?";
        auth.jdbcAuthentication().passwordEncoder(encoder)
                .dataSource(dataSource)
                .usersByUsernameQuery(userSQL)
                .authoritiesByUsernameQuery(authoritySQL);
    }
}

