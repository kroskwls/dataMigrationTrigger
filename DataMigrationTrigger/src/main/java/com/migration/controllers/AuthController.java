package com.migration.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.migration.h2.AuthUserRepository;
import com.migration.jwt.request.AuthRequest;
import com.migration.jwt.response.JwtResponse;
import com.migration.utils.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Authentication", description = "Authentication API")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    AuthUserRepository userRepository;

//     @Hidden
//     @PostMapping("/create-account")
//     public ResponseEntity<?> registerUser(@RequestBody AuthUser signUpRequest, HttpServletRequest request)
//             throws UnsupportedEncodingException {
//         if (userRepository.existsByUsername(signUpRequest.getUsername())) {
//             ResponseBody body = ResponseBody.builder()
//                     .status(HttpServletResponse.SC_BAD_REQUEST)
//                     .error("Already exists")
//                     .build();

//             return ResponseEntity.badRequest().body(body);
//         }
//         AuthUser user = new AuthUser();
//         user.setUsername(signUpRequest.getUsername());
//         // String encodedKey = encoder.encode(signUpRequest.getSecretKey());
//         // user.setSecretKey(encodedKey);
//         user.setSecretKey(signUpRequest.getSecretKey());

//         userRepository.save(user);

//         user.setSecretKey(null);
//         return ResponseEntity.ok(user);
//     }

    @Operation(summary = "사용자 인증 토큰 요청", description = "username, secretKey 기반 인증 토큰 요청")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponse.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"status\": 401, \"error\": \"Unauthorized\"}"))
            }),
            @ApiResponse(responseCode = "403", description = "Bad Request", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"status\": 403, \"error\": \"Bad Request\"}"))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"status\": 404, \"error\": \"Not Found\"}"))
            }),
    })
    @PostMapping(value = "/token", produces = "application/json")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthRequest userInfo) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userInfo.getUsername(), userInfo.getSecretKey()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateJwtToken(authentication);
        int jwtExpirationMs = jwtUtil.getExpirationMs();

        log.info("Authentication token issued successfully");
        return ResponseEntity
                .ok(JwtResponse.builder().status(200).access_token(jwt).expires_in(jwtExpirationMs).build());
    }
}
