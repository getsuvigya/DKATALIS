package exception;

public class ParkLotFullException extends Exception {
   private String userMessage="Sorry, parking lot is full";
   public ParkLotFullException(){

   }
    public ParkLotFullException(String msg){
        this.userMessage = msg;
    }
    public String getUserMsg() {
        return userMessage;
    }
}
