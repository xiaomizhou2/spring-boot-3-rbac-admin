package com.xiaomizhou.admin.domain.auth.jwt;

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
     * token过期时间 默认2小时
     */
    private long expiration = 2 * 60 * 60 * 1000;
    /**
     * 刷新token过期时间 默认8小时
     */
    private long refreshExpiration = 8 * 60 * 60 * 1000;
}
