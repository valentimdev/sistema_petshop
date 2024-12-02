import java.util.Scanner;

public class PetShop {
    private PetManager petManager;
    private ClienteManager clienteManager;
    private float valor = 0;

    public PetShop() {
        this.petManager = new PetManager();
        this.clienteManager = new ClienteManager();
    }

    public void exibirMenuPrincipal() {
        Scanner scanner = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Gerenciar Pets");
            System.out.println("2. Gerenciar Clientes");
            System.out.println("0. Sair");
            System.out.println("Valor a ser pago: " + valor);
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            switch (opcao) {
                case 1 -> menuPets(scanner);
                case 2 -> menuClientes(scanner);
                case 0 -> {
                    if (valor > 0) {
                        realizarPagamento(scanner);
                    }
                    System.out.println("Encerrando o sistema...");
                }
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void menuPets(Scanner scanner) {
        int opcao;
        do {
            System.out.println("      MENU       ");
            System.out.println("1. Listar Cachorros");
            System.out.println("2. Adicionar Cachorro");
            System.out.println("3. Vender Cachorro");
            System.out.println("4. Listar Gatos");
            System.out.println("5. Adicionar Gato");
            System.out.println("6. Vender Gato");
            System.out.println("0. Voltar ao menu principal");
            System.out.println("Valor a ser pago: " + valor);
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            switch (opcao) {
                case 1 -> petManager.listarCachorros();
                case 2 -> petManager.adicionarCachorro(scanner);
                case 3 -> venderPet(scanner, "cachorro");
                case 4 -> petManager.listarGatos();
                case 5 -> petManager.adicionarGato(scanner);
                case 6 -> venderPet(scanner, "gato");
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void menuClientes(Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n=== MENU CLIENTES ===");
            System.out.println("1. Listar Clientes");
            System.out.println("2. Adicionar Cliente");
            System.out.println("3. Remover Cliente");
            System.out.println("0. Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            try {
                switch (opcao) {
                    case 1 -> clienteManager.listarClientes();
                    case 2 -> clienteManager.adicionarCliente(scanner);
                    case 3 -> clienteManager.removerCliente(scanner);
                    case 0 -> System.out.println("Voltando ao menu principal...");
                    default -> System.out.println("Opção inválida.");
                }
            } catch (ClienteNaoEncontradoException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private void venderPet(Scanner scanner, String tipoPet) {
        System.out.print("Digite o ID do " + tipoPet + " que deseja vender: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer

        if (tipoPet.equals("cachorro")) {
            valor = petManager.venderCachorro(id, valor);
        } else if (tipoPet.equals("gato")) {
            valor = petManager.venderGato(id, valor);
        }
    }

    private void realizarPagamento(Scanner scanner) {
        System.out.print("Digite o ID do cliente que está realizando a compra: ");
        int clienteId = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer

        try {
            String nomeCliente = clienteManager.obterCliente(clienteId);
            System.out.println("Cliente: " + nomeCliente);
            System.out.println("Valor a ser pago: R$" + valor);
        } catch (ClienteNaoEncontradoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
