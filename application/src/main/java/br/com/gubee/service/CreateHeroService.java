package br.com.gubee.service;

import br.com.gubee.annotation.DomainService;
import br.com.gubee.ports.CreateHeroPort;
import br.com.gubee.ports.CreatePowerStatsPort;
import br.com.gubee.ports.request.PersistHeroCommand;
import br.com.gubee.ports.request.PersistPowerStatsCommand;
import br.com.gubee.service.exceptions.InvalidHeroException;
import br.com.gubee.service.exceptions.InvalidPowerStatsException;
import br.com.gubee.usecase.CreateHeroUseCase;
import br.com.gubee.usecase.command.CreateHeroCommand;
import lombok.RequiredArgsConstructor;
import br.com.gubee.model.Hero;
import br.com.gubee.model.PowerStats;

import java.util.UUID;

@RequiredArgsConstructor
@DomainService
public class CreateHeroService implements CreateHeroUseCase {

    private final CreateHeroPort createHeroPort;
    private final CreatePowerStatsPort createPowerStatsPort;

    @Override
    public UUID create(CreateHeroCommand createHeroCommand){
        PowerStats powerStats = fromCommandToPowerStats(createHeroCommand);

        if(!powerStats.validate()) {
            throw new InvalidPowerStatsException();
        }

        UUID powerStatsId = createPowerStatsPort.create(PersistPowerStatsCommand.builder()
                .strength(powerStats.getStrength())
                .agility(powerStats.getAgility())
                .dexterity(powerStats.getDexterity())
                .intelligence(powerStats.getIntelligence())
                .build());



        Hero hero = fromCommandToHero(createHeroCommand, powerStatsId);

        if(!hero.validate()) {
            throw new InvalidHeroException();
        }

        PersistHeroCommand persistHeroCommand = PersistHeroCommand.builder()
                .name(hero.getName())
                .race(hero.getRace())
                .build();

        return createHeroPort.create(persistHeroCommand, powerStatsId);
    }

    public Hero fromCommandToHero(CreateHeroCommand cmd, UUID id) {
        return new Hero(cmd.getName(), cmd.getRace(), id);
    }

    public PowerStats fromCommandToPowerStats(CreateHeroCommand cmd) {
        return new PowerStats(cmd.getStrength(), cmd.getAgility(),
                cmd.getDexterity(), cmd.getIntelligence());
    }
}
