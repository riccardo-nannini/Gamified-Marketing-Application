package it.polimi.db2.exceptions;

public class OffensiveWordException extends Exception{
	private static final long serialVersionUID = 1L;

	public OffensiveWordException(String message) {
		super(message);
	}
}