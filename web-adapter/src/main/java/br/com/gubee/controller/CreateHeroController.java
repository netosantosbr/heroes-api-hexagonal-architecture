package br.com.gubee.controller;

import br.com.gubee.usecase.CreateHeroUseCase;
import br.com.gubee.usecase.command.CreateHeroCommand;
import br.com.gubee.usecase.request.CreateHeroRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

import static java.lang.String.format;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1/heroes", produces = APPLICATION_JSON_VALUE)
public class CreateHeroController {

    private final CreateHeroUseCase createHeroUseCase;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Validated @RequestBody CreateHeroRequest createHeroRequest) {
        CreateHeroCommand command = fromHeroRequestToCommand(createHeroRequest);
        UUID id = createHeroUseCase.create(command);
        return ResponseEntity.created(URI.create(format("/api/v1/heroes/%s", id))).build();
    }

    public CreateHeroCommand fromHeroRequestToCommand(CreateHeroRequest chr) {
        return new CreateHeroCommand()
                .setName(chr.getName())
                .setRace(chr.getRace())
                .setStrength(chr.getStrength())
                .setAgility(chr.getAgility())
                .setDexterity(chr.getDexterity())
                .setIntelligence(chr.getIntelligence());
    }
}
