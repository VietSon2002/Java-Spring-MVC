package vn.hoidanit.laptopshop.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.UserService;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        List<User> arrUsers = this.userService.getAllUserByEmail("son@gmail.com");
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
        return "admin/user/table-user";
    }

    @RequestMapping("/admin/user/{id}")
    public String GetUserDetailPage(Model model, @PathVariable long id) {
        User user = this.userService.getUserById(id);// {id} va id phai giong nhau
        model.addAttribute("user", user); // truyen qua view
        model.addAttribute("id", id);
        return "admin/user/user-detail";
    }

    @GetMapping("/admin/user/create") // GET
    public String getUserPage(Model model) {
        model.addAttribute("newUser", new User()); // Truyền biến qua view
        return "admin/user/create";  // Đảm bảo đúng tên view (JSP)
    }

    @PostMapping("/admin/user/create") // POST
    public String createUserPage(@Valid @ModelAttribute("newUser") User moimoi, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Nếu có lỗi, trả lại trang form để người dùng sửa thông tin
            return "admin/user/create";  // Đồng nhất tên view với GET
        }
        // Nếu không có lỗi, tiến hành lưu người dùng
        this.userService.handleSaveUser(moimoi);
        return "redirect:/admin/user";  // Sau khi thành công, chuyển hướng
    }

    @RequestMapping("/admin/user/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable long id) {
        User currentUser = this.userService.getUserById(id);
        model.addAttribute("newUser", currentUser);
        return "admin/user/user-update";
    }

    @PostMapping("/admin/user/update")
    public String postUpdateConten(Model model, @ModelAttribute("newUser") User moimoi) {
        User currentUser = this.userService.getUserById(moimoi.getId());
        if (currentUser != null) {
            currentUser.setAddress(moimoi.getAddress());
            currentUser.setFullName(moimoi.getFullName());
            currentUser.setPhone(moimoi.getPhone());
            this.userService.handleSaveUser(currentUser);
        }
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUser(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        model.addAttribute("newUser", new User());
        return "admin/user/user-delete";
    }

    @PostMapping("/admin/user/delete")
    public String postDeleteUser(Model model, @ModelAttribute("newUser") User moimoi) {
        this.userService.deleteUser(moimoi.getId());
        return "redirect:/admin/user";
    }
}
