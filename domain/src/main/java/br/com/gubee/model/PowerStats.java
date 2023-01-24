package br.com.gubee.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
@Builder
public class PowerStats {

    private UUID id;
    private int strength;
    private int agility;
    private int dexterity;
    private int intelligence;
    private Instant createdAt;
    private Instant updatedAt;

    public PowerStats(int strength, int agility, int dexterity, int intelligence) {
        this.strength = strength;
        this.agility = agility;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
    }

    public boolean validate() {
        if( (this.strength < 0 || this.strength > 10) || (this.agility < 0 || this.agility > 10) ||
        (this.dexterity < 0 || this.dexterity > 10) || (this.intelligence < 0 || this.intelligence > 10)) {
            return false;
        } else {
            return true;
        }
    }

}
