package com.conference.controller;

import com.conference.dto.EventDTO;
import com.conference.entity.User;
import com.conference.exception.ResourceNotFoundException;
import com.conference.service.EventService;
import com.conference.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    private final EventService eventService;

    private final UserDetailsServiceImpl userService;

    public UserController(EventService eventService, UserDetailsServiceImpl userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            model.addAttribute("error", "Passwords are not match");
            logger.warn("Passwords doesn't match");

            return "registration";
        }
        if (!userService.saveUser(userForm)) {
            model.addAttribute("error", "That username is already taken");
            logger.warn("User already exists");

            return "registration";
        }

        return "redirect:/login";
    }

    @RequestMapping("/login")
    public String doLogin(@RequestParam(value = "error", required = false) String error,
                          @RequestParam(value = "logout", required = false) String logout, Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);

        return "login";
    }

    @GetMapping("/")
    public String main(Model model) {
        return mainPaginated(model, 1, "title", "asc");
    }

    @GetMapping("/page/{page}")
    public String mainPaginated(Model model,
                               @PathVariable("page") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDirection) {
        int size = 3;
        Page<EventDTO> page = eventService.findEventDetailsPaginated(pageNo, size, sortField, sortDirection);
        List<EventDTO> eventDTOS = page.getContent();

        logger.info("Main Page");

        if (pageNo > page.getTotalPages() || pageNo <= 0) {
            logger.error("That's page doesn't exist");
            throw new ResourceNotFoundException();
        }

        model.addAttribute("listOfEvents", eventDTOS);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalEvents", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

        return "main";
    }

    @GetMapping("/error")
    public String errorPage() {
        return "error";
    }
}
