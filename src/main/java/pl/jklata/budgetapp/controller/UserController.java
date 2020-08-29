package pl.jklata.budgetapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.jklata.budgetapp.dto.UserUpdateDto;
import pl.jklata.budgetapp.service.UserService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping(value = "/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping(value = "")
    public String getUser(Model model) {

        model.addAttribute("user", userService.getAuthenticatedUserUpdateDto());
        return "userSettings";
    }

    @GetMapping(value = "/avatar", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getUserAvatar() throws IOException {

        UserUpdateDto userUpdateDto = userService.getAuthenticatedUserUpdateDto();
        byte[] buffer = null;
        if (Objects.nonNull(userUpdateDto)) {
            buffer = Objects.isNull(userUpdateDto.getAvatar()) ? null : userUpdateDto.getAvatar().getBytes();
        }
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(buffer, headers, HttpStatus.OK);
    }

    @PostMapping(value = "/save")
    public String updateUser(@Valid @ModelAttribute("user") UserUpdateDto user, BindingResult binding, Model model) {

        if (binding.hasErrors()) {
            for (ObjectError error : binding.getAllErrors()) {
                log.info("Validation errors during updating user. Error: [object = {}, message = {}]",
                        error.getObjectName(), error.getDefaultMessage());
            }
            model.addAttribute(user);
            log.error("User update went wrong.");
            return "userSettings";
        }


        userService.updateUser(user);
        log.debug("User update successful.");
        return "redirect:/user/";
    }

}
