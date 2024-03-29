package ru.topjava.restaurant_voting.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.web.SecurityFilterChain;
import ru.topjava.restaurant_voting.model.Role;
import ru.topjava.restaurant_voting.model.User;
import ru.topjava.restaurant_voting.repository.UserRepository;
import ru.topjava.restaurant_voting.web.AuthUser;

import java.util.Optional;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@Slf4j
@AllArgsConstructor
public class WebSecurityConfig {

    private final UserRepository userRepository;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole(Role.ADMIN.name())
                .requestMatchers("/api/**").authenticated()
                .anyRequest().authenticated()
            )
            .httpBasic(withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(sessions -> sessions
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(
                        login -> {
                            log.debug("Authenticating '{}'", login);
                            Optional<User> optionalUser = userRepository.getUserByLogin(login);
                            return new AuthUser(optionalUser.orElseThrow(
                                    () -> new UsernameNotFoundException("User '" + login + "' was not found")));
                        })
                .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
    }

    @Bean
    // https://stackoverflow.com/a/70176629/548473
    public UserDetailsService userDetailsServiceBean(AuthenticationManagerBuilder auth) {
        return auth.getDefaultUserDetailsService();
    }
}