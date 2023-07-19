package com.soloProject.server.global.auth.config;


import com.soloProject.server.global.auth.filter.JwtAuthenticationFilter;
import com.soloProject.server.global.auth.filter.JwtVerificationFilter;
import com.soloProject.server.global.auth.handler.MemberAccessDeniedHandler;
import com.soloProject.server.global.auth.handler.MemberAuthenticationEntryPoint;
import com.soloProject.server.global.auth.handler.MemberAuthenticationFailureHandler;
import com.soloProject.server.global.auth.handler.MemberAuthenticationSuccessHandler;
import com.soloProject.server.global.auth.jwt.JwtTokenizer;
import com.soloProject.server.global.auth.utils.CustomAuthorityUtils;
import com.soloProject.server.global.redis.RedisService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils customAuthorityUtils;
    private final RedisService redisService;

    public SecurityConfiguration(JwtTokenizer jwtTokenizer,
                                 CustomAuthorityUtils customAuthorityUtils,
                                 RedisService redisService) {
        this.jwtTokenizer = jwtTokenizer;
        this.customAuthorityUtils = customAuthorityUtils;
        this.redisService = redisService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()//CSRF 공격에 대한 Spring Security에 대한 설정을 비활성화
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //세션을 생성하지 않도록 설정
                .and()
                .formLogin().disable() //폼 로그인 방식을 비활성화
                .httpBasic().disable() //HTTP Basic 인증 방식을 비활성화
                .exceptionHandling()
                .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
                .accessDeniedHandler(new MemberAccessDeniedHandler())
                .and()
                .apply(new CustomFilterConfigurer())//Custom Configurer를 추가해 내가 만든 Configuration을 추가
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        //.antMatchers(HttpMethod.PATCH, "/members/**").hasRole("USER") //이런식으로 역할에 따른 제한 가능
                        .anyRequest().permitAll() //우선은 모든 HTTP request 요청에 대해서 접근을 허용하도록 설정
                );

        return http.build();
    }

    //PasswordEncoder Bean 객체를 생성
    @Bean
    public PasswordEncoder passwordEncoder() { //memberService에서 DI 받아 사용
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("*"));
        configuration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    //CustomFilterConfigurer는 우리가 구현한 JwtAuthenticationFilter를 등록하는 역할
    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            //getSharedObject(AuthenticationManager.class)를 통해 AuthenticationManager의 객체를 얻
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

            //JwtAuthenticationFilter를 생성하면서 JwtAuthenticationFilter에서 사용되는 AuthenticationManager와 JwtTokenizer를 DI
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer, redisService);
            //디폴트 로그인 request URL 변경
            jwtAuthenticationFilter.setFilterProcessesUrl("/members/login");

            //AuthenticationSuccessHandler와 AuthenticationFailureHandler를 JwtAuthenticationFilter에 등록
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, customAuthorityUtils);

            //addFilter() 메서드를 통해 Filter들을 Spring Security Filter Chain에 추가
            builder.addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
        }
    }


}
