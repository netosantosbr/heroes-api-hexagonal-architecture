package br.com.gubee.ports;



import br.com.gubee.ports.request.CreatePowerStatsRequestOut;


import java.util.UUID;

public interface CreatePowerStatsPort {
    UUID create(CreatePowerStatsRequestOut powerStats);
}
