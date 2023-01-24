package br.com.gubee.controller;


import br.com.gubee.usecase.model.HeroRespIn;
import br.com.gubee.usecase.FindHeroByIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1/heroes", produces = APPLICATION_JSON_VALUE)
public class FindHeroByIdController {

    private final FindHeroByIdUseCase findHeroByIdUseCase;

    @GetMapping("/{id}")
    public ResponseEntity<HeroRespIn> findById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok().body(findHeroByIdUseCase.findById(id));
        } catch(NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
