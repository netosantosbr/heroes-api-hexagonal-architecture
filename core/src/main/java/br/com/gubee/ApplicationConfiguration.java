package br.com.gubee;

import br.com.gubee.ports.*;
import br.com.gubee.service.*;
import br.com.gubee.usecase.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {

    private final CompareTwoHeroesPort compareTwoHeroesPort;
    private final CreateHeroPort createHeroPort;
    private final CreatePowerStatsPort createPowerStatsPort;
    private final DeleteHeroPort deleteHeroPort;
    private final DeletePowerStatsPort deletePowerStatsPort;
    private final FindAllHeroPort findAllHeroPort;
    private final FindHeroByIdPort findHeroByIdPort;
    private final FindHeroByNamePort findHeroByNamePort;
    private final FindPowerStatsIdFromHeroPort findPowerStatsIdFromHeroPort;
    private final UpdateHeroPort updateHeroPort;


    @Bean
    public CompareTwoHeroesUseCase compare() {
        return new CompareTwoHeroesService(compareTwoHeroesPort);
    }

    @Bean
    public CreateHeroUseCase create() {
        return new CreateHeroService(createHeroPort, createPowerStatsPort);
    }

    @Bean
    public DeleteHeroUseCase deleteHero() {
        return new DeleteHeroService(deleteHeroPort, deletePowerStatsPort, findPowerStatsIdFromHeroPort);
    }

    @Bean
    public FindAllHeroUseCase findAll() {
        return new FindAllHeroService(findAllHeroPort);
    }

    @Bean
    public FindHeroByNameUseCase findByName() {
        return new FindHeroByNameService(findHeroByNamePort);
    }

    @Bean
    public FindHeroByIdUseCase findById() {
        return new FindHeroByIdService(findHeroByIdPort);
    }

    @Bean
    public UpdateHeroUseCase update() {
        return new UpdateHeroService(updateHeroPort);
    }

}
