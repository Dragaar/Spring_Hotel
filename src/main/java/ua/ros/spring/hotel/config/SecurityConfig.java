package ua.ros.spring.hotel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ua.ros.spring.hotel.model.repository.AccountRepository;
import ua.ros.spring.hotel.model.service.impl.account.AccountDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private static final String LOGIN = "/auth/login";
    private static final String[] ALLOWED_URLS = {LOGIN, "/auth/registration", "/error", "/", "/contacts"};


   /* AccountDetailsService accountDetailsService;

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.userDetailsService(accountDetailsService)
                                    .passwordEncoder(passwordEncoder());

        return authenticationManagerBuilder.build();
    }*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(ALLOWED_URLS).permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage(LOGIN)
                        .loginProcessingUrl("/process_login")
                        .usernameParameter("email")
                        .failureUrl("/error")
                        .failureUrl("/auth/login?error")
                        .defaultSuccessUrl("/", false)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl(LOGIN)
                        .deleteCookies("JSESSIONID")
                );

        return httpSecurity.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}