package com.dudko.library.dao;

import com.dudko.library.model.Book;
import com.dudko.library.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> showAll() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book findById(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book(name, author, year) VALUES (?, ?, ?)", book.getName(), book.getAuthor(), book.getYear());
    }

    public void remove(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE book SET name = ?, author = ?, year = ? WHERE id = ?",
                book.getName(), book.getAuthor(), book.getYear(), id);
    }

    public List<Book> findByUserId(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE person_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }

    public Optional<Person> findBookOwner(int id) {
        return jdbcTemplate.query("SELECT Person.* FROM person JOIN book ON person.id = book.person_id WHERE book.id = ?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void release(int id) {
        jdbcTemplate.update("UPDATE book SET person_id = null WHERE id = ?", id);
    }

    public void assignBook(int id, Person person) {
        jdbcTemplate.update("UPDATE book SET person_id = ? WHERE id = ?", person.getId(), id);
    }
}
