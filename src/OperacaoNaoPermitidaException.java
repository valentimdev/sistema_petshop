public class OperacaoNaoPermitidaException extends Exception {
    public OperacaoNaoPermitidaException(String message) {
        super(message);
    }

    public OperacaoNaoPermitidaException(String message, Throwable cause) {
        super(message, cause);
    }
}
