package br.com.gubee.controller;

import br.com.gubee.usecase.model.HeroCompareRespIn;
import br.com.gubee.usecase.CompareTwoHeroesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/heroes", produces = APPLICATION_JSON_VALUE)
public class CompareTwoHeroesController {

    private final CompareTwoHeroesUseCase compareTwoHeroesUseCase;


    @GetMapping("/compare")
    public ResponseEntity<HeroCompareRespIn> compareTwoHeroes(@RequestParam UUID firstId, @RequestParam UUID secondId) {
        try {
            return ResponseEntity.ok().body(compareTwoHeroesUseCase.compareTwoHeroes(firstId, secondId));
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

}
