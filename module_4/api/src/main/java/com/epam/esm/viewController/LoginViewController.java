package com.epam.esm.viewController;

import com.epam.esm.CertificateService;
import com.epam.esm.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class LoginViewController {

    private final TagService tagService;
    private final CertificateService certificateService;

    @RequestMapping("/login")
    public String register(Model model) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("mainpage");

        return "login";
    }

    @RequestMapping("/mainpage")
    public String mainpage(Model model, @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                        @RequestParam(value = "size", defaultValue = "18", required = false) int size) {
        model.addAttribute("tags", tagService.readAll(page, size));
        return "mainpage";
    }

    @RequestMapping("/cart")
    public String order(Model model, Authentication authentication) {
//        model.addAttribute("orders", orderList);
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("mainpage");
        return "cart";
    }

    @RequestMapping("/certificate")
    public String certificate(Model model, Authentication authentication) {
//        model.addAttribute("orders", orderList);
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("mainpage");
        return "certificate";
    }
}
