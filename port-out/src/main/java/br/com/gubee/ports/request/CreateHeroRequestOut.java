package br.com.gubee.ports.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import br.com.gubee.model.enums.Race;

@Builder
@AllArgsConstructor
@Data
public class CreateHeroRequestOut {
    private String name;
    private Race race;
}
