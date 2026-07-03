package com.example.rakshit.CollegeManagementSystem.config;

import com.example.rakshit.CollegeManagementSystem.enums.Role;
import com.example.rakshit.CollegeManagementSystem.filters.JwtAuthFilter;
import com.example.rakshit.CollegeManagementSystem.handlers.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.rakshit.CollegeManagementSystem.enums.Role.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
@Profile("!test")
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler successHandler;

    private static final String[] publicRoutes = {
            "/error", "/auth/**", "/home.html"
    };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeHttpRequests(auth ->
//                        Tells Spring that anyone can access these specific URLs without a token. This is where you put your login and signup routes.
                auth
                        .requestMatchers(publicRoutes).permitAll()
                        .requestMatchers("/students/**").authenticated()
//        This is the "lock everything else" rule. If a URL isn't in the permit list, you MUST be authenticated to see it.
                        .anyRequest().authenticated())
                .csrf(csrfConfig -> csrfConfig.disable())

//        Tells Spring NOT to create sessions on the server. Since we use JWTs, the server doesn't need to remember who you are between requests; the token you send has all the info.
                .sessionManagement(sessionConfig -> sessionConfig
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

//        This puts our custom
//        JwtAuthFilter
//        in the security line before the standard login filter. This ensures we check for a JWT token on every single request.
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2Login -> oauth2Login
                        .failureUrl(("/login?error=true"))
                        .successHandler(successHandler)
                );

        return httpSecurity.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}


//    @Bean
//    UserDetailsService myInMemoryUserDetailsService() {
//        UserDetails normalUser = User
//                .withUsername("anuj")
//                .password(passwordEncoder().encode("Anuj123"))
//                .roles("USER")
//                .build();
//
//        UserDetails adminUser = User
//                .withUsername("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(normalUser, adminUser);
//    }
