package skuc.io.skucioapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import skuc.io.skucioapp.api_contracts.requests.Authentication.LoginRequest;
import skuc.io.skuciocore.models.authentication.AuthenticatedUser;
import skuc.io.skuciocore.services.AuthenticationService;

@RestController
@RequestMapping("authenticate")
public class AuthenticationController {

    private AuthenticationService _authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        _authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticatedUser> login(@RequestBody LoginRequest loginRequest) {
        var authenticatedUser = _authenticationService.authenticate(loginRequest.getEmail(),
            loginRequest.getPassword());

        return ResponseEntity.ok(authenticatedUser);
    }
}
