package lv.venta.cbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lv.venta.cbs.model.User;
import lv.venta.cbs.service.UserService;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String showProfile(Model model) {
        User currentUser = userService.getCurrentAuthenticatedUser();
        if (currentUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("currentUser", currentUser);
        return "profile";
    }

    @GetMapping("/edit")
    public String showEditProfile(Model model) {
        User currentUser = userService.getCurrentAuthenticatedUser();
        if (currentUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("currentUser", currentUser);
        return "edit-profile";
    }

    @PostMapping("/edit")
    public String updateProfile(@RequestParam String currentPassword,
                              @RequestParam(required = false) String newPassword,
                              @RequestParam String email,
                              @RequestParam String fullName,
                              @RequestParam String phoneNumber,
                              @RequestParam String role,
                              RedirectAttributes redirectAttributes) {
        try {
            User currentUser = userService.getCurrentAuthenticatedUser();
            if (currentUser == null) {
                return "redirect:/login";
            }

            User updatedUser = userService.updateProfile(
                currentUser.getUsername(),
                currentPassword,
                newPassword,
                email,
                fullName,
                phoneNumber,
                role
            );

            // Update the authentication context with the new user details, since our role wont update without relooging without this
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Authentication newAuth = new UsernamePasswordAuthenticationToken(
                updatedUser,
                auth.getCredentials(),
                updatedUser.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(newAuth);

            redirectAttributes.addFlashAttribute("success", "Profile updated successfully");
            return "redirect:/profile";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/profile/edit";
        }
    }
}
