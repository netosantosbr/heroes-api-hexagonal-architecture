package br.com.gubee.service.exceptions;

public class InvalidHeroException extends RuntimeException {
    public InvalidHeroException() {
        super("Herói inválido");
    }
}
