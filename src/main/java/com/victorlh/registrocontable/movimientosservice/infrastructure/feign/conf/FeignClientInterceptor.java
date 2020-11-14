package com.victorlh.registrocontable.movimientosservice.infrastructure.feign.conf;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.victorlh.registrocontable.common.securitycommon.AuthenticationToken;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String TOKEN_TYPE = "Bearer";

	@Override
	public void apply(RequestTemplate requestTemplate) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication instanceof AuthenticationToken) {
			AuthenticationToken authToken = (AuthenticationToken) authentication;
			requestTemplate.header(AUTHORIZATION_HEADER, String.format("%s %s", TOKEN_TYPE, authToken.getCredentials()));
		}
	}
}