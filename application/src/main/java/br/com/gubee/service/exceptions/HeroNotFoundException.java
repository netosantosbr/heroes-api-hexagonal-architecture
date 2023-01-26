package br.com.gubee.service.exceptions;

public class HeroNotFoundException extends HeroesContextException {
    public HeroNotFoundException() {
        super("Herói não encontrado!");
    }
}
