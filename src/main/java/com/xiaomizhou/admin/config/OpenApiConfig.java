package com.xiaomizhou.admin.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * @author xiaomizhou
 * @date 2023/10/27
 * @email 521jx123@gmail.com
 */
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "xiaomizhou",
                        email = "521jx123@gmail.com",
                        url = "https://github.com/xiaomizhou2"
                ),
                description = "RBAC模型的简单权限系统API文档",
                title = "RBAC权限系统API文档",
                version = "1.0.0"
        ),
        servers = {
                @Server(
                        description = "本地环境",
                        url = "http://127.0.0.1:8080"
                )
        }
)
public class OpenApiConfig {
}
