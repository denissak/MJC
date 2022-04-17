package com.epam.esm.viewController;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginViewController {

    @RequestMapping("/login")
    public String register(Model model) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("mainpage");

        return "login";
    }

    @RequestMapping("/mainpage")
    public String login(Model model, Authentication authentication) {
//        ModelAndView modelAndView = new ModelAndView();

//        modelAndView.setViewName("mainpage");
        return "mainpage";
    }
    @RequestMapping("/order")
    public String order(Model model, Authentication authentication) {
//        model.addAttribute("orders", orderList);
//        ModelAndView modelAndView = new ModelAndView();

//        modelAndView.setViewName("mainpage");
        return "order";
    }
}
