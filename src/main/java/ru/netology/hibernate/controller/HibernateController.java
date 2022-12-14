package ru.netology.hibernate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.hibernate.entity.Persons;
import ru.netology.hibernate.service.HibernateService;

import java.util.List;

@RestController
public class HibernateController {
    @Autowired
    private HibernateService hibernateService;

    @GetMapping("/persons/by-city")
    public List<Persons> handlerController(@RequestParam("city") String city) {
        return hibernateService.handlerService(city);
    }
}
