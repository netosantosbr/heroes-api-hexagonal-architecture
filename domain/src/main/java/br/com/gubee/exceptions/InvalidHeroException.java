package br.com.gubee.exceptions;

public class InvalidHeroException extends HeroesContextException {
    public InvalidHeroException() {
        super("Herói inválido");
    }
}
