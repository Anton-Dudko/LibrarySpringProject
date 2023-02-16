package com.dudko.library.controller;

import com.dudko.library.dao.BookDAO;
import com.dudko.library.dao.PersonDAO;
import com.dudko.library.model.Person;
import com.dudko.library.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class UserController {

    @Autowired
    private PersonDAO personDAO;
    @Autowired
    private BookDAO bookDAO;
    @Autowired
    private PersonValidator personValidator;

    @GetMapping
    public String firstMethod(Model model) {
        model.addAttribute("persons", personDAO.showAll());
        return "index";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.findById(id));
        model.addAttribute("books", bookDAO.findByUserId(id));
        return "user/showUser";
    }

    @GetMapping("/new")
    public String addUser(Model model) {
        model.addAttribute("person", new Person());
        return "user/newUser";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "user/newUser";
        }
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.findById(id));
        return "user/editUser";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "user/editUser";
        }
        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.remove(id);
        return "redirect:/people";
    }
}
