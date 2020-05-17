package ua.edu.sumdu.employees.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.edu.sumdu.employees.model.UserDTO;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller(value = "RegistrationController")
public class RegistrationController {

    public RegistrationController(@Qualifier(value = "UserDetailsService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    private final UserDetailsService userDetailsService;
    private static final Logger LOGGER = Logger.getLogger(RegistrationController.class);

    @Cacheable("images")
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registrationPage(ModelAndView modelAndView) {
        UserDTO userDTO = new UserDTO();
        modelAndView.getModelMap().addAttribute("user", userDTO);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView registerUser(
            @Valid @ModelAttribute(value = "user") UserDTO userDTO,
            HttpServletRequest request,
            Errors errors) {
        ModelAndView modelAndView = new ModelAndView();
        // todo
        return modelAndView;
    }
}
