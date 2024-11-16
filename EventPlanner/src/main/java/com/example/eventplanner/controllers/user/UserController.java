package com.example.eventplanner.controllers.user;

import com.example.eventplanner.dto.user.RegisterAuUserDTO;
import com.example.eventplanner.dto.user.RegisterEoDTO;
import com.example.eventplanner.dto.user.RegisterSpDTO;
import com.example.eventplanner.model.user.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {
    @PostMapping("/register")
    public RegisterAuUserDTO Register(@RequestBody RegisterAuUserDTO user) {
        return user;
    }

    @PostMapping("/register-eo")
    public RegisterEoDTO RegisterEo(@RequestBody RegisterEoDTO user) {
        return user;
    }

    @PostMapping("/register-sp")
    public RegisterSpDTO RegisterSp(@RequestBody RegisterSpDTO user) {
        return user;
    }
}
