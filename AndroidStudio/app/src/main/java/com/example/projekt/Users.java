package com.example.projekt;

public class Users {
    String email;
    String name;
    Integer age;
    String gender;

    public Users(String email, String name, Integer age, String gender) {
        this.email = email;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getEmail() {return email;}

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }
}
