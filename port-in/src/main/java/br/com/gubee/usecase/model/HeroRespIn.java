package br.com.gubee.usecase.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.enums.Race;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HeroRespIn {
    private UUID id;
    private String name;
    private Race race;
    private int strength;
    private int agility;
    private int dexterity;
    private int intelligence;
}
