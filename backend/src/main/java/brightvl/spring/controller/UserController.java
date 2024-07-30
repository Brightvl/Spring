package brightvl.spring.controller;

import brightvl.spring.model.Timesheet;
import brightvl.spring.model.User;
import brightvl.spring.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        final User created = userService.create(user);

        // 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
