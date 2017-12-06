public class PeselException extends Throwable {
    public PeselException(Exception e) {
        System.out.println("Pesel is invalid!");
    }
}
