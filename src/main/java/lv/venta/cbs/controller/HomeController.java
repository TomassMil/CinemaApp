package lv.venta.cbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lv.venta.cbs.model.User;
import lv.venta.cbs.service.UserService;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() 
            && !auth.getName().equals("anonymousUser")) { // japarbauda pret anonUser, jo spring security boot pec default assigno lietotajam anonymousUser pie UserDetails username.
            
            User currentUser = userService.getCurrentAuthenticatedUser(); // sis samekle musu user details db pec username, un ja mes tad padodam anonymouseUser pec username, tads db neeksiste - errors un forco login
            model.addAttribute("currentUser", currentUser);
        }
        return "home";
    }
} 