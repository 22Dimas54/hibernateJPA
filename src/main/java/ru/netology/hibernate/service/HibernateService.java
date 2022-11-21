package ru.netology.hibernate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.netology.hibernate.entity.Persons;
import ru.netology.hibernate.repository.HibernateRepository;

import java.util.List;

@Service
public class HibernateService {
    @Autowired
    private HibernateRepository hibernateRepository;

    public List<Persons> handlerServiceCity(String city) {
        return hibernateRepository.getPersonsByCity(city);
    }

    public List<String> handlerServiceCustomer(String name) {
        return hibernateRepository.getProductName(name);
    }
}
