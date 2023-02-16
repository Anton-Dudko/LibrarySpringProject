package com.dudko.library.controller;

import com.dudko.library.dao.BookDAO;
import com.dudko.library.dao.PersonDAO;
import com.dudko.library.model.Book;
import com.dudko.library.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private PersonDAO personDAO;

    @GetMapping
    public String showAllBook(Model model) {
        model.addAttribute("books", bookDAO.showAll());
        return "book/showAll";
    }

    @GetMapping("{id}")
    public String showBook(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.findById(id));

        Optional<Person> owner = bookDAO.findBookOwner(id);

        if (owner.isPresent()) {
            model.addAttribute("owner", owner.get());
        } else {
            model.addAttribute("people", personDAO.showAll());
        }
        return "book/showBook";
    }

    @GetMapping("/new")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        return "book/newBook";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book/newBook";
        }
        bookDAO.save(book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.remove(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.findById(id));
        return "book/editBook";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "book/editBook";
        }
        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id) {
        bookDAO.release(id);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/assign")
    public String assignBook(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        bookDAO.assignBook(id, person);
        return "redirect:/books/{id}";
    }
}
