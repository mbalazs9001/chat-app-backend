package com.mng.hermes.api;

import com.mng.hermes.model.TargetType;
import com.mng.hermes.model.User;
import com.mng.hermes.service.MessageService;
import com.mng.hermes.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity getMessages(@RequestParam(value = "target", required = false) String target,
                                      @RequestParam(value = "type", required = false) TargetType type,
                                      @RequestHeader(value = "Authorization") String auth) {
        try {
            User user = userService.getUser(auth);
            if ((type == null && target != null) || (type != null && target == null)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(messageService.getMessages(target, type, user.getId()), HttpStatus.OK);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
