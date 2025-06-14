	package lv.venta.cbs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Disable CSRF for development
            .authorizeHttpRequests(auth -> auth
                // Public pages
                .requestMatchers("/", "/movie/**", "/register", "/login", "/css/**", "/js/**", "/images/**").permitAll()
                // CRUD operations - any role except ROLE_USER
                .requestMatchers("/crud/**").access((authentication, context) -> {
                    if (authentication.get() == null) {
                        return new AuthorizationDecision(false);
                    }
                    boolean hasUserRole = authentication.get().getAuthorities().stream()
                            .anyMatch(a -> a.getAuthority().equals("ROLE_USER"));
                    return new AuthorizationDecision(!hasUserRole);
                })
                // Protected pages - require authentication
                .requestMatchers("/profile/**", "/booking/**").authenticated()
                // Any other request requires authentication
                .anyRequest().permitAll()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            );
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
} 