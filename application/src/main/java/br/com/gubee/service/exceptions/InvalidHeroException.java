package br.com.gubee.service.exceptions;

public class InvalidHeroException extends HeroesContextException {
    public InvalidHeroException() {
        super("Herói inválido");
    }
}
