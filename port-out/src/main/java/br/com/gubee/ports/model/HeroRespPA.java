package br.com.gubee.ports.model;

import lombok.*;
import br.com.gubee.model.enums.Race;
import lombok.experimental.Accessors;

import java.util.UUID;

@Accessors(chain = true)
@Getter
@Setter
public class HeroRespPA {
    private UUID id;
    private String name;
    private Race race;
    private int strength;
    private int agility;
    private int dexterity;
    private int intelligence;
}
