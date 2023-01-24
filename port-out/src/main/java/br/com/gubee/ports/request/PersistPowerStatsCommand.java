package br.com.gubee.ports.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
@AllArgsConstructor
public class PersistPowerStatsCommand {
    private int strength;
    private int agility;
    private int dexterity;
    private int intelligence;
}
