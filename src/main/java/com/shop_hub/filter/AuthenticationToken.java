package com.shop_hub.filter;

import com.shop_hub.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class AuthenticationToken implements Authentication {

    private final User user;
    private final String currentOrgId;
    private final String currentVenueIds;

    public AuthenticationToken(User user, String currentOrgId, String currentVenueIds) {
        this.user = user;
        this.currentOrgId = currentOrgId;
        this.currentVenueIds = currentVenueIds;
    }

    public String getCurrentOrgId() {
        return currentOrgId;
    }

    public String getCurrentVenueIds() {
        return currentVenueIds;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.user.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return this.user;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        // TODO document why this method is empty
    }

    @Override
    public boolean equals(Object another) {
        return false;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String getName() {
        return "";
    }
}
