package io.github.byference.sso.client.config;

import io.github.byference.sso.client.authentication.OAuth2ClientAuthenticationProcessingFilter;
import io.github.byference.sso.client.handler.OAuth2ClientAuthenticationSuccessHandler;
import io.github.byference.sso.client.properties.OAuth2SsoProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author byference
 * @since 2019-11-17
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OAuth2SsoClientWebSecurityConfigurer extends WebSecurityConfigurerAdapter {


    @Autowired
    private OAuth2SsoProperties OAuth2SsoProperties;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AuthenticationProvider ssoClientAuthenticationProvider;


    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers(OAuth2SsoProperties.getPermitUrls()).permitAll()
                .anyRequest().authenticated();

        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint);

        // http.logout().addLogoutHandler();
        http.apply(new SsoClientAuthenticationSecurityConfig(ssoClientAuthenticationProvider));
    }


    public static class SsoClientAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

        private final AuthenticationProvider ssoClientAuthenticationProvider;

        public SsoClientAuthenticationSecurityConfig(AuthenticationProvider ssoClientAuthenticationProvider) {
            this.ssoClientAuthenticationProvider = ssoClientAuthenticationProvider;
        }

        @Override
        public void configure(HttpSecurity http) {

            OAuth2ClientAuthenticationProcessingFilter oAuth2ClientAuthenticationProcessingFilter = new OAuth2ClientAuthenticationProcessingFilter();
            oAuth2ClientAuthenticationProcessingFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
            oAuth2ClientAuthenticationProcessingFilter.setAuthenticationSuccessHandler(new OAuth2ClientAuthenticationSuccessHandler());
            // oAuth2ClientAuthenticationProcessingFilter.setAuthenticationFailureHandler();
            // oAuth2ClientAuthenticationProcessingFilter.setUserDetailsService(userDetailsService);
            http.authenticationProvider(ssoClientAuthenticationProvider)
                    .addFilterAfter(oAuth2ClientAuthenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class);
        }
    }
}
