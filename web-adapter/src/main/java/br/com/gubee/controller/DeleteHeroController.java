package br.com.gubee.controller;

import br.com.gubee.exceptions.HeroesContextException;
import br.com.gubee.usecase.DeleteHeroUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1/heroes", produces = APPLICATION_JSON_VALUE)
public class DeleteHeroController {

    private final DeleteHeroUseCase deleteHeroUseCase;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        try {
            deleteHeroUseCase.delete(id);
            return ResponseEntity.ok().build();
        } catch(HeroesContextException hce) {
            return ResponseEntity.notFound().build();
        }
    }
}
