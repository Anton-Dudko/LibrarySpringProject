package com.dudko.library.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {
    private int id;
    @NotEmpty(message = "Поле Название не должно быть пустым!")
    @Size(min = 5, max = 70, message = "Название должно быть длинной от 5 до 70 символов!")
    private String name;
    @NotEmpty(message = "Поле Автор не должно быть пустым!")
    @Size(min = 5, max = 70, message = "Поле Автор должно быть длинной от 5 до 70 символов!")
    private String author;
    @Min(value = 1500, message = "Год публикации не меньше 1500!")
    private int year;

    public Book(int id, String name, String author, int year) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
