package ru.netology.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.netology.hibernate.entity.Human;
import ru.netology.hibernate.entity.Persons;

import java.util.List;
import java.util.Optional;

public interface PersonsRepository extends JpaRepository<Persons, Human> {
    @Query("select p from Persons p")
    List<Persons> findBy();

    @Query("select p from Persons p where p.human.name = :name and p.human.surname = :surname and p.human.age = :age")
    Persons findFirst1ByHuman_NameAndHuman_SurnameAndHuman_Age(@Param("name") String name, @Param("surname") String surname, @Param("age") int age);

    @Query("select p from Persons p where p.human.age>:age")
    List<Persons> findByHuman_AgeGreaterThan(@Param("age")int age);

    @Query("select p from Persons p where p.human.name = :name and p.human.surname = :surname")
    Optional<Persons> findFirstByHuman_NameAndHuman_Surname(@Param("name")String name, @Param("surname")String surname);
}
