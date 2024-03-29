package com.example.project_sem_4.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    private static String HEADER = "Authorization";
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//        // Lấy token từ header
//        String token = httpServletRequest.getHeader(HEADER);
//
//        // Parse thông tin từ token
//        Claims claims = jwtTokenUtil.getClaimsFromToken(token);
//        if (claims == null) {
//            filterChain.doFilter(httpServletRequest, httpServletResponse);
//            return;
//        }
//
//        // Kiểm tra hạn token
//        Date expiration = claims.getExpiration();
//        if (expiration.before(new Date())) {
//            filterChain.doFilter(httpServletRequest, httpServletResponse);
//            return;
//        }
//
//        // Tạo object Authentication
//        UsernamePasswordAuthenticationToken authenticationObject = getAuthentication(claims);
//        if (authenticationObject == null) {
//            filterChain.doFilter(httpServletRequest, httpServletResponse);
//            return;
//        }
//
//        // Xác thực thành công, lưu object Authentication vào SecurityContextHolder
//        SecurityContextHolder.getContext().setAuthentication(authenticationObject);
//        filterChain.doFilter(httpServletRequest, httpServletResponse);
//    }
//
//    private UsernamePasswordAuthenticationToken getAuthentication(Claims claims) {
//        String username = claims.getSubject();
//
//        if (username != null) {
//            UserDetails user = userDetailsService.loadUserByUsername(username);
//            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
//        }
//        return null;
//    }

    @Value("${jwt.header.string}")
    public String HEADER_STRING;

    @Value("${jwt.token.prefix}")
    public String TOKEN_PREFIX;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);
        String email = null;
        String authToken = null;
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            authToken = header.replace(TOKEN_PREFIX,"");
            try {
                email = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                logger.error("An error occurred while fetching Username from Token", e);
            } catch (ExpiredJwtException e) {
                logger.warn("The token has expired", e);
            } catch(SignatureException e){
                logger.error("Authentication Failed. Username or Password not valid.");
            }
        } else {
            logger.warn("Couldn't find bearer string, header will be ignored");
        }
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthenticationToken(authToken, SecurityContextHolder.getContext().getAuthentication(), userDetails);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                logger.info("authenticated user " + email + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(req, res);
    }
}
