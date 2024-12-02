import java.util.Scanner;

public interface CrudCliente {
    void listarClientes();
    void adicionarCliente(Scanner scanner);
    void removerCliente(Scanner scanner)  throws ClienteNaoEncontradoException ;
}
