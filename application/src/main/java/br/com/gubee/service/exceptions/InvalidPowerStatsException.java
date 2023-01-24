package br.com.gubee.service.exceptions;

public class InvalidPowerStatsException extends RuntimeException {
    public InvalidPowerStatsException() {
        super("PowerStats inválido");
    }
}
