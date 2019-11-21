package io.github.byference.sso.client.handler;

import io.github.byference.sso.client.properties.OAuth2SsoProperties;
import io.github.byference.sso.client.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * OAuth2ClientAuthenticationEntryPoint
 *
 * @author byference
 * @since 2019/11/19
 */
@Slf4j
public class OAuth2ClientAuthenticationEntryPoint implements AuthenticationEntryPoint {


    private final OAuth2SsoProperties OAuth2SsoProperties;

    public OAuth2ClientAuthenticationEntryPoint(OAuth2SsoProperties OAuth2SsoProperties) {
        this.OAuth2SsoProperties = OAuth2SsoProperties;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {

        String requestURI = request.getRequestURI();
        if ("/error".equalsIgnoreCase(requestURI)) {
            requestURI = "/";
        }

        // 访问当前应用认证失败，重定向到SSO 认证服务器（获取授权码）
        String redirectUri = OAuth2SsoProperties.getClient().getRedirectUri() + "?next=" + requestURI;
        String oauthAuthorizeUrl = HttpUtil.getOauthAuthorizeUrl(OAuth2SsoProperties.getClient().getUserAuthorizationUri(), OAuth2SsoProperties.getClient().getClientId(), redirectUri);
        log.info("==> send redirect to: {}", oauthAuthorizeUrl);
        response.sendRedirect(oauthAuthorizeUrl);
    }

}
