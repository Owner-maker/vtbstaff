package ru.team451.vtbstaff.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    private boolean status;
}
