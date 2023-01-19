package br.com.gubee.ports;


import br.com.gubee.ports.model.HeroCompareRespPA;

import java.util.UUID;

public interface CompareTwoHeroesPort {
    HeroCompareRespPA compareTwoHeroes(UUID firstId, UUID secondId);
}
