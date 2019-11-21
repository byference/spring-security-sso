package io.github.byference.sso.client.authentication;

import io.github.byference.sso.client.properties.OAuth2SsoProperties;
import io.github.byference.sso.client.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * OAuth2ClientAuthenticationProvider
 *
 * @author byference
 * @since 2019/11/19
 */
@Slf4j
public class OAuth2ClientAuthenticationProvider implements AuthenticationProvider {

    private final OAuth2SsoProperties OAuth2SsoProperties;

    private final RestTemplate restTemplate;

    public OAuth2ClientAuthenticationProvider(OAuth2SsoProperties OAuth2SsoProperties, RestTemplate restTemplate) {
        this.OAuth2SsoProperties = OAuth2SsoProperties;
        this.restTemplate = restTemplate;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        OAuth2ClientAuthenticationToken authenticationToken = (OAuth2ClientAuthenticationToken) authentication;
        String queryString = (String) authenticationToken.getPrincipal();

        // 拦截 GET: /login ，通过授权码后，获取access_token
        Map<String, String> queryString2Map = HttpUtil.transQueryString2Map(queryString);
        String code = queryString2Map.get("code");

        String redirectUri = OAuth2SsoProperties.getClient().getRedirectUri() + "?next=" + queryString2Map.get("next");
        String oauthTokenUrl = HttpUtil.getOauthTokenUrl(OAuth2SsoProperties.getClient().getAccessTokenUri(), OAuth2SsoProperties.getClient().getClientId(), OAuth2SsoProperties.getClient().getClientSecret(), redirectUri, code);

        log.info("==> send post to: {}", oauthTokenUrl);
        ResponseEntity<OAuth2AuthenticationResult> forEntity = restTemplate.postForEntity(oauthTokenUrl, null, OAuth2AuthenticationResult.class);
        // OAuth2Request
        log.info("<== get body from entity: {}", forEntity.getBody());


        OAuth2ClientAuthenticationToken authenticationResult = new OAuth2ClientAuthenticationToken("admin",
                forEntity.getBody(),
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return OAuth2ClientAuthenticationToken.class.isAssignableFrom(authentication);
    }

}