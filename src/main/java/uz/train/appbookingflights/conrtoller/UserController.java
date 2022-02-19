package uz.train.appbookingflights.conrtoller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.train.appbookingflights.service.UserService;
import uz.train.modelappbookingflights.Dto.UserDto;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getUser(@PathVariable UUID user_id) {
        return ResponseEntity.ok(userService.getUser(user_id));
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody UserDto userDto) {
        userService.addUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{user_id}")
    public ResponseEntity<?> editUser (@PathVariable UUID user_id, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.editUser(user_id,userDto));
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID user_id) {
        userService.deleteUser(user_id);
        return ResponseEntity.ok().build();
    }

}

