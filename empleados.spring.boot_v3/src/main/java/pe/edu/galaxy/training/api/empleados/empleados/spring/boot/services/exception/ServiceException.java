package pe.edu.galaxy.training.api.empleados.empleados.spring.boot.services.exception;

public class ServiceException extends Exception{
	
	private static final long serialVersionUID = 4222972740058473227L;
	
	public ServiceException() {
		
	}

	public ServiceException(String message) {
		super(message);
		
	}

	public ServiceException(Throwable cause) {
		super(cause);
		
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}
}
