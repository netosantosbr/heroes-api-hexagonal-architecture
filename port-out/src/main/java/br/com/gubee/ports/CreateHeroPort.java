package br.com.gubee.ports;


import br.com.gubee.ports.request.CreateHeroRequestOut;


import java.util.UUID;

public interface CreateHeroPort {
    UUID create(CreateHeroRequestOut hero, UUID powerStatsUUID);
}
