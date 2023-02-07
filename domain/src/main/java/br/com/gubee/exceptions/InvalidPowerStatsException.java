package br.com.gubee.exceptions;

public class InvalidPowerStatsException extends HeroesContextException {
    public InvalidPowerStatsException() {
        super("PowerStats inválido");
    }
}
