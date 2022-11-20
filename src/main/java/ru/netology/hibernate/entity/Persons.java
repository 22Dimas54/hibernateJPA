package ru.netology.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Persons {
    @Id
    private Human human;

    @Column
    private String phoneNumber;

    @ManyToOne(optional = false)
    private City cityOfLiving;
}
