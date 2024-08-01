package brightvl.spring.controller.rest;

import brightvl.spring.controller.rest.DTO.UserDto;
import brightvl.spring.service.rest.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        UserDto created = userService.create(userDto);

        // 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
