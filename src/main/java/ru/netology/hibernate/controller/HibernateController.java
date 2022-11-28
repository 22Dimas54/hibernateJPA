package ru.netology.hibernate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.hibernate.entity.City;
import ru.netology.hibernate.entity.Persons;
import ru.netology.hibernate.service.HibernateService;

import java.util.List;
import java.util.Optional;

@RestController
public class HibernateController {
    @Autowired
    private HibernateService hibernateService;

    @GetMapping("/persons/by-city")
    public List<Persons> handlerController(@RequestParam("city") String city) {
        return hibernateService.handlerService(city);
    }

    //    CRUD
    @GetMapping("/persons/create")
    public Persons createController(@RequestParam("name") String name,
                                    @RequestParam("surname") String surname,
                                    @RequestParam("age") int age,
                                    @RequestParam("phoneNumber") String phoneNumber,
                                    @RequestParam("city") String city) {
        return hibernateService.createService(name, surname, age, phoneNumber, city);
    }

    @GetMapping("/persons/readAll")
    public List<Persons> readAllController() {
        return hibernateService.readAllService();
    }

    @GetMapping("/persons/update")
    public Persons updateController(@RequestParam("name") String name,
                                    @RequestParam("surname") String surname,
                                    @RequestParam("age") int age,
                                    @RequestParam("phoneNumber") String phoneNumber) {
        return hibernateService.updateService(name, surname, age, phoneNumber);
    }

    @GetMapping("/persons/delete")
    public String deleteController(@RequestParam("name") String name,
                                   @RequestParam("surname") String surname,
                                   @RequestParam("age") int age) {
        return hibernateService.deleteService(name, surname, age);
    }

    @GetMapping("/persons/find-city")
    public City findCityController(@RequestParam("city") String city) {
        return hibernateService.findCity(city);
    }

    @GetMapping("/persons/find-age")
    public List<Persons> findAgeController(@RequestParam("age") int age) {
        return hibernateService.findAge(age);
    }

    @GetMapping("/persons/find-name-surname")
    public Optional<Persons> findByNameSurnameController(@RequestParam("name") String name, @RequestParam("surname") String surname) {
        return hibernateService.findByNameSurname(name, surname);
    }

    @GetMapping("/anyRequest")
    public String anyRequest() {
        return "any request";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
