package br.com.gubee.controller;


import br.com.gubee.usecase.model.HeroRespIn;
import br.com.gubee.usecase.FindHeroByNameUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/v1/heroes")
public class FindHeroByNameController {

    private final FindHeroByNameUseCase findHeroByNameUseCase;

    @GetMapping("")
    public ResponseEntity<List<HeroRespIn>> findByName(@RequestParam String name) {
        try {
            return ResponseEntity.ok().body(findHeroByNameUseCase.findByName(name));
        } catch(NoSuchElementException ex) {
            return ResponseEntity.ok().build();
        }
    }
}
