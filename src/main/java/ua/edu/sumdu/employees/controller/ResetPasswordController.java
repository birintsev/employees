package ua.edu.sumdu.employees.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.edu.sumdu.employees.config.EmployeesProperties;
import ua.edu.sumdu.employees.model.dto.NewPasswordDTO;
import ua.edu.sumdu.employees.model.user.ResetPasswordToken;
import ua.edu.sumdu.employees.model.user.User;
import ua.edu.sumdu.employees.service.ResetPasswordTokenService;
import ua.edu.sumdu.employees.service.TokenGenerator;
import ua.edu.sumdu.employees.service.UserService;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

@Controller
@Getter
@Setter
@AllArgsConstructor
public class ResetPasswordController {
    private static final Logger LOGGER = Logger.getLogger(ResetPasswordController.class);
    private final JavaMailSender javaMailSender;
    private final ResetPasswordTokenService resetPasswordTokenService;
    private final TokenGenerator tokenGenerator;
    private final UserService userService;

    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    public ModelAndView resetPassword(ModelAndView modelAndView) {
        modelAndView.setViewName("resetPassword");
        return modelAndView;
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public ModelAndView resetPassword(
        ModelAndView modelAndView,
        @Valid @RequestParam(name = "email") String email,
        /*@Value("#{T(java.util.Optional).ofNullable(${employees.resetPasswordTokenDuration}).orElse(T(java.time.Duration).ofDays(1))}") Duration duration,*/
        EmployeesProperties employeesProperties,
        Errors errors,
        @Value("${spring.mail.username}") String from) throws IOException { // todo handling errors

        User user = userService.findByEmail(email);
        if (user != null) {
            ResetPasswordToken token = resetPasswordTokenService.createResetPasswordTokenFor(user);
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom(from);
            mail.setTo(email); // todo set topic
            mail.setText("Your reset password token is " + token.getToken()); // todo move mail template to a separate file + Provider
            javaMailSender.send(mail);
        }

        modelAndView.setViewName(
                "redirect:/newPassword/" + URLEncoder.encode(email, Charset.defaultCharset().toString()) // todo forward instead of redirect
        );
        return modelAndView;
    }

    @RequestMapping(value = "/newPassword/{email}", method = RequestMethod.GET)
    public ModelAndView enterNewPassword(ModelAndView modelAndView, @PathVariable(name = "email") String email) {
        modelAndView.setViewName("newPassword");
        modelAndView.getModelMap().addAttribute(
            "DTO",
            new NewPasswordDTO(email, null, null, null)
        );
        return modelAndView;
    }

    @RequestMapping(value = "/newPassword", method = RequestMethod.POST)
    public ModelAndView setNewPassword(
        ModelAndView modelAndView,
        @Valid @ModelAttribute(value = "DTO") NewPasswordDTO newPasswordDTO,
        Errors errors
    ) throws IOException {

        if (errors.hasErrors()) { // todo handle errors (Invalid form input)
            modelAndView.getModelMap().addAttribute("DTO", newPasswordDTO);
            modelAndView.setViewName(
                "redirect:/newPassword/"
                + URLEncoder.encode(newPasswordDTO.getEmail(), Charset.defaultCharset().toString())
            );
            return modelAndView;
        }
        User user = userService.findByEmail(newPasswordDTO.getEmail());
        if (user == null) { // todo handle errors (No such user with the email)
            modelAndView.setViewName("registration");
            modelAndView.getModelMap().addAttribute("DTO", newPasswordDTO);
            return modelAndView;
        }
        ResetPasswordToken existingPasswordToken = resetPasswordTokenService.getFor(user);
        if (existingPasswordToken != null) { // if a user has requested password resetting
            if (existingPasswordToken.getToken().equals(newPasswordDTO.getResetPasswordToken())
                && newPasswordDTO.getNewPassword().equals(newPasswordDTO.getNewPasswordConfirmation())) { // todo create custom validator for 2 equal fields
                LOGGER.debug("Old user password: " + user.getPassword());
                user = userService.resetPassword(user, newPasswordDTO.getNewPassword());
                LOGGER.debug("New user password " + user.getPassword());
                modelAndView.setViewName("redirect:/login");
            } else { // todo handle errors (Wrong token)
                modelAndView.setViewName(
                    "redirect:/newPassword/"
                    + URLEncoder.encode(newPasswordDTO.getEmail(), Charset.defaultCharset().toString())
                );
                modelAndView.getModelMap().addAttribute("DTO", newPasswordDTO);
            }
        } else { // a user has not requested password reset yet
            modelAndView.setViewName("resetPassword");
        }
        return modelAndView;
    }

    /*@RequestMapping(path = "/changePassword", method = RequestMethod.POST)
    public ModelAndView changePassword(
        ModelAndView modelAndView,
        @Valid @ModelAttribute(name = "DTO") NewPasswordDTO newPasswordDTO,
        Errors errors
    ) {
        if (errors.hasErrors()) { // todo handle errors
            modelAndView.setViewName("newPassword");
            LOGGER.error("");
            return modelAndView;
        }
        userService.
    }*/
}
