package com.xiaomizhou.admin.domain.auth;

import com.xiaomizhou.admin.domain.menu.MenuEntity;
import com.xiaomizhou.admin.domain.permission.PermissionEntity;
import com.xiaomizhou.admin.domain.user.UserEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/11/14
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UserInfo extends UserEntity implements UserDetails, Serializable {

    private Collection<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserInfo user(UserEntity user) {
        BeanUtils.copyProperties(user, this);
        return this;
    }

    public UserInfo authorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
        return this;
    }
}
