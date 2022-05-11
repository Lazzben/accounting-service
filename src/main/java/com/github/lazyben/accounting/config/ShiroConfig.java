package com.github.lazyben.accounting.config;

import com.github.lazyben.accounting.manager.UserInfoManagerImpl;
import com.github.lazyben.accounting.shiro.CustomFormAuthenticationFilter;
import com.github.lazyben.accounting.shiro.CustomHttpFilter;
import com.github.lazyben.accounting.shiro.CustomShiroFilterFactoryBean;
import lombok.val;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {
    private static final String HASH_ALGORITHM_NAME = "SHA-256";

    @Bean
    public SecurityManager securityManager(Realm realm) {
        val securityManager = new DefaultWebSecurityManager(realm);
        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        val shiroFilterFactoryBean = new CustomShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        val filters = shiroFilterFactoryBean.getFilters();
        filters.put("custom", new CustomHttpFilter());
        filters.put("authc", new CustomFormAuthenticationFilter());

        val filterChainDefinitionMap = new LinkedHashMap<String, String>();

        filterChainDefinitionMap.put("/v1.0/session", "anon");
        filterChainDefinitionMap.put("/v1.0/userinfo/**::POST", "custom");
        filterChainDefinitionMap.put("/v1.0/tag/**::POST", "custom");
        filterChainDefinitionMap.put("/v1.0/tag/**::PUT", "custom");

        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public HashedCredentialsMatcher matcher() {
        val matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName(HASH_ALGORITHM_NAME);
        matcher.setHashIterations(UserInfoManagerImpl.HASH_ITERATIONS);
        matcher.setHashSalted(true);
        matcher.setStoredCredentialsHexEncoded(false);
        return matcher;
    }
}
