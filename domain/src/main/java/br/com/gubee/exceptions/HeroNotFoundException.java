package br.com.gubee.exceptions;

public class HeroNotFoundException extends HeroesContextException {
    public HeroNotFoundException() {
        super("Herói não encontrado!");
    }
}
