package com.vk.oms.config.security.expressions;

import org.springframework.security.access.expression.SecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

@Component
public class CustomWebSecurityExpressionHandler extends OAuth2WebSecurityExpressionHandler {

    private final AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();

    @Override
    protected SecurityExpressionOperations createSecurityExpressionRoot(
            Authentication authentication, FilterInvocation fi) {
        final CustomSecurityExpressionRoot root = new CustomSecurityExpressionRoot(authentication);

        root.setPermissionEvaluator(getPermissionEvaluator());
        root.setTrustResolver(trustResolver);
        return root;
    }
}
