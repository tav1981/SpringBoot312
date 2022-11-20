package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import java.util.List;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users")
    public String printUsers(Model model) {
        List<User> users = userService.listUsers();
        //создаем 5 пользователей если нет ни одного
        if (users.isEmpty()) {
            userService.add(new User("name1", "last1", "1@dd.w"));
            userService.add(new User("name2", "last2", "2@dd.w"));
            userService.add(new User("name3", "last3", "3@dd.w"));
            userService.add(new User("name4", "last4", "4@dd.w"));
            userService.add(new User("name5", "last5", "5@dd.w"));
        }
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping(value = "/add")
    public String creatUsersForm(User user){
        return "add";
    }

    @PostMapping(value = "add")
    public String createUsers(User user) {
        userService.add(user);
        return "redirect:/users";
    }

    @GetMapping(value = "/edit/{id}")
    public String editUsersForm(@PathVariable("id") Long id, Model model){
        User user = userService.getForId(id);

        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping(value = "edit")
    public String editUsers(User user) {
        userService.add(user);
        return "redirect:/users";
    }

    @PostMapping (value = "/delete")
    public String deleteUsers(ModelMap model, @RequestParam(value = "delete", required = false, defaultValue = "0") Long deleteId) {
        userService.dell(deleteId);
        return "redirect:/users";
    }
}
