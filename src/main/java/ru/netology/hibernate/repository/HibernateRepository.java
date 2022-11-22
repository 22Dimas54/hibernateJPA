package ru.netology.hibernate.repository;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import ru.netology.hibernate.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Repository
public class HibernateRepository {
    private final String SCRIPT_FILE_NAME = "myScript.sql";
    private final String SCRIPT_FILE_GET_PRODUCT_NAME = "myScriptGetProductName.sql";
    private final int AGE = 93;

    private static final List<String> NAMES = List.of("Дмитрий", "Алексей", "Олег", "Николай", "Иван");
    private static final List<String> SURNAMES = List.of("Иванов", "Петров", "Зябликов", "Пушкин", "Галогер");
    private static final List<String> PRODUCTS = List.of("Хлеб", "Молоко", "Печенье", "Варенье", "Шоколад");
    private static List<Customer> Customers = new ArrayList<>();
    private static final Random RANDOM = new Random();
    private static final int COUNT = 50;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<Persons> getPersonsByCity(String city) {
//      creatingDefaultCities(entityManager);
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
        IntStream.range(0, COUNT)
                .forEach(i -> {
                    var person = Persons.builder()
                            .human(Human.builder()
                                    .name(NAMES.get(RANDOM.nextInt(NAMES.size())))
                                    .surname(SURNAMES.get(RANDOM.nextInt(SURNAMES.size())))
                                    .age(RANDOM.nextInt(AGE))
                                    .build())
                            .phoneNumber("8-913-151-10-" + RANDOM.nextInt(AGE))
                            .cityOfLiving(cities.get(RANDOM.nextInt(cities.size())))
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

    @Transactional
    public List<String> getProductName(String name) {
//        creatingDefaultCustomers(entityManager);
//        creatingDefaultOrders(entityManager);

        Query query = entityManager.createQuery(read(SCRIPT_FILE_GET_PRODUCT_NAME));
        query.setParameter("nameCustomer", name.toLowerCase());
        return query.getResultList();
    }

    private void creatingDefaultOrders(EntityManager entityManager) {
        IntStream.range(0, COUNT)
                .forEach(i -> {
                    var order = Order.builder()
                            .date(new Date())
                            .customerId(Customers.get(RANDOM.nextInt(Customers.size())))
                            .productName(PRODUCTS.get(RANDOM.nextInt(PRODUCTS.size())))
                            .amount(RANDOM.nextInt(COUNT))
                            .build();
                    entityManager.persist(order);
                });
    }

    private void creatingDefaultCustomers(EntityManager entityManager) {
        IntStream.range(0, COUNT)
                .forEach(i -> {
                    var customer = Customer.builder()
                            .name(NAMES.get(RANDOM.nextInt(NAMES.size())))
                            .surname(SURNAMES.get(RANDOM.nextInt(SURNAMES.size())))
                            .age(RANDOM.nextInt(AGE))
                            .phoneNumber("8-913-151-10-" + RANDOM.nextInt(AGE))
                            .build();
                    entityManager.persist(customer);
                    Customers.add(customer);
                });
    }
}
