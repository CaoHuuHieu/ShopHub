package com.shop_hub.filter;

import com.shop_hub.model.Admin;
import com.shop_hub.service.impl.AdminService;
import com.shop_hub.util.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final AdminService adminService;

    @Value("${security.public_url}")
    private String publicUrls;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if(!isPublicUrl(request)) {
            try {
                String authValue = request.getHeader("Authorization");
                String jwt = authValue.split(" ")[1];
                if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                    String username = jwtUtils.getUserNameFromJwtToken(jwt);
                    UserDetails userDetails = adminService.loadUserByUsername(username);
                    AuthenticationToken authenticationToken = new AuthenticationToken((Admin)userDetails);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (Exception e) {
                logger.error("Cannot set user authentication: {}", e);
            }
        }
        filterChain.doFilter(request, response);
    }

    public boolean isPublicUrl(HttpServletRequest request){
        List<String> urls = Arrays.stream(publicUrls.split(",")).toList();
        String currentUri = request.getRequestURI();
        return urls.contains(currentUri);
    }

}
