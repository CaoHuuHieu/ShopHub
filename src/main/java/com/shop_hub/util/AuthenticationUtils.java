package com.shop_hub.util;

import com.shop_hub.exception.UnauthenticatedException;
import com.shop_hub.filter.AuthenticationToken;
import com.shop_hub.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;

public class AuthenticationUtils {

    private AuthenticationUtils() {}

    public static User getCurrentUser(){
        AuthenticationToken authenticationToken = getAuthenticationToken();
        return (User) authenticationToken.getDetails();
    }

    public static AuthenticationToken getAuthenticationToken(){
        AuthenticationToken authenticationToken = (AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if(authenticationToken == null)
            throw new UnauthenticatedException();
        return authenticationToken;
    }

    public static String getCurrentRole(){
        User admin = getCurrentUser();
        return admin.getRole().getRoleCode();
    }

    public static String getCurrentOrgId(){
        AuthenticationToken authenticationToken = getAuthenticationToken();
        return authenticationToken.getCurrentOrgId();
    }

    public static List<String> getCurrentVenueIds(){
        AuthenticationToken authenticationToken = getAuthenticationToken();
        String venueIds = authenticationToken.getCurrentVenueIds();
        return Arrays.stream(venueIds.split(",")).toList();
    }


}
