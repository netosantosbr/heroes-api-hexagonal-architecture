package br.com.gubee.service;

import br.com.gubee.ports.CreateHeroPort;
import br.com.gubee.ports.CreatePowerStatsPort;
import br.com.gubee.ports.request.CreateHeroRequestOut;
import br.com.gubee.ports.request.CreatePowerStatsRequestOut;
import br.com.gubee.usecase.CreateHeroUseCase;
import br.com.gubee.usecase.request.CreateHeroRequest;
import lombok.RequiredArgsConstructor;
import model.Hero;
import model.PowerStats;

import java.util.UUID;

@RequiredArgsConstructor
public class CreateHeroService implements CreateHeroUseCase {

    private final CreateHeroPort createHeroPort;
    private final CreatePowerStatsPort createPowerStatsPort;

    @Override
    public UUID create(CreateHeroRequest createHeroRequest) {
        PowerStats powerStats = new PowerStats(createHeroRequest.getStrength(),
          createHeroRequest.getAgility(), createHeroRequest.getDexterity(), createHeroRequest.getIntelligence());

        UUID powerStatsId = createPowerStatsPort.create(CreatePowerStatsRequestOut.builder()
                .strength(powerStats.getStrength())
                .agility(powerStats.getAgility())
                .dexterity(powerStats.getDexterity())
                .intelligence(powerStats.getIntelligence())
                .build());


        Hero hero = new Hero(createHeroRequest.getName(), createHeroRequest.getRace(), powerStatsId);

        CreateHeroRequestOut createHeroRequestOut = CreateHeroRequestOut.builder()
                .name(hero.getName())
                .race(hero.getRace())
                .build();

        return createHeroPort.create(createHeroRequestOut, powerStatsId);
    }
}
