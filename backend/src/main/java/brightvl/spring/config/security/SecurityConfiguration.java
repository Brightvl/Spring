package brightvl.spring.config.security;


import brightvl.spring.model.Role;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

@Configuration //указывает, что этот класс содержит конфигурацию Spring.
@EnableWebSecurity //включает поддержку безопасности в приложении.
@EnableMethodSecurity(securedEnabled = true) //позволяет использовать аннотации безопасности на методах.// чтобы работал @Secure над классами
public class SecurityConfiguration {

    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");// Убираем префикс "ROLE_"
    }

    //  Собственная настройка формы входа
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(requests -> requests
                                .requestMatchers("/home/timesheets/**").hasAnyAuthority("admin","user")
                                .requestMatchers("/api/**").hasAuthority("rest")
                                .anyRequest().authenticated() // нужна авторизация
//                                .permitAll() // всем разрешено
//                                .denyAll() // всем вход запрещен
                )
                .formLogin(Customizer.withDefaults()) // выдает окно авторизации - можно подменить на свое
                .httpBasic(Customizer.withDefaults()) // поддержку Basic Auth для REST ресурсов
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .defaultAuthenticationEntryPointFor(
                                (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"),
                                request -> request.getRequestURI().startsWith("/api")
                        )
                )
                .build();
    }


    // 1. пользователь регистрируется на сайте и вводит свой пароль
    // 1.1 сервер считает хеш от пароля и сохраняет его в бд = hashInDatabase
    // 2. пользователь логинится на сайт и вводит свой пароль
    // 2.1 сервер считает хеш от пароля и сравнивает его с хешом в БД
    // hashFunc(password) -> hashInDatabase
    // hash -> password !!!
    // username1, password => hash(username1 + _ + password) == hash1
    // username2, password => hash(username2 + _ + password) == hash2

    // hashFunc(rawPassword) == hashInDatabase
    // пароль всегда нужно хранить в виде hash

    /**
     * Возвращает объект для шифрования паролей (BCrypt).
     *
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // суть под капотом
    // String encode(CharSequence rawPassword); шифрует пароль
    // boolean matches(CharSequence rawPassword, String encodedPassword); сравнивает введенный пароль хешируя его и сравнивая с хешированнм паролем в бд
}
