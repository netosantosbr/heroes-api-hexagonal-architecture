package br.com.gubee.ports.request;

import lombok.*;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
public class PersistPowerStatsCommand {
    private int strength;
    private int agility;
    private int dexterity;
    private int intelligence;
}
