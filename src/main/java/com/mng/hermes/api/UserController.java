package com.mng.hermes.api;

import com.mng.hermes.dto.UserDTO;
import com.mng.hermes.model.User;
import com.mng.hermes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<UserDTO> validateUser(@RequestHeader(value = "Authorization") String auth){
        try {
            User user = userService.getUser(auth);
            return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(value = "/update")
    public ResponseEntity<UserDTO> updateProfile(@RequestBody User newUserData, @RequestHeader(value = "Authorization") String auth) {
        try {
            User currentUser = userService.getUser(auth);
            userService.updateProfile(newUserData, currentUser);
            return new ResponseEntity<>(new UserDTO(currentUser), HttpStatus.OK);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void logoutUser(@RequestHeader(value = "Authorization") String auth){
        userService.logoutUser(auth);
    }
}
