package skuc.io.skuciocore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import skuc.io.skuciocore.models.authentication.AuthenticatedUser;
import skuc.io.skuciocore.security.utils.JwtUtil;

@Service
public class AuthenticationService {

    private AuthenticationManager _authenticationManager;
    private JwtUtil _jwtUtil;

    private UserService _userService;
    

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService) {
        _authenticationManager = authenticationManager;
        _jwtUtil = jwtUtil;
        _userService = userService;
    }

    public AuthenticatedUser authenticate(String email, String password) {
        var user = _userService.getByEmail(email);
        var jwt = generateJwt(email, password, user.getId(), user.getGroupId());

        return new AuthenticatedUser(user, "Bearer " + jwt);
    }
    
    private String generateJwt(String email, String password, String userId, String groupId) {
        var authentication = getAuthentication(email, password);

        return _jwtUtil.generateToken(authentication, userId, groupId);
    }

    private Authentication getAuthentication(String email, String password) {
        var usernamePasswordToken = new UsernamePasswordAuthenticationToken(email, password);
        var authentication = _authenticationManager.authenticate(usernamePasswordToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }
}
