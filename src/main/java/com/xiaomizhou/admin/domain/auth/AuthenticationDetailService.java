package com.xiaomizhou.admin.domain.auth;

import com.xiaomizhou.admin.domain.permission.PermissionEntity;
import com.xiaomizhou.admin.domain.menu.MenuEntity;
import com.xiaomizhou.admin.domain.role.RoleEntity;
import com.xiaomizhou.admin.domain.user.UserEntity;
import com.xiaomizhou.admin.domain.user.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/11/14
 */
public class AuthenticationDetailService implements UserDetailsService {
    @Resource
    private UserRepository repository;

    @Cacheable(key = "#username", cacheNames = "authentication:user")
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("用户" + username + "不存在"));
        Set<MenuEntity> permissions = new HashSet<>();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            for (RoleEntity role : user.getRoles()) {
                if (role.getMenus() != null && !role.getMenus().isEmpty()) {
                    permissions.addAll(role.getMenus());
                    for (MenuEntity permission : permissions) {
                        authorities = permission.getPermissions().stream()
                                .map(PermissionEntity::getCode)
                                .distinct()
                                .map(code -> new SimpleGrantedAuthority(code))
                                .collect(Collectors.toList());
                    }
                }
            }
        }
        UserInfo userInfo = new UserInfo(user, authorities, permissions);
        return userInfo;
    }
}
