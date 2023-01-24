package br.com.gubee.usecase;


import br.com.gubee.usecase.command.CreateHeroCommand;
import br.com.gubee.usecase.request.CreateHeroRequest;

import java.util.UUID;

public interface CreateHeroUseCase {
    UUID create(CreateHeroCommand createHeroCommand);
}
