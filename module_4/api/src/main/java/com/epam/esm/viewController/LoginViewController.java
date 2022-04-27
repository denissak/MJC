package com.epam.esm.viewController;

import com.epam.esm.CertificateService;
import com.epam.esm.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class LoginViewController {

    private final TagService tagService;
    private final CertificateService certificateService;

    @RequestMapping("/login")
    public String register(Model model) {
        return "login";
    }

    @RequestMapping("/mainpage")
    public String mainpage(Model model, @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                           @RequestParam(value = "size", defaultValue = "18", required = false) int size) {
        model.addAttribute("tags", tagService.readAll(page, size));
        return "mainpage";
    }

    @RequestMapping("/cart")
    public String order(Model model) {
        return "cart";
    }

    @RequestMapping("/certificate")
    public String certificate(Model model) {
        return "certificate";
    }
}
