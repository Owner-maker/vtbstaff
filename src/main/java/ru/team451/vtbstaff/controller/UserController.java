package ru.team451.vtbstaff.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.team451.vtbstaff.domain.AppUser;
import ru.team451.vtbstaff.domain.Role;
import ru.team451.vtbstaff.http.ApiMethodStarter;
import ru.team451.vtbstaff.http.WalletCreator;
import ru.team451.vtbstaff.pojo.RoleToUserForm;
import ru.team451.vtbstaff.pojo.UserToRegister;
import ru.team451.vtbstaff.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.team451.vtbstaff.filter.CustomAuthenticationFilter.DELTA_TOKEN_EXPIRING_10MINS_MILLS;
import static ru.team451.vtbstaff.filter.CustomAuthorizationFilter.JWT_STRING_PREFIX_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
public class UserController {
    private final UserService userService;
    private final ApiMethodStarter<String> walletCreator;

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<AppUser> getUser(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUser(username));
    }

    @PostMapping("/user/register")
    public ResponseEntity<AppUser> saveUser(@RequestBody UserToRegister user) {
        AppUser appUser = userService.getUser(user.getUsername());
        if (appUser != null && user.getUsername().equals(appUser.getUsername())) {
            return new ResponseEntity<>(FORBIDDEN);
        }

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/register").toUriString());
        List<Role> roles = new ArrayList<>();
        try {
            roles.add(userService.getRole("ROLE_USER"));
        }
        catch (Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
        AppUser appUserToSave = new AppUser(null, user.getName(), user.getUsername(), user.getPassword(),
                roles, new ArrayList<>(),new ArrayList<>(),null, null);    ////////////////////////////////
        userService.saveUser(appUserToSave);
        walletCreator.start(user.getUsername());

        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm role) {
        userService.addRoleToUser(role.getUsername(), role.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith(JWT_STRING_PREFIX_VALUE)) {
            try {
                String refreshToken = authHeader.substring(JWT_STRING_PREFIX_VALUE.length());
                Algorithm algorithm = Algorithm.HMAC256("SecretExample".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                AppUser user = userService.getUser(username);

                String accessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + DELTA_TOKEN_EXPIRING_10MINS_MILLS))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }
            catch (Exception exception) {
                log.error("Error logging in {}", exception.getMessage());
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }
        else {
            log.error("Refresh token is missing");
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Refresh token is missing");
        }
    }

}

