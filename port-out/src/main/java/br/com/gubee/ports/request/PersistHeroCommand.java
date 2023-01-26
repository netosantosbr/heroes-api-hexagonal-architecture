package br.com.gubee.ports.request;

import lombok.*;
import br.com.gubee.model.enums.Race;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
public class PersistHeroCommand {
    private String name;
    private Race race;
}
