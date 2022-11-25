package ru.netology.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.netology.hibernate.entity.City;

public interface CityRepository extends JpaRepository<City, Long> {
    City findByName(String name);
}
