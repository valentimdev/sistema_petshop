public class PetNaoEncontradoException extends Exception {
    public PetNaoEncontradoException(String message) {
        super(message);
    }

    public PetNaoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }
}
