package br.com.gubee.ports;



import br.com.gubee.ports.request.PersistPowerStatsCommand;


import java.util.UUID;

public interface CreatePowerStatsPort {
    UUID create(PersistPowerStatsCommand powerStats);
}
