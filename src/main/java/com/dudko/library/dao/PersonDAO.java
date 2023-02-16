package com.dudko.library.dao;

import com.dudko.library.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> showAll() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person findById(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person(fio, yearOfBirth) VALUES(?, ?)", person.getFio(), person.getYearOfBirth());
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE person SET fio = ?, yearOfBirth = ? WHERE id = ?", person.getFio(), person.getYearOfBirth(), id);
    }

    public void remove(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id = ?", id);
    }

    //для валидатора
    public Optional<Person> findByFio(String fio) {
        return jdbcTemplate.query("SELECT * FROM person WHERE fio = ?", new Object[]{fio}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

}
