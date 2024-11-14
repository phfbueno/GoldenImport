package com.github.phfbueno.GoldenImport.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import jakarta.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class GoldenRaspberryAward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int yearMovie;
    private String title;
    private String studios;
    private String producers;
    private Boolean winner;

    public GoldenRaspberryAward(int i, String s, String s1, String s2, boolean b) {
    }
}
