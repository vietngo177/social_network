package com.ghtk.social_network.application.controller;

import com.ghtk.social_network.application.request.CreateNewPasswordRequest;
import com.ghtk.social_network.application.request.ForgotPasswordRequest;
import com.ghtk.social_network.application.request.LoginRequest;
import com.ghtk.social_network.application.request.RegisterRequest;
import com.ghtk.social_network.application.response.LoginResponse;
import com.ghtk.social_network.domain.model.User;
import com.ghtk.social_network.exception.customexception.IdInvalidException;
import com.ghtk.social_network.domain.port.api.UserServicePort;
import com.ghtk.social_network.util.SecurityUtil;
import com.ghtk.social_network.util.annotation.ApiMessage;
import com.ghtk.social_network.util.mapper.UserMapper;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securityUtil;
    private final UserServicePort userServicePort;
    private final UserMapper userMapper;

    public static class Utility {
        public static String getSiteURL(HttpServletRequest request) {
            String siteURL = request.getRequestURL().toString();
            return siteURL.replace(request.getServletPath(), "");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        // Nạp input gồm username/password vào Security
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword());

        // xác thực người dùng => cần viết hàm loadUserByUsername
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        LoginResponse res = new LoginResponse();
        User userCurrentDB = this.userServicePort.findUserByEmail(loginRequest.getEmail());
        if (userCurrentDB == null) {
            throw new BadCredentialsException("credentials invalid");
        }

        LoginResponse.UserLogin userLogin = new LoginResponse.UserLogin(userCurrentDB.getEmail(), userCurrentDB.getUsername());
        res.setUser(userLogin);

        // Truyen thong tin dang nhap cua nguoi dung
        String access_token = this.securityUtil.createAccessToken(userCurrentDB.getEmail(), res.getUser());
        res.setAccessToken(access_token);

        // Create refresh token
        String refreshToken = this.securityUtil.createRefreshToken(loginRequest.getEmail(), res);

        // Update user
        this.userServicePort.updateRefreshToken(refreshToken, loginRequest.getEmail());

        // set cookies
        ResponseCookie responseCookie = ResponseCookie
                .from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(864000)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(res);
    }

    @GetMapping("/account")
    @ApiMessage("fetch account")
    public ResponseEntity<LoginResponse.UserGetAccount> getAccount() {
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "";

        User userCurrentDB = this.userServicePort.findUserByEmail(email);
        LoginResponse.UserLogin userLogin = new LoginResponse.UserLogin();
        LoginResponse.UserGetAccount userGetAccount = new LoginResponse.UserGetAccount(userLogin);
        if (userCurrentDB != null) {
            userLogin.setEmail(email);
            userLogin.setUsername(userCurrentDB.getUsername());
            userGetAccount.setUserLogin(userLogin);
        }

        return ResponseEntity.ok(userGetAccount);
    }

    @GetMapping("/refresh")
    @ApiMessage("Get user by refresh token")
    public ResponseEntity<LoginResponse> getRefreshToken(
            @CookieValue(name = "refresh_token", defaultValue = "refresh_token_default") String refresh_token)
            throws IdInvalidException {
        if (refresh_token.equals("refresh_token_default")) {
            throw new IdInvalidException("Ban khong co refresh token trong cookies");
        }

        // check valid
        Jwt decodedToken = this.securityUtil.checkValidRefreshToken(refresh_token);
        String email = decodedToken.getSubject();

        // check user by refreshtoken and email
        User currentUser = this.userServicePort.findUserByRefreshTokenAndEmail(refresh_token, email);
        if (currentUser == null) {
            throw new IdInvalidException("Refresh token khong hop le");
        }

        LoginResponse res = new LoginResponse();
        User userCurrentDB = this.userServicePort.findUserByEmail(email);
        if (userCurrentDB != null) {
            LoginResponse.UserLogin userLogin = new LoginResponse.UserLogin(userCurrentDB.getEmail(),
                    userCurrentDB.getUsername());
            res.setUser(userLogin);
        }

        // Truyen thong tin dang nhap cua nguoi dung
        String access_token = this.securityUtil.createAccessToken(email, res.getUser());
        res.setAccessToken(access_token);

        // Create refresh token
        String newRefreshToken = this.securityUtil.createRefreshToken(email, res);

        // Update user
        this.userServicePort.updateRefreshToken(newRefreshToken, email);

        // set cookies
        ResponseCookie responseCookie = ResponseCookie
                .from("refresh_token", newRefreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(864000)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(res);
    }

    @PostMapping("/logout")
    @ApiMessage("Logout UserEntity")
    public ResponseEntity<Void> logout() throws IdInvalidException {
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "";

        if (email.isEmpty()) {
            throw new IdInvalidException("Access token khong hop le");
        }

        // update refresh token
        this.userServicePort.updateRefreshToken(null, email);

        // remove refresh token from cookies
        ResponseCookie deleteCookies = ResponseCookie
                .from("refresh_token", null)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, deleteCookies.toString())
                .body(null);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest user, HttpServletRequest request)
            throws MessagingException {
        userServicePort.register(Utility.getSiteURL(request), userMapper.toUser(user));
        return ResponseEntity.ok().body("Please check your email to confirm your account.");
    }

    @GetMapping("/register/confirm_register/{token}")
    public ResponseEntity<String> confirmToken(@PathVariable int token) {
        return ResponseEntity.ok().body(userServicePort.confirmRegister(token));
    }

    @PostMapping("/forgot_password")
    public ResponseEntity<String> forgotPassword(HttpServletRequest request,
            @RequestBody ForgotPasswordRequest forgotPasswordRequest) throws MessagingException {
        String result = userServicePort.forgotPassword(Utility.getSiteURL(request), forgotPasswordRequest.getEmail());
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/forgot_password/confirm_request/{token}")
    public ResponseEntity<Void> confirmPassword(@PathVariable int token) {
        userServicePort.confirmForgotPasswordToken(token);
        ResponseCookie responseCookie = ResponseCookie
                .from("confirm_password", String.valueOf(token))
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(864000)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .build();
    }

    @PostMapping("/forgot_password/create_password")
    public ResponseEntity<String> createPassword(@CookieValue(value = "confirm_password") String token,
            @RequestBody CreateNewPasswordRequest createNewPasswordRequest) {
        if (!createNewPasswordRequest.getPassword().equals(createNewPasswordRequest.getConfirmPassword()))
            throw new RuntimeException("Re-enter password does not match");
        ResponseCookie deleteCookies = ResponseCookie
                .from("confirm_password", null)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, deleteCookies.toString())
                .body(userServicePort.createNewPassword(createNewPasswordRequest.getPassword(),
                        Integer.parseInt(token)));
    }
}