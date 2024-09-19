package vn.hoidanit.laptopshop.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;
import vn.hoidanit.laptopshop.service.UserService;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        List<User> arrUsers = this.userService.geAllUserByEmail("son@gmail.com");
        System.err.println(arrUsers);

        model.addAttribute("eric", "test");
        model.addAttribute("hoidanIt", "form controller with model");
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String GetUserPage(Model model) {
        List<User> users = this.userService.geAllUser();
        model.addAttribute("users1", users); // users la gia triben contoller Users1
        // la gia tri nhan duoc ben view
        System.err.println(users);
        return "admin/user/table-user";
    }

    @RequestMapping("/admin/user/create") // get
    public String getUserPage(Model model) {
        model.addAttribute("newUser", new User()); // truyen bien qua view
        return "admin/user/create";
    }

    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST) // trang luu
    public String CreateUserPage(Model model, @ModelAttribute("newUser") User moimoi) {
        this.userService.handleSaveUser(moimoi);
        return "redirect:/admin/user";
    }
}
