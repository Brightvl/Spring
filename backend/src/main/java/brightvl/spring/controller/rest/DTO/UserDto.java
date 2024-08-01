package brightvl.spring.controller.rest.DTO;

import lombok.Value;

import java.util.Set;

/**
 * DTO for {@link brightvl.spring.model.User}
 */
@Value
public class UserDto {
    Long id;
    String login;
    String password;
    Set<RoleDto> roles;
}