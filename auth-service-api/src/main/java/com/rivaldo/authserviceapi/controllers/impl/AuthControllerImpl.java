package com.rivaldo.authserviceapi.controllers.impl;

import br.com.rivaldo.models.requests.AuthenticateRequest;
import br.com.rivaldo.models.requests.RefreshTokenRequest;
import br.com.rivaldo.models.responses.AuthenticationResponse;import br.com.rivaldo.models.responses.RefreshTokenResponse;
import com.rivaldo.authserviceapi.controllers.AuthController;
import com.rivaldo.authserviceapi.security.JWTAuthenticationImpl;
import com.rivaldo.authserviceapi.services.RefreshTokenService;
import com.rivaldo.authserviceapi.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final JWTUtils jwtUtils;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final RefreshTokenService refreshTokenService;

    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticateRequest request) throws Exception {
        return ResponseEntity.ok().body(
                new JWTAuthenticationImpl(jwtUtils, authenticationConfiguration.getAuthenticationManager())
                        .authenticate(request)
                        .withRefreshToken(refreshTokenService.save(request.email()).getId())
        );
    }

    @Override
    public ResponseEntity<RefreshTokenResponse> refreshToken(RefreshTokenRequest request) {
        return ResponseEntity.ok().body(
                refreshTokenService.refreshToken(request.refreshToken()));
    }
}
