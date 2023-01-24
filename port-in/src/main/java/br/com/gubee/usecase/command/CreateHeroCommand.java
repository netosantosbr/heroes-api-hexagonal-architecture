package br.com.gubee.usecase.command;

import br.com.gubee.model.enums.Race;
import br.com.gubee.usecase.request.CreateHeroRequest;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateHeroCommand {
    private String name;
    private Race race;
    private int strength;
    private int agility;
    private int dexterity;
    private int intelligence;
}
