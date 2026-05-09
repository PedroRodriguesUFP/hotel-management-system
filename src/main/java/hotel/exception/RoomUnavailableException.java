package hotel.exception;

public class RoomUnavailableException extends RuntimeException {
    public RoomUnavailableException(int roomNumber) {
        super("Room " + roomNumber + " is not available.");
    }
}
