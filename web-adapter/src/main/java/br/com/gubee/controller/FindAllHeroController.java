package br.com.gubee.controller;


import br.com.gubee.usecase.model.HeroRespIn;
import br.com.gubee.usecase.FindAllHeroUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1/heroes", produces = APPLICATION_JSON_VALUE)
public class FindAllHeroController {

    private final FindAllHeroUseCase findAllHeroUseCase;

    @GetMapping("/findAll")
    public ResponseEntity<List<HeroRespIn>> findAll() {
        try{
            return ResponseEntity.ok().body(findAllHeroUseCase.findAll());
        } catch(NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
