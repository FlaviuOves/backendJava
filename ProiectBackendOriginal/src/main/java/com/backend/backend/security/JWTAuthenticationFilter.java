package com.backend.backend.security;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.auth0.jwt.JWT;
import com.backend.backend.dto.UserDto;
import com.backend.backend.service.UserService;
import com.backend.backend.util.EntityToDtoConvertor;
import com.backend.backend.util.TSConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private String secret;


    private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    private UserService userService;

    private AuthenticationManager authenticationManager;


    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, String secret,
                                   UserService userService) {
        this.authenticationManager = authenticationManager;
        this.secret = secret;
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
        try {
            UserDto creds = new ObjectMapper().readValue(req.getInputStream(), UserDto.class);
            Authentication auth = new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>());
            Authentication authManager = authenticationManager.authenticate(auth);
            return authManager;
        } catch (Exception e) {

            try {
                res.setContentType("text;charset=UTF-8");
                res.setStatus(HttpStatus.UNAUTHORIZED.value());
                res.getWriter().write("" +e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            logger.error(""+ e);
            return null;
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        User user = (User) auth.getPrincipal();
        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
               // .withClaim("roles", String.valueOf(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())))
                  .withArrayClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
                .sign(HMAC512(secret.getBytes()));
        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);

        String userEmail = ((User) auth.getPrincipal()).getUsername();
        UserDto userFound = EntityToDtoConvertor.convertTo(userService.findUserByEmail(userEmail));

        ServletRequestAttributes attr = (ServletRequestAttributes)  RequestContextHolder.getRequestAttributes();
        HttpSession session= attr.getRequest().getSession(true);
        session.setAttribute(userEmail, req.getHeader("User-Agent"));

        // end session user logged in

        Gson gson = new GsonBuilder().setDateFormat(TSConstants.DATE_SIMPLE_PATTERN).create();
        String userJsonString = gson.toJson(userFound);
        res.setContentType("text/x-json;charset=UTF-8");
        res.getWriter().write(userJsonString);


    }
}
