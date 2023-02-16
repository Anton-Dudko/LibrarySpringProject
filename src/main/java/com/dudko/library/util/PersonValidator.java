package com.dudko.library.util;

import com.dudko.library.dao.PersonDAO;
import com.dudko.library.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    @Autowired
    private PersonDAO personDAO;

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        if (personDAO.findByFio(person.getFio()).isPresent()) {
            errors.rejectValue("fio", "", "Такие данные уже существуют!");
        }
    }
}
