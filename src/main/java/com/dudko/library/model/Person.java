package com.dudko.library.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Person {
    private int id;
    @NotEmpty(message = "Поле ФИО не должно быть пустым!")
    @Size(min = 5, max = 70, message = "Имя должно быть длинной от 5 до 70 символов!")
    private String fio;
    @Min(value = 1900, message = "Год рождения не меньше 1900!")
    private int yearOfBirth;

    public Person() {
    }

    public Person(int id, String fio, int yearOfBirth) {
        this.id = id;
        this.fio = fio;
        this.yearOfBirth = yearOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
}
