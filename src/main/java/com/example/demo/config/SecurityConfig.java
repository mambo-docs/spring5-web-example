package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import java.time.Duration;
import java.util.Arrays;

//@EnableGlobalMethodSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password("$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
                .roles("ADMIN")
                .build();

        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager(admin);

        UserDetails user = User.builder()
                .username("user")
                .password("$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
                .roles("USER")
                .build();
        userDetailsManager.createUser(user);

        return userDetailsManager;
    }

    @EnableWebSecurity(debug = false)
    @Configuration
    public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        private final PasswordEncoder passwordEncoder;

        public WebSecurityConfig(PasswordEncoder passwordEncoder) {
            this.passwordEncoder = passwordEncoder;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.headers(headers -> {
                headers.frameOptions(frameOptions -> {
                    frameOptions.sameOrigin();
                });
                headers.cacheControl(Customizer.withDefaults());
            });

            http.authorizeRequests()
                    .antMatchers("/users").hasRole("ADMIN")
                    .antMatchers("/**").authenticated();

            http.formLogin(Customizer.withDefaults());
            http.logout(Customizer.withDefaults());
            http.csrf().disable();
            http.exceptionHandling(exception -> {
                exception.accessDeniedPage("/error");
            });

            http.sessionManagement(session -> {
                session.maximumSessions(1);
                session.sessionConcurrency(concurrency -> {
                    concurrency.maxSessionsPreventsLogin(true);
                });
            });

            http.rememberMe()
                .userDetailsService(userDetailsService())
                .key("remember-me-key")
                .rememberMeCookieName("remember-me")
                .rememberMeParameter("remember-me")
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds((int) Duration.ofDays(1).toSeconds());
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.httpFirewall(httpFirewall());

            web.ignoring().antMatchers("/images/**", "/css/**", "/js/**");
        }

        @Bean
        public StrictHttpFirewall httpFirewall() {
            StrictHttpFirewall firewall = new StrictHttpFirewall();
            firewall.setAllowedHttpMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
            return firewall;
        }

        @Bean
        public PersistentTokenRepository persistentTokenRepository() {
            return new InMemoryTokenRepositoryImpl();
        }
    }
}
