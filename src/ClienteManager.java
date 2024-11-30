import java.io.*;
import java.util.*;

public class ClienteManager {
    private static final String FILE_NAME = "clientes.csv";
    private List<Cliente> clientes = new ArrayList<>();
    private int clienteIdCounter = 1;

    public ClienteManager() {
        carregarClientes();
    }

    public void listarClientes() {
        listarClientes(FILE_NAME, "=== Lista de Clientes ===");
    }

    private void listarClientes(String fileName, String titulo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            System.out.println(titulo);
            while ((line = reader.readLine()) != null) {
                String[] partes = line.split(",");
                System.out.println("ID: " + partes[0] + ", Nome: " + partes[1] + ", Telefone: " + partes[2]);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + fileName);
        }
    }

    public void adicionarCliente(Scanner scanner) throws OperacaoNaoPermitidaException {
        System.out.print("Digite o nome do cliente: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o telefone do cliente: ");
        String telefone = scanner.nextLine();
        int id = clienteIdCounter++;
        Cliente cliente = new Cliente(id, nome, telefone);

        for (Cliente c : clientes) {
            if (c.getNome().equalsIgnoreCase(nome)) {
                throw new OperacaoNaoPermitidaException("Um cliente com o nome " + nome + " já existe.");
            }
        }

        clientes.add(cliente);

        salvarClientes();
        System.out.println("Cliente adicionado com sucesso.");
    }

    public void removerCliente(Scanner scanner) throws ClienteNaoEncontradoException {
        System.out.print("Digite o ID do cliente para remover: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer

        boolean removido = clientes.removeIf(cliente -> cliente.getId() == id);

        if (!removido) {
            throw new ClienteNaoEncontradoException("Um cliente com o ID " + id + " não foi encontrado.");
        }

        reindexIds();
        salvarClientes();
        System.out.println("Cliente removido com sucesso.");
    }

    private void carregarClientes() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Ignora a primeira linha (cabeçalho)
                }
                String[] partes = line.split(",");
                int id = Integer.parseInt(partes[0].trim());
                String nome = partes[1].trim();
                String telefone = partes[2].trim();

                Cliente cliente = new Cliente(id, nome, telefone);
                clientes.add(cliente);

                if (id >= clienteIdCounter) {
                    clienteIdCounter = id + 1;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar o arquivo: " + FILE_NAME);
        }
    }

    private void salvarClientes() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write("ID,Nome,Telefone");
            writer.newLine();
            for (Cliente cliente : clientes) {
                writer.write(cliente.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo de clientes.");
        }
    }

    private void reindexIds() {
        int newId = 1;
        for (Cliente cliente : clientes) {
            cliente.setId(newId++);
        }
        clienteIdCounter = newId;
    }
}
