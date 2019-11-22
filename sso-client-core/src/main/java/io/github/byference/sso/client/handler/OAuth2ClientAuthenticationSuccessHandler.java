package io.github.byference.sso.client.handler;

import io.github.byference.sso.client.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * OAuth2ClientAuthenticationSuccessHandler
 *
 * @author byference
 * @since 2019/11/19
 */
@Slf4j
public class OAuth2ClientAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        log.info("onAuthenticationSuccess: {}", request.getRequestURL());
        Map<String, String> stringStringMap = HttpUtil.transQueryString2Map(request.getQueryString());
        String next = stringStringMap.get("next");
        if (!StringUtils.isEmpty(next)) {
            response.sendRedirect(next);
        }
    }

}