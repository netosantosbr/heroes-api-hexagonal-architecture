package br.com.gubee.ports;


import br.com.gubee.ports.model.HeroRespPA;

import java.util.UUID;

public interface FindHeroByIdPort {
    HeroRespPA findById(UUID id);
}
