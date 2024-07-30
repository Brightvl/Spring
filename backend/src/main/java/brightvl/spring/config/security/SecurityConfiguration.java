package brightvl.spring.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //указывает, что этот класс содержит конфигурацию Spring.
//@EnableWebSecurity //включает поддержку безопасности в приложении.
//@EnableMethodSecurity(securedEnabled = true) //позволяет использовать аннотации безопасности на методах.
public class SecurityConfiguration {

//  @Bean
//  GrantedAuthorityDefaults grantedAuthorityDefaults() {
//    return new GrantedAuthorityDefaults("");
//  }
//

    // как только мы это производим, дефолтная форма логина не требуется и нам самим нужно теперь настроить вход
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(requests -> requests
//                    .requestMatchers("/home/projects/**").hasAuthority("ADMIN") //доступен только пользователям с ролью "ADMIN".
//                    .requestMatchers("/home/timesheets/**").hasAuthority("USER")
//                    .requestMatchers("/api/**").hasAuthority("REST")
//                                .requestMatchers("/users").permitAll()
                                .anyRequest()
                                //.denyAll() // всем вход запрещен
                                .permitAll() // всем разрешено
//                                .authenticated() // пользователь должен быть авторизованным нет смысла без formLogin
                )
//                .formLogin(Customizer.withDefaults()) // выдает окно авторизации
                .build();
    }
//
//  /**
//   * возвращает объект для шифрования паролей (BCrypt).
//   * @return
//   */
//  @Bean
//  PasswordEncoder passwordEncoder() {
//    return new BCryptPasswordEncoder();
//  }

}
