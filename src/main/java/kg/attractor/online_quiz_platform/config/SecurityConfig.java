package kg.attractor.online_quiz_platform.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final DataSource dataSource;

//    private final PasswordEncoder encoder;


//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager() {
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(encoder.encode("admin"))
//                .roles("ADMIN")
//                .authorities("FULL")
//                .build();
//
//        UserDetails guest = User.builder()
//                .username("guest")
//                .password(encoder.encode("qwe"))
//                .roles("GUEST")
//                .authorities("READ_ONLY")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, guest);
//    }

    private static final String USER_QUERY =
            """
                    select EMAIL, PASSWORD, ENABLED
                    from USERS
                    where EMAIL = ?;
                    """;
    private static final String AUTHORITIES_QUERY =
            """
                    select USERS.EMAIL, A.ROLE
                    from USERS
                    inner join USER_AUTHORITY UA on USERS.ID = UA.USER_ID
                    inner join AUTHORITIES A on A.ID = UA.AUTHORITY_ID
                    where EMAIL=?;
                    """;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(USER_QUERY)
                .authoritiesByUsernameQuery(AUTHORITIES_QUERY)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        // Public Endpoints
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/quizzes/rates").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/quizzes").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/quizzes/{quizId}/results").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/quizzes/{quizId}/leaderboard").permitAll()

                        // Authenticated Endpoints
                        .requestMatchers(HttpMethod.GET, "/api/users/**").hasAuthority("ADMIN")

                        // Role-Specific Endpoints
                        .requestMatchers(HttpMethod.GET, "/api/quizzes/{id}").hasAnyAuthority("ADMIN", "GUEST")
                        .requestMatchers(HttpMethod.POST, "/api/quizzes/").hasAnyAuthority("ADMIN", "GUEST")
                        .requestMatchers(HttpMethod.POST, "/api/quizzes/{quizId}/solve").hasAnyAuthority("ADMIN", "GUEST")
                        .requestMatchers(HttpMethod.GET, "/api/quizzes/{quizId}/rate").hasAnyAuthority("ADMIN", "GUEST")

                        // Catch-All
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}
