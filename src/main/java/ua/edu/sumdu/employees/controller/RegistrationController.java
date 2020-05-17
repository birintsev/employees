package ua.edu.sumdu.employees.controller;

import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.edu.sumdu.employees.model.User;
import ua.edu.sumdu.employees.model.UserDTO;
import ua.edu.sumdu.employees.service.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@Controller(value = "RegistrationController")
@AllArgsConstructor
public class RegistrationController {
    private static final Logger LOGGER = Logger.getLogger(RegistrationController.class);
    private final UserDetailsService userDetailsService;
    private final ConversionService conversionService;

    @Cacheable("images")
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registrationPage(ModelAndView modelAndView) {
        UserDTO userDTO = new UserDTO();
        modelAndView.getModelMap().addAttribute("user", userDTO);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    // todo errors handling
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView registerUser(
            @Valid @ModelAttribute(value = "user") UserDTO userDTO,
            HttpServletRequest request,
            Errors errors) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            if (!errors.hasErrors()) {
                userDetailsService.registerNewUser(conversionService.convert(userDTO, User.class));
                modelAndView.setStatus(HttpStatus.OK);
                modelAndView.setViewName("redirect:/employees"); // todo (1) notify about successful registration (2) auto login ? after successfully completed registration
            } else {
                throw new IOException("Invalid user input " + errors);
            }
        } catch (IOException e) {
            LOGGER.error(e);
            modelAndView.setViewName("redirect:/registration");
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        }
        return modelAndView;
    }
}
