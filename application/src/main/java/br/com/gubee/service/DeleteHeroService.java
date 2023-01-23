package br.com.gubee.service;

import br.com.gubee.annotation.DomainService;
import br.com.gubee.ports.DeleteHeroPort;
import br.com.gubee.ports.DeletePowerStatsPort;
import br.com.gubee.ports.FindPowerStatsIdFromHeroPort;
import br.com.gubee.usecase.DeleteHeroUseCase;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@DomainService
public class DeleteHeroService implements DeleteHeroUseCase {

    private final DeleteHeroPort deleteHeroPort;
    private final DeletePowerStatsPort deletePowerStatsPort;
    private final FindPowerStatsIdFromHeroPort findPowerStatsIdFromHeroPort;

    @Override
    public void delete(UUID id) {
        UUID powerStatsIdFromHero = findPowerStatsIdFromHeroPort.findPowerStatsIdFromHero(id);
        deleteHeroPort.delete(id);
        deletePowerStatsPort.delete(powerStatsIdFromHero);
    }
}
