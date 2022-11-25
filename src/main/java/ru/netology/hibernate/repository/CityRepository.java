package ru.netology.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.netology.hibernate.entity.City;

public interface CityRepository extends JpaRepository<City, Long> {
    @Query("select c from City c where c.name=:name")
    City findByName(@Param("name") String name);
}
