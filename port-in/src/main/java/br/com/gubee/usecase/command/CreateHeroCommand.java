package br.com.gubee.usecase.command;

import br.com.gubee.model.enums.Race;
import lombok.*;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
public class CreateHeroCommand {
    private String name;
    private Race race;
    private int strength;
    private int agility;
    private int dexterity;
    private int intelligence;
}
