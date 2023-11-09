package com.xiaomizhou.admin.auth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/11/8
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.security.jwt")
public class JwtProperties {
    /**
     * 密钥
     */
    private String secretKey;
    /**
     * token过期时间
     */
    private long expiration;
    /**
     * 刷新token过期时间
     */
    private long refreshExpiration;
}
