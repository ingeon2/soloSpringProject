package com.soloProject.server.global.auth.filter;

import com.soloProject.server.global.auth.jwt.JwtTokenizer;
import com.soloProject.server.global.auth.utils.CustomAuthorityUtils;
import com.soloProject.server.global.redis.service.RedisService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

//JWT에 대해 검증 작업을 수행
public class JwtVerificationFilter extends OncePerRequestFilter {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final RedisService redisService;


    public JwtVerificationFilter(JwtTokenizer jwtTokenizer,
                                 CustomAuthorityUtils authorityUtils,
                                 RedisService redisService) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
        this.redisService = redisService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, ServletException, IOException {
        try {
            Map<String, Object> claims = verifyJws(request);


            if(verifyRefreshToken(claims, redisService)) { //받은 토큰이 유효하다면 Spring context로 가자!
                setAuthenticationToContext(claims);
            }
            else {
                throw new Exception("선생님 토큰이 만료되셨습니다");
            }

        } catch (ExpiredJwtException ee) {
            request.setAttribute("exception", ee);
        } catch (Exception e) {
            request.setAttribute("exception", e);
        }

        filterChain.doFilter(request, response);
    }


    //true이면 해당 Filter의 동작을 수행하지 않고 다음 Filter로 건너뛰도록 해줌
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization");

        return authorization == null || !authorization.startsWith("Bearer");
    }

    // JWT를 검증하는 데 사용되는 private 메서드
    private Map<String, Object> verifyJws(HttpServletRequest request) {
        String jws = request.getHeader("Authorization").replace("Bearer ", "");
        
        // JWT 서명을 검증하기 위한 Secret Key를 얻음
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        //JWT에서 Claims를 파싱
        //Claims가 정상적으로 파싱이 되면 서명 검증 역시 자연스럽게 성공
        Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();

        return claims;
    }

    private void setAuthenticationToContext(Map<String, Object> claims) {
        // JWT에서 파싱 한 Claims에서 username을 얻음
        String username = (String) claims.get("username");

        //JWT의 Claims에서 얻은 권한 정보를 기반으로 List<GrantedAuthority>를 생성
        List<GrantedAuthority> authorities = authorityUtils.createAuthorities((List)claims.get("roles"));

        //username과 List<GrantedAuthority>를 포함한 Authentication 객체를 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);

        //SecurityContext에 Authentication 객체를 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private boolean verifyRefreshToken(Map<String, Object> claims, RedisService redisService) {
        // 클라이언트가 전달한 refreshToken
        String clientRefreshToken = (String) claims.get("refreshToken");
        
        // redis에 저장해놓았던 기존의 토큰
        long memberId = (Long) claims.get("memberId");
        String refreshTokenFromRedis = redisService.getRefreshTokenFromRedis(memberId);

        if(refreshTokenFromRedis.equals(null) || !clientRefreshToken.equals(refreshTokenFromRedis)) return false;
        return true;
    }
}
