package br.com.gubee.model;

import br.com.gubee.model.enums.Race;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE)
public class Hero {
    private UUID id;
    private String name;
    private Race race;
    private UUID powerStatsId;
    private Instant createdAt;
    private Instant updatedAt;
    private boolean enabled;


    public Hero(String name, Race race, UUID powerStatsId) {
        this.name = name;
        this.race = race;
        this.powerStatsId = powerStatsId;
    }


    public Hero(String name, Race race){
        this.name = name;
        this.race = race;
    }

    public boolean validate() {
        if( !((this.name == null || this.name == "") || (this.race == null) || this.powerStatsId == null) ) {
            return true;
        }
        return false;
    }
}
