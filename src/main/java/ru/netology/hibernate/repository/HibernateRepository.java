package ru.netology.hibernate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import ru.netology.hibernate.entity.City;
import ru.netology.hibernate.entity.Human;
import ru.netology.hibernate.entity.Persons;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class HibernateRepository {
    private final String SCRIPT_FILE_NAME = "myScript.sql";
    private final int AGE = 93;
    private final EntityManager entityManager;
    private final PersonsRepository personsRepository;
    private final CityRepository cityRepository;

    @Transactional
    public List<Persons> getPersonsByCity(String city) {
        creatingDefaultCities();
        Query query = entityManager.createQuery(read(SCRIPT_FILE_NAME));
        query.setParameter("nameCity", city.toLowerCase());
        return query.getResultList();
    }

    private void creatingDefaultCities() {
        var cities = Stream.of("Moscow", "Novosibirsk", "Slavgorod")
                .map(n -> City.builder()
                        .name(n)
                        .build())
                .collect(Collectors.toUnmodifiableList());
        cityRepository.saveAll(cities);
        creatingDefaultPersons(cities);
    }

    private void creatingDefaultPersons(List<City> cities) {
        var names = List.of("Дмитрий", "Алексей", "Олег", "Николай", "Иван");
        var surnames = List.of("Иванов", "Петров", "Зябликов", "Пушкин", "Галогер");
        var random = new Random();

        IntStream.range(0, 50)
                .forEach(i -> {
                    var person = Persons.builder()
                            .human(Human.builder()
                                    .name(names.get(random.nextInt(names.size())))
                                    .surname(surnames.get(random.nextInt(surnames.size())))
                                    .age(random.nextInt(AGE))
                                    .build())
                            .phoneNumber("8-913-151-10-" + random.nextInt(AGE))
                            .cityOfLiving(cities.get(random.nextInt(cities.size())))
                            .build();
                    personsRepository.save(person);
                });
    }

    private static String read(String scriptFileName) {
        try (InputStream is = new ClassPathResource(scriptFileName).getInputStream();
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is))) {
            return bufferedReader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Persons createService(String name, String surname, int age, String phoneNumber, String city) {
        var person = Persons.builder()
                .human(Human.builder()
                        .name(name)
                        .surname(surname)
                        .age(age)
                        .build())
                .phoneNumber(phoneNumber)
                .cityOfLiving(cityRepository.findByName(city))
                .build();
        personsRepository.save(person);
        return person;
    }

    public List<Persons> readAllService() {
        return personsRepository.findBy();
    }

    public Persons updateService(String name, String surname, int age, String phoneNumber) {
        var person = personsRepository.findFirst1ByHuman_NameAndHuman_SurnameAndHuman_Age(name, surname, age);
        person.setPhoneNumber(phoneNumber);
        personsRepository.save(person);
        return person;
    }

    public String deleteService(String name, String surname, int age) {
        try {
            var person = personsRepository.findFirst1ByHuman_NameAndHuman_SurnameAndHuman_Age(name, surname, age);
            personsRepository.delete(person);
            return "deletion successful";
        } catch (Exception e) {
            e.printStackTrace();
            return "deletion not successful";
        }
    }

    public City findCity(String city) {
        return cityRepository.findByName(city);
    }

    public List<Persons> findAge(int age) {
        return personsRepository.findByHuman_AgeGreaterThan(age);
    }

    public Optional<Persons> findByNameSurname(String name, String surname) {
        var s = personsRepository.findFirstByHuman_NameAndHuman_Surname(name, surname);
        return s;
    }
}
