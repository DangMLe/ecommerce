package com.example.ecommerce.restcontroller;

import java.util.List;
import java.util.stream.Collectors;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.example.ecommerce.entity.Account;
import com.example.ecommerce.payload.request.LoginRequest;
import com.example.ecommerce.payload.request.SignupRequest;
import com.example.ecommerce.payload.response.JwtResponse;
import com.example.ecommerce.payload.response.MessageResponse;
import com.example.ecommerce.repository.AccountRepository;
import com.example.ecommerce.security.jwt.JwtAuthTokenFilter;
import com.example.ecommerce.security.jwt.JwtUtils;
import com.example.ecommerce.security.services.UserDetailsImpl;

import org.springframework.util.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    final private AuthenticationManager authenticationManager;

    final private AccountRepository accountRepository;


    final private PasswordEncoder passwordEncoder;

    final private JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, AccountRepository accountRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                                                 userDetails.getId(),
                                                 userDetails.getUsername(),
                                                 userDetails.getEmail(),
                                                 roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (accountRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: Username is already taken!"));
        }
        
        if (accountRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        Account user = new Account(signUpRequest.getUsername(),
            passwordEncoder.encode(signUpRequest.getPassword()),
                                    signUpRequest.getFirstname(),
                                    signUpRequest.getLastname(),
                                    signUpRequest.getRole(), 
                                    signUpRequest.getAge(), signUpRequest.getEmail(), 
                                    signUpRequest.getAddress(), signUpRequest.getPhonenum(), 
                                    signUpRequest.getAvatar());

        accountRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    @PostMapping("/logout")
    public Boolean logout(HttpServletRequest request, HttpServletResponse response){
		try{
			String headerAuth = request.getHeader("Authorization"), jwt="";

	        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
	            jwt = headerAuth.substring(7, headerAuth.length());
	        }

	        jwtUtils.addToBlackList(jwt);
		}
		catch(Exception ex){
			System.out.println("Logout error!");
			return false;
		}
		
        return true;
	}
}
