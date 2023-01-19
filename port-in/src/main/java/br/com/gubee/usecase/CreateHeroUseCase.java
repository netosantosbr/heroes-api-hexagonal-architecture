package br.com.gubee.usecase;


import br.com.gubee.usecase.request.CreateHeroRequest;

import java.util.UUID;

public interface CreateHeroUseCase {
    UUID create(CreateHeroRequest createHeroRequest);
}
