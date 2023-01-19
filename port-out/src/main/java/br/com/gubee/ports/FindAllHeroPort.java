package br.com.gubee.ports;


import br.com.gubee.ports.model.HeroRespPA;

import java.util.List;

public interface FindAllHeroPort {
    List<HeroRespPA> findAll();
}
