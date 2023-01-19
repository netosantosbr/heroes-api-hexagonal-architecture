package br.com.gubee.service;

import br.com.gubee.ports.CompareTwoHeroesPort;
import br.com.gubee.ports.model.HeroCompareRespPA;
import br.com.gubee.usecase.CompareTwoHeroesUseCase;
import br.com.gubee.usecase.model.HeroCompareRespIn;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class CompareTwoHeroesService implements CompareTwoHeroesUseCase {

    private final CompareTwoHeroesPort compareTwoHeroesPort;

    @Override
    public HeroCompareRespIn compareTwoHeroes(UUID firstId, UUID secondId) {
        HeroCompareRespPA heroCompareRespPA = compareTwoHeroesPort.compareTwoHeroes(firstId, secondId);
        HeroCompareRespIn heroCompareRespWA = HeroCompareRespIn.builder()
                .firstHeroId(heroCompareRespPA.getFirstHeroId())
                .secondHeroId(heroCompareRespPA.getSecondHeroId())
                .strengthDifference(heroCompareRespPA.getStrengthDifference())
                .agilityDifference(heroCompareRespPA.getAgilityDifference())
                .dexterityDifference(heroCompareRespPA.getDexterityDifference())
                .intelligenceDifference(heroCompareRespPA.getIntelligenceDifference())
                .build();


        return heroCompareRespWA;
    }
}
