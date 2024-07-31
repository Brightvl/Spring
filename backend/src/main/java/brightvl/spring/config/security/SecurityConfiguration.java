package brightvl.spring.config.security;


import brightvl.spring.model.Role;
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

@Configuration //указывает, что этот класс содержит конфигурацию Spring.
//@EnableGlobalAuthentication
@EnableWebSecurity //включает поддержку безопасности в приложении.
@EnableMethodSecurity(securedEnabled = true) //позволяет использовать аннотации безопасности на методах.// чтобы работал @Secure над классами
public class SecurityConfiguration {

    //ROLE_admin -> admin
    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }


    // как только мы это производим, дефолтная форма логина не требуется и нам самим нужно теперь настроить вход
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(requests -> requests
//                                .requestMatchers("/home/projects/**").hasAuthority(Role.ADMIN.getName()) //доступен только пользователям с ролью "ADMIN".
//                                .requestMatchers("/home/timesheets/**").hasAnyAuthority(Role.ADMIN.getName(),Role.USER.getName())
//                                .requestMatchers("/api/**").hasAuthority("REST")
//                                .requestMatchers("/").permitAll()
//                                .requestMatchers("/projects").permitAll() // Разрешаем доступ к /projects без аутентификации
                                .anyRequest().authenticated() // нужна авторизация
//                                .permitAll() // всем разрешено
//                                .denyAll() // всем вход запрещен
//                                .authenticated() // пользователь должен быть авторизованным нет смысла без formLogin
                )
                .formLogin(Customizer.withDefaults()) // выдает окно авторизации
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

    // суть такая что там 2 метода
    // String encode(CharSequence rawPassword); шифрует пароль
    // boolean matches(CharSequence rawPassword, String encodedPassword); сравнивает введенный пароль хешируя его и сравнивая с хешированнм паролем в бд
}
