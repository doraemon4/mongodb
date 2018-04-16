package com.study.mongodb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@AllArgsConstructor
public class JwtUser implements UserDetails {
    private final String id;
    private final String username;
    private final String password;
    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Date lastPasswordResetDate;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    @JsonIgnore
    public String getId() {
        return id;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() { // 账户是否未过期
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {  // 账户是否未锁定
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {   //密码是否未过期
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {  // 账户是否激活
        return true;
    }

    @JsonIgnore
    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

}
