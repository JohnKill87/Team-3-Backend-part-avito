package ru.skypro.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.skypro.homework.repository.UserRepository;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class WebSecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v3/api-docs",
            "/webjars/**",
            "/login",
            "/register",
            "/ads"
    };

    private static final String[] PROTECTED = {"/ads/**","/users/**"};
    @Bean
    public MyUserDetailManager userDetailsService(UserRepository userRepository) {
        return new MyUserDetailManager(userRepository);
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(conf
                -> conf.antMatchers(AUTH_WHITELIST).permitAll());
        http.authorizeHttpRequests(conf
                -> conf.antMatchers(HttpMethod.GET, "/ads/*/image").permitAll());
        http.authorizeHttpRequests(conf
                -> conf.antMatchers(HttpMethod.GET, "/ads").permitAll());
        http.authorizeHttpRequests(conf
                -> conf.antMatchers(PROTECTED).hasAnyRole("USER", "ADMIN"));
        http.csrf(conf -> conf.disable());
        http.cors(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
//        http.csrf()
//                .disable()
//                .authorizeHttpRequests(
//                        authorization ->
//                                authorization
//                                        .mvcMatchers(AUTH_WHITELIST)
//                                        .permitAll()
//                                        .mvcMatchers("/ads/**", "/users/**")
//                                        .authenticated()
//                                        .antMatchers("/ads/{id}","/ads").hasRole("USER"))
//                .cors()
//                .and()
//                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
