import java.io.*;
import java.util.*;

public class ClienteManager implements CrudCliente {
    private static final String CLIENTES_FILE = "clientes.csv";
    private Map<Integer, Cliente> clientes = new HashMap<>();

    public ClienteManager() {
        carregarClientes(CLIENTES_FILE);
    }

    public String obterCliente(int id) throws ClienteNaoEncontradoException {
        Cliente cliente = clientes.get(id);
        if (cliente == null) {
            throw new ClienteNaoEncontradoException("Cliente com ID " + id + " não encontrado.");
        }
        return cliente.getNome();
    }

    public void listarClientes() {
        for (Cliente cliente : clientes.values()) {
            System.out.println("ID: " + cliente.getId() + ", Nome: " + cliente.getNome());
        }
    }
    @Override
    public void adicionarCliente(Scanner scanner) {
        int id = -1;
        boolean idValido = false;
        while (!idValido) {
            System.out.print("Digite o ID do cliente: ");
            try {
                id = scanner.nextInt();
                idValido = true;
            } catch (InputMismatchException e) {
                System.out.println("ID inválido. Por favor, digite um número inteiro.");
                scanner.next();
            }
        }
        scanner.nextLine(); // Limpa o buffer
        System.out.print("Digite o nome do cliente: ");
        String nome = scanner.nextLine();

        Cliente cliente = new Cliente(id, nome);
        clientes.put(id, cliente);
        salvarClientes(CLIENTES_FILE);
        System.out.println("Cliente adicionado com sucesso.");
    }


    @Override
    public void removerCliente(Scanner scanner) throws ClienteNaoEncontradoException {
        System.out.print("Digite o ID do cliente para remover: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer

        if (!clientes.containsKey(id)) {
            throw new ClienteNaoEncontradoException("Cliente com ID " + id + " não encontrado.");
        }
        clientes.remove(id);
        reindexarClientes();
        salvarClientes(CLIENTES_FILE);
        System.out.println("Cliente removido com sucesso.");
    }

    private void reindexarClientes() {
        int novoId = 1;
        Map<Integer, Cliente> clientesReindexados = new HashMap<>();
        for (Cliente cliente : clientes.values()) {
            cliente = new Cliente(novoId++, cliente.getNome());
            clientesReindexados.put(cliente.getId(), cliente);
        }
        clientes = clientesReindexados;
    }

    private void carregarClientes(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] partes = line.split(",");
                int id = Integer.parseInt(partes[0].trim());
                String nome = partes[1].trim();

                Cliente cliente = new Cliente(id, nome);
                clientes.put(id, cliente);
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar o arquivo: " + fileName);
        }
    }

    private void salvarClientes(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("ID,Nome,Telefone");
            writer.newLine();
            for (Cliente cliente : clientes.values()) {
                writer.write(cliente.getId() + "," + cliente.getNome() + ",");
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo de clientes.");
        }
    }
}
