package com.shop_hub.filter;

import com.shop_hub.model.User;
import com.shop_hub.service.impl.AdminService;
import com.shop_hub.util.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    private final static String X_ORG_ID = "X-Org-Id";
    private final static String X_VENUE_IDS = "X-Venue-Ids";
    private final static String AUTHORIZATION = "Authorization";
    private final static String AUTHORIZATION_TYPE = "Bearer ";

    @Value("${security.public_url}")
    private String publicUrls;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if(!isPublicUrl(request)) {
            try {
                String authValue = getValueFromHeader(request, AUTHORIZATION);
                String orgId = getValueFromHeader(request, X_ORG_ID);
                String venueIds = getValueFromHeader(request, X_VENUE_IDS);

                String token = null;
                if(authValue != null && authValue.startsWith(AUTHORIZATION_TYPE))
                    token = authValue.split(" ")[1];
                if (token != null && jwtUtils.validateJwtToken(token)) {
                    String username = jwtUtils.getUserNameFromJwtToken(token);
                    UserDetails user = adminService.loadUserByUsername(username);
                    AuthenticationToken authenticationToken = new AuthenticationToken((User) user, orgId, venueIds);
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

    public String getValueFromHeader(HttpServletRequest request, String headerKey) {
        return request.getHeader(headerKey);
    }

}
