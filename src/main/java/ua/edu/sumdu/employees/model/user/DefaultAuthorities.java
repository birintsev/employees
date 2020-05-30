package ua.edu.sumdu.employees.model.user;

import org.springframework.security.core.GrantedAuthority;

public enum DefaultAuthorities implements GrantedAuthority {
    RO_USER("RO_USER"), ADMINISTRATOR("ADMINISTRATOR");
    private final String authorityName;

    DefaultAuthorities(String authorityName) {
        this.authorityName = authorityName;
    }

    @Override
    public String getAuthority() {
        return authorityName;
    }

    @Override
    public String toString() {
        return getAuthority();
    }
}
