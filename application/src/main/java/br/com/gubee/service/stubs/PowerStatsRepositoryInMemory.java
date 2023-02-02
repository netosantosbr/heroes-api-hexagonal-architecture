package br.com.gubee.service.stubs;

import br.com.gubee.model.PowerStats;
import br.com.gubee.ports.CreatePowerStatsPort;
import br.com.gubee.ports.DeletePowerStatsPort;
import br.com.gubee.ports.request.PersistPowerStatsCommand;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PowerStatsRepositoryInMemory  implements CreatePowerStatsPort, DeletePowerStatsPort {

    private List<PowerStats> powerStatsDatabaseInMemory = new ArrayList<>();

    @Override
    public UUID create(PersistPowerStatsCommand powerStats) {
        var ps = new PowerStats(powerStats.getStrength(), powerStats.getAgility(),
                powerStats.getDexterity(), powerStats.getIntelligence());

        ps.setId(UUID.randomUUID());
        ps.setCreatedAt(Instant.now());
        ps.setUpdatedAt(Instant.now());

        powerStatsDatabaseInMemory.add(ps);


        return ps.getId();
    }

    @Override
    public void delete(UUID id) {
        powerStatsDatabaseInMemory.forEach(powerStats -> {
            if(powerStats.getId() == id) {
                powerStatsDatabaseInMemory.remove(powerStats);
            }
        });
    }

    public PowerStats findById(UUID id) {
        for(var powerStats : powerStatsDatabaseInMemory) {
            if(powerStats.getId() == id) {
                return powerStats;
            }
        }
        return null;
    }
}
