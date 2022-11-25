package ru.netology.hibernate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.netology.hibernate.entity.City;
import ru.netology.hibernate.entity.Persons;
import ru.netology.hibernate.repository.HibernateRepository;

import java.util.List;
import java.util.Optional;

@Service
public class HibernateService {
    @Autowired
    private HibernateRepository hibernateRepository;

    public List<Persons> handlerService(String city) {
        return hibernateRepository.getPersonsByCity(city);
    }

    public Persons createService(String name, String surname, int age, String phoneNumber, String city) {
        return hibernateRepository.createService(name, surname, age, phoneNumber, city);
    }

    public List<Persons> readAllService() {
        return hibernateRepository.readAllService();
    }

    public Persons updateService(String name, String surname, int age, String phoneNumber) {
        return hibernateRepository.updateService(name, surname, age, phoneNumber);
    }

    public String deleteService(String name, String surname, int age) {
        return hibernateRepository.deleteService(name, surname, age);
    }

    public City findCity(String city) {
        return hibernateRepository.findCity(city);
    }

    public List<Persons> findAge(int age) {
        return hibernateRepository.findAge(age);
    }

    public Optional<Persons> findByNameSurname(String name, String surname) {
        return hibernateRepository.findByNameSurname(name, surname);
    }
}
