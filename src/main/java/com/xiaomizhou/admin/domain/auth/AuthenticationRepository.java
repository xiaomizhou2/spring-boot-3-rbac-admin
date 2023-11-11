package com.xiaomizhou.admin.domain.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/11/11
 */
public interface AuthenticationRepository extends JpaRepository<AuthenticationEntity, Integer> {

    @Query(value = "UPDATE AuthenticationEntity t SET t.deleted = true WHERE t.principalName = :principalName")
    void deleteByPrincipalName(String principalName);

    int countAllByPrincipalNameAndDeleted(String principalName, Boolean deleted);
}
