package com.xiaomizhou.admin.domain.auth;

import com.xiaomizhou.admin.domain.menu.MenuEntity;
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

    private Set<MenuEntity> permissions;

    public UserInfo() {

    }

    public UserInfo(UserEntity user, Collection<GrantedAuthority> authorities, Set<MenuEntity> permissions) {
        BeanUtils.copyProperties(user, this);
        this.authorities = authorities;
        this.permissions = permissions;
    }

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
}
