package brightvl.spring.service.rest;

import brightvl.spring.controller.rest.DTO.RoleDto;
import brightvl.spring.controller.rest.DTO.UserDto;
import brightvl.spring.model.Role;
import brightvl.spring.model.User;
import brightvl.spring.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public UserDto create(UserDto userDto) {
        User user = convertToEntity(userDto);
        User createdUser = userRepository.save(user);
        return convertToDto(createdUser);
    }

    private UserDto convertToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new RoleDto(role.getId(), role.getName()))
                        .collect(Collectors.toSet())
        );
    }

    private User convertToEntity(UserDto userDto) {
        User user = new User();
        user.setLogin(userDto.getLogin());
        user.setPassword(userDto.getPassword());
        user.setRoles(userDto.getRoles().stream()
                .map(roleDto -> {
                    Role role = new Role();
                    role.setId(roleDto.getId());
                    role.setName(roleDto.getName());
                    return role;
                })
                .collect(Collectors.toSet()));
        return user;
    }
}
