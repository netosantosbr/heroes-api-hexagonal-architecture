package br.com.gubee.ports;


import br.com.gubee.ports.request.PersistHeroCommand;


import java.util.UUID;

public interface CreateHeroPort {
    UUID create(PersistHeroCommand hero, UUID powerStatsUUID);
}
