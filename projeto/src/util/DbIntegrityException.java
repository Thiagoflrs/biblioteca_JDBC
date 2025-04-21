package util;

public class DbIntegrityException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	// Exceção personalizada para integridade referencial
	public DbIntegrityException(String msg) {
		super(msg);
	}
}
