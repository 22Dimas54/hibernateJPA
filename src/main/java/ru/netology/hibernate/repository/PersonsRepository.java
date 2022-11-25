package ru.netology.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.netology.hibernate.entity.Human;
import ru.netology.hibernate.entity.Persons;

import java.util.List;
import java.util.Optional;

public interface PersonsRepository extends JpaRepository<Persons, Human> {
    List<Persons> findBy();

    Persons findFirst1ByHuman_NameAndHuman_SurnameAndHuman_Age(String name, String surname, int age);

    List<Persons> findByHuman_AgeGreaterThan(int age);

    Optional<Persons> findFirstByHuman_NameAndHuman_Surname(String name, String surname);
}
