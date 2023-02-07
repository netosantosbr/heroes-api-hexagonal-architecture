package br.com.gubee.controller;


import br.com.gubee.exceptions.HeroesContextException;
import br.com.gubee.usecase.model.HeroRespIn;
import br.com.gubee.usecase.UpdateHeroUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1/heroes", produces = APPLICATION_JSON_VALUE)
public class UpdateHeroController {

    private final UpdateHeroUseCase updateHeroUseCase;

    @PutMapping("/{id}")
    public ResponseEntity<HeroRespIn> update(@PathVariable UUID id, @RequestBody HeroRespIn hero) {
        try {
            updateHeroUseCase.update(id, hero);
            return ResponseEntity.ok().build();
        } catch (HeroesContextException hce) {
            return ResponseEntity.notFound().build();
        }
    }
}
