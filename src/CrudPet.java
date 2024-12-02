import java.util.Scanner;

public interface CrudPet {
    void adicionarPet(Scanner scanner, String fileName, String tipo);
    void editarPet(Scanner scanner, String tipo) throws PetNaoEncontradoException;
}
