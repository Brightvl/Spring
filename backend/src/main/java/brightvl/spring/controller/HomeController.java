package brightvl.spring.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HomeController {
//    @Value("${spring.mvc.cors.allowed-origins}")
//    private String allowedOrigins;
//
//    @GetMapping("/")
//    public RedirectView redirect() {
//        return new RedirectView(allowedOrigins);
//    }
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Добро пожаловать на главную страницу!");
        return "home";
    }
}
