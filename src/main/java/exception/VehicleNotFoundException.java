package exception;

public class VehicleNotFoundException extends Exception {
    private String userMessage;

    public VehicleNotFoundException() {
    }
    public VehicleNotFoundException(String msg) {
        this.userMessage = msg;
    }
    public String getUserMsg() {
        return userMessage;
    }
}