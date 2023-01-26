package br.com.gubee.usecase.model;

import lombok.*;
import br.com.gubee.model.enums.Race;
import lombok.experimental.Accessors;

import java.util.UUID;

@Accessors(chain = true)
@Getter
@Setter
public class HeroRespIn {
    private UUID id;
    private String name;
    private Race race;
    private int strength;
    private int agility;
    private int dexterity;
    private int intelligence;
}
