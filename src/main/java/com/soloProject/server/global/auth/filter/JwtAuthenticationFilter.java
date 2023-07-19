package com.soloProject.server.global.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soloProject.server.domain.member.entity.Member;
import com.soloProject.server.global.auth.dto.LoginDto;
import com.soloProject.server.global.auth.jwt.JwtTokenizer;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//로그인 인증 요청을 처리하는 CustomSecurityFilter인 JwtAuthenticationFilter
//UsernamePasswordAuthenticationFilter를 상속.
//UsernamePasswordAuthenticationFilter는 폼 로그인 방식에서 사용하는 디폴트 Security Filter로써,
//폼 로그인이 아니더라도 Username/Password 기반의 인증을 처리하기 위해 UsernamePasswordAuthenticationFilter를 확장해서 구현
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenizer jwtTokenizer;



    //DI 받은 AuthenticationManager는 로그인 인증 정보(Username/Password)를 전달받아 UserDetailsService와 인터랙션 한 뒤 인증 여부를 판단합니다.
    //DI 받은 JwtTokenizer는 클라이언트가 인증에 성공할 경우, JWT를 생성 및 발급하는 역할
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenizer jwtTokenizer) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenizer = jwtTokenizer;
    }


    //메서드 내부에서 인증을 시도하는 로직을 구현
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        //클라이언트에서 전송한 Username과 Password를 DTO 클래스로 역직렬화 하기 위해 ObjectMapper 인스턴스를 생성
        ObjectMapper objectMapper = new ObjectMapper();
        //역직렬화
        LoginDto loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class);

        // Username과 Password 정보를 포함한 UsernamePasswordAuthenticationToken을 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        //UsernamePasswordAuthenticationToken을 AuthenticationManager에게 전달하면서 인증 처리를 위임
        return authenticationManager.authenticate(authenticationToken);
    }


    //클라이언트의 인증 정보를 이용해 인증에 성공할 경우 호출
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws ServletException, IOException {
        //authResult.getPrincipal()로 Member 엔티티 클래스의 객체를 얻음
        Member member = (Member) authResult.getPrincipal();

        //토큰 생성
        String accessToken = delegateAccessToken(member);
        String refreshToken = delegateRefreshToken(member);

        //토큰들을 헤더에 담음
        response.setHeader("Authorization", "Bearer " + accessToken);
        response.setHeader("Refresh", refreshToken);

        //MemberAuthenticationSuccessHandler의 onAuthenticationSuccess() 메서드를 호출하기위해
        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }


    //Access Token을 생성하는 구체적인 로직
    private String delegateAccessToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", member.getEmail());
        claims.put("roles", member.getRoles());

        String subject = member.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }

    //Refresh Token을 생성하는 구체적인 로직
    private String delegateRefreshToken(Member member) {
        String subject = member.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        return refreshToken;
    }


}
