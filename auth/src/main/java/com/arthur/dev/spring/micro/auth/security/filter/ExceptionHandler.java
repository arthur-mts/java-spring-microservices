package com.arthur.dev.spring.micro.auth.security.filter;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.core.Authentication;

public class ExceptionHandler implements SecurityExpressionHandler {
    @Override
    public ExpressionParser getExpressionParser() {
        return null;
    }

    @Override
    public EvaluationContext createEvaluationContext(Authentication authentication, Object o) {
        return null;
    }
}
