package hv.hoviet.itpostbackend.config;

import hv.hoviet.itpostbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/* Quản lý và cấu hình bảo mật trong Spring application */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(
                                "/",
                                "/home",
                                "api/v1/**",
                                "api/v1/posts"
                        ).permitAll()
                        .anyRequest().authenticated()
                )

                // cung cấp cấu hình dựa trên form đăng nhập
//                .formLogin((form) -> form
//                        /* khi người dùng truy cập vào trang web yêu cầu khác thực
//                        mà chưa xác thực thì sẽ được chuyển hướng đến trang này */
//                        .loginPage("/login")
//                        .permitAll()
//                )

                // Định cấu hình cho việc quản lý đồng thời các phiên đăng nhập
                .sessionManagement((sessionManagement) ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                .sessionConcurrency((sessionConcurrency) ->
                                        /* giới hạn phiên đăng nhập của người dùng là 2.
                                        nếu > 2 thì được chuyển hướng đến URL "/login?expired" */
                                        sessionConcurrency
                                                .maximumSessions(2)
                                                .expiredUrl("/login?expired")
                                )
                )
                // cung cấp một AuthenticationProvider cho việc xác thực người dùng
                .authenticationProvider(
                        authenticationProvider()
                ).addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
                )
                .logout((logout) -> logout.permitAll());
        return http.build();
    }

    /* tạo và cấu hình một DaoAuthenticationProvider, một AuthenticationProvider cung cấp cơ chế xác thực dựa trên csdl */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        // thiết lập UserDetailsService để tìm kiếm thông tin người dùng trong csdl
        authenticationProvider.setUserDetailsService(userService.userDetailsService());
        // mã hóa mật khẩu
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /* Phương thức authenticationManager tạo và cấu hình một AuthenticationManager,
    một giao diện trong Spring Security giúp quản lý việc xác thực người dùng. */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
