package br.com.gubee.service;

import br.com.gubee.annotation.DomainService;
import br.com.gubee.ports.CompareTwoHeroesPort;
import br.com.gubee.exceptions.HeroNotFoundException;
import br.com.gubee.usecase.CompareTwoHeroesUseCase;
import br.com.gubee.usecase.model.HeroCompareRespIn;
import lombok.RequiredArgsConstructor;

import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@DomainService
public class CompareTwoHeroesService implements CompareTwoHeroesUseCase {

    private final CompareTwoHeroesPort compareTwoHeroesPort;

    @Override
    public HeroCompareRespIn compareTwoHeroes(UUID firstId, UUID secondId) {
        HeroCompareRespIn heroCompareRespWA;
        try {
            var heroCompareRespPA = compareTwoHeroesPort.compareTwoHeroes(firstId, secondId);
            heroCompareRespWA = HeroCompareRespIn.builder()
                    .firstHeroId(heroCompareRespPA.getFirstHeroId())
                    .secondHeroId(heroCompareRespPA.getSecondHeroId())
                    .strengthDifference(heroCompareRespPA.getStrengthDifference())
                    .agilityDifference(heroCompareRespPA.getAgilityDifference())
                    .dexterityDifference(heroCompareRespPA.getDexterityDifference())
                    .intelligenceDifference(heroCompareRespPA.getIntelligenceDifference())
                    .build();
        } catch(NoSuchElementException e) {
            throw new HeroNotFoundException();
        }

        return heroCompareRespWA;
    }
}
