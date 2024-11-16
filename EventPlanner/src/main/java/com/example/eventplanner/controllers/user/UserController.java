package com.example.eventplanner.controllers.user;

import com.example.eventplanner.dto.user.GetEoByIdResponseDTO;
import com.example.eventplanner.dto.user.GetSpByIdResponseDTO;
import com.example.eventplanner.dto.user.auth.*;
import com.example.eventplanner.dto.user.update.UpdateEoRequestDTO;
import com.example.eventplanner.dto.user.update.UpdateEoResponseDTO;
import com.example.eventplanner.dto.user.update.UpdateSpRequestDTO;
import com.example.eventplanner.dto.user.update.UpdateSpResponseDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserController {

    @GetMapping("/sp/{id}")
    public GetSpByIdResponseDTO GetSpById(@PathVariable( value = "id") int id){
        return new GetSpByIdResponseDTO();
    }

    @GetMapping("/eo/{id}")
    public GetEoByIdResponseDTO GetEoById(@PathVariable( value = "id") int id){
        return new GetEoByIdResponseDTO();
    }

    @PostMapping("/login")
    public LoginResponseDTO Register(@RequestBody LoginRequestDTO login) {
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setEmail(login.getEmail());
        loginResponseDTO.setPassword(login.getPassword());
        loginResponseDTO.setAccessToken("324-fsghsfljh235fsgsfg");
        return loginResponseDTO;
    }

    @PostMapping("/register")
    public RegisterAuUserResponseRequestDTO Register(@RequestBody RegisterAuUserRequestDTO user) {
        RegisterAuUserResponseRequestDTO userResponseDTO = new RegisterAuUserResponseRequestDTO();
        userResponseDTO.setEmail(user.getEmail());
        return userResponseDTO;
    }

    @PostMapping("/register-eo")
    public RegisterEoRequestResponseDTO RegisterEo(@RequestBody RegisterEoRequestDTO user) {
        RegisterEoRequestResponseDTO userResponseDTO = new RegisterEoRequestResponseDTO();
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setPassword(user.getPassword());
        userResponseDTO.setAddress(user.getAddress());
        userResponseDTO.setName(user.getName());
        // other attributes (maybe mapping)
        return userResponseDTO;
    }

    @PostMapping("/register-sp")
    public RegisterSpRequestResponseDTO RegisterSp(@RequestBody RegisterSpRequestDTO user) {
        RegisterSpRequestResponseDTO userResponseDTO = new RegisterSpRequestResponseDTO();
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setPassword(user.getPassword());
        userResponseDTO.setAddress(user.getAddress());
        userResponseDTO.setName(user.getName());
        return userResponseDTO;
    }

    @PutMapping("/update-eo")
    public UpdateEoResponseDTO UpdateEo(@RequestBody UpdateEoRequestDTO user) {
        return  new UpdateEoResponseDTO();
    }

    @PutMapping("/update-sp")
    public UpdateSpResponseDTO UpdateEo(@RequestBody UpdateSpRequestDTO user) {
        return  new UpdateSpResponseDTO();
    }
}
