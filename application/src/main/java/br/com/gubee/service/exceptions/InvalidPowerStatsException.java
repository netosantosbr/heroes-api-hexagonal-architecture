package br.com.gubee.service.exceptions;

public class InvalidPowerStatsException extends HeroesContextException {
    public InvalidPowerStatsException() {
        super("PowerStats inválido");
    }
}
