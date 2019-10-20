package guru.springframework.controllers;

import guru.springframework.domain.User;
import guru.springframework.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.transaction.Transactional;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    @Autowired
    public void setUserService(UserService userService){
        this.userService=userService;
    }
    @Transactional
    @RequestMapping({"/list","/"})
    public String listUser(Model model){
        model.addAttribute("users",userService.listAll());
        return "listUser";
    }
    @RequestMapping("/show/{id}")
    public String getUser(@PathVariable Integer id,Model model){
        model.addAttribute("user",userService.getById(id));
        return "showUser";
    }
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        model.addAttribute("user",userService.getById(id));
        return "userForm";
    }
    @RequestMapping("/new")
    public String newUser(Model model){
        model.addAttribute("user",new User());
        return "userForm";
    }
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String saveOrUpdate(User user){
        User savedUser=userService.saveOrUpdate(user);
        return "redirect:/showUser/"+savedUser.getId();
    }
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        userService.delete(id);
        return "redirect:/listUser";
    }
}
