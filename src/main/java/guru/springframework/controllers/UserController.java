package guru.springframework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import guru.springframework.domain.Customer;
import guru.springframework.domain.User;
import guru.springframework.services.UserService;

@RequestMapping("/user")
@Controller
public class UserController {

    private UserService userService;
    @Autowired
    public void setUserService(UserService userService){
        this.userService=userService;
    }
    @RequestMapping(method=RequestMethod.GET ,value="/list")
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
    @RequestMapping(method = RequestMethod.POST)
    public String createCustomer(User user){
        User newUser= userService.saveOrUpdate(user);
        return "redirect:/user/show/"+newUser.getId();
    }
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        userService.delete(id);
        return "redirect:/user/list";
    }
}
