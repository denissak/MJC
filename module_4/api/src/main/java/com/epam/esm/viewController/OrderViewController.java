package com.epam.esm.viewController;

import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class OrderViewController {

    @RequestMapping("/order")
    public ModelAndView getOrder(HttpServletRequest servletRequest, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
//        servletRequest.getSession().s;
        String a = (String) servletRequest.getSession().getAttribute("Authorization");
        response.setHeader("Authorization", a);
        response.getHeaders("Authorization");

        Session session = new Session();
        var asd = session.getCookie();
//        response.setHeader("Authorization", asd);
        modelAndView.setViewName("order");
        return modelAndView;
    }
}
