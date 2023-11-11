package com.xiaomizhou.admin.domain.auth;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/11/11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_authorization")
public class AuthenticationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String principalName;

    private String accessToken;

    private Date accessTokenIssued;

    private Date accessTokenExpires;

    private String accessTokenType;

    private String refreshToken;

    private Date refreshTokenIssued;

    private Date refreshTokenExpires;

    private Boolean deleted;
}
