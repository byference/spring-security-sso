package io.github.byference.sso.client.authentication;

import io.github.byference.sso.client.util.HttpUtil;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * OAuth2ClientAuthenticationProcessingFilter
 *
 * @author byference
 * @since 2019/11/20
 */
public class OAuth2ClientAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {


    public OAuth2ClientAuthenticationProcessingFilter() {
        super(new AntPathRequestMatcher("/login", "GET"));
    }


    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        if (!"GET".equals(request.getMethod())) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String queryString = request.getQueryString();
        Map<String, String> queryString2Map = HttpUtil.transQueryString2Map(queryString);

        if (!queryString2Map.containsKey("code")) {
            throw new InsufficientAuthenticationException("Authentication request not supported: "
                    + request.getRequestURI() + "?" + queryString);
        }

        OAuth2ClientAuthenticationToken authRequest = new OAuth2ClientAuthenticationToken(request.getQueryString(), null);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected void setDetails(HttpServletRequest request, OAuth2ClientAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

}
