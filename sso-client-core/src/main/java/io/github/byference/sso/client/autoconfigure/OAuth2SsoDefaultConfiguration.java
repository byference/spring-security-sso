package io.github.byference.sso.client.autoconfigure;

import io.github.byference.sso.client.authentication.OAuth2ClientAuthenticationProvider;
import io.github.byference.sso.client.handler.OAuth2ClientAuthenticationEntryPoint;
import io.github.byference.sso.client.properties.OAuth2SsoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.client.RestTemplate;

/**
 * OAuth2SsoDefaultConfiguration
 *
 * @author byfenrece
 * @since 2019/11/20
 */
@Configuration
@EnableConfigurationProperties(OAuth2SsoProperties.class)
public class OAuth2SsoDefaultConfiguration {

    private final OAuth2SsoProperties OAuth2SsoProperties;

    public OAuth2SsoDefaultConfiguration(OAuth2SsoProperties OAuth2SsoProperties) {
        this.OAuth2SsoProperties = OAuth2SsoProperties;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public AuthenticationProvider ssoClientAuthenticationProvider() {
        return new OAuth2ClientAuthenticationProvider(OAuth2SsoProperties, restTemplate());
    }

    @Bean
    public AuthenticationEntryPoint ssoClientAuthenticationEntryPoint() {
        return new OAuth2ClientAuthenticationEntryPoint(OAuth2SsoProperties);
    }

    @Bean
    public OAuth2ClientContextFilter oAuth2ClientContextFilter() {
        return new OAuth2ClientContextFilter();
    }

}
