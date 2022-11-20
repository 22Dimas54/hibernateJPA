package ru.netology.hibernate.repository;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import ru.netology.hibernate.entity.City;
import ru.netology.hibernate.entity.Human;
import ru.netology.hibernate.entity.Persons;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Repository
public class HibernateRepository {
    private final String SCRIPT_FILE_NAME = "myScript.sql";
    private final int AGE = 93;
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<Persons> getPersonsByCity(String city) {
//        creatingDefaultCities(entityManager);
        Query query = entityManager.createQuery(read(SCRIPT_FILE_NAME));
        query.setParameter("nameCity", city.toLowerCase());
        return query.getResultList();
    }

    private void creatingDefaultCities(EntityManager entityManager) {
        var cities = Stream.of("Moscow", "Novosibirsk", "Slavgorod")
                .map(n -> City.builder()
                        .name(n)
                        .build())
                .collect(Collectors.toUnmodifiableList());
        for (City entity : cities) {
            entityManager.persist(entity);
        }
        creatingDefaultPersons(entityManager, cities);
    }

    private void creatingDefaultPersons(EntityManager entityManager, List<City> cities) {
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
                    entityManager.persist(person);
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
}
