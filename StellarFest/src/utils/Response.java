package utils;

public class Response {
	private boolean isSuccessful;
	private String message;

	public Response(boolean isSuccessful, String message) {
		// TODO Auto-generated constructor stub
		this.isSuccessful = isSuccessful;
		this.message = message;
	}

	public boolean isSuccessful() {
		return isSuccessful;
	}

	public void setSuccessful(boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
