package brightvl.spring.config.security;

import brightvl.spring.model.User;
import brightvl.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MyCustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // по сути это контейнер в котором есть логин пароль и список привилегий ролей
    // он сам нам достанет все что нужно, по тем данным которые мы ввели в поле авторизации
    // vметод вызывается когда ввели данные для авторизации
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // юзеры пока хранятся в бд но так делать не следует, лучше использовать внешний сервер auth-service ldap-сервер
        // Получаем логин
        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));


        List<SimpleGrantedAuthority> userRoles = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                userRoles
        );
    }

}
