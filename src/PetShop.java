import java.util.InputMismatchException;
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
        int opcao = 300;

        do {
            try {
                System.out.println("\n=== MENU PRINCIPAL ===");
                System.out.println("1. Gerenciar Pets");
                System.out.println("2. Gerenciar Clientes");
                System.out.println("0. Sair");
                System.out.println("Valor a ser pago: " + valor);
                System.out.print("Escolha uma opção: ");
                opcao = scanner.nextInt();
                scanner.nextLine();

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
            } catch (InputMismatchException e) {
                System.out.println("Erro: Digite um número válido.");
                scanner.nextLine();
            }
        } while (opcao != 0);
    }


    private void menuPets(Scanner scanner) {
        int opcao = -1;
        do {
            try {
                System.out.println("      MENU       ");
                System.out.println("1. Listar Cachorros");
                System.out.println("2. Adicionar Cachorro");
                System.out.println("3. Editar Cachorro");
                System.out.println("4. Vender Cachorro");
                System.out.println("5. Listar Gatos");
                System.out.println("6. Adicionar Gato");
                System.out.println("7. Editar Gato");
                System.out.println("8. Vender Gato");
                System.out.println("0. Voltar ao menu principal");
                System.out.println("Valor a ser pago: " + valor);
                System.out.print("Escolha uma opção: ");

                opcao = scanner.nextInt();
                scanner.nextLine();
                try {
                    switch (opcao) {
                        case 1 -> petManager.listarCachorros();
                        case 2 -> petManager.adicionarCachorro(scanner);
                        case 3 -> petManager.editarPet(scanner, "cachorro");
                        case 4 -> venderPet(scanner, "cachorro");
                        case 5 -> petManager.listarGatos();
                        case 6 -> petManager.adicionarGato(scanner);
                        case 7 -> petManager.editarPet(scanner, "gato");
                        case 8 -> venderPet(scanner, "gato");
                        case 0 -> System.out.println("Voltando ao menu principal...");
                        default -> System.out.println("Opção inválida.");
                    }
                } catch (PetNaoEncontradoException e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            } catch (InputMismatchException e) {
                System.out.println("Digite um número válido.");
                scanner.next();
            }
        } while (opcao != 0);
    }


    private void menuClientes(Scanner scanner) {
        int opcao = -1;
        do {
            try {
                System.out.println("\n=== MENU CLIENTES ===");
                System.out.println("1. Listar Clientes");
                System.out.println("2. Adicionar Cliente");
                System.out.println("3. Remover Cliente");
                System.out.println("0. Voltar ao menu principal");
                System.out.print("Escolha uma opção: ");
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1 -> clienteManager.listarClientes();
                    case 2 -> clienteManager.adicionarCliente(scanner);
                    case 3 -> clienteManager.removerCliente(scanner);
                    case 0 -> System.out.println("Voltando ao menu principal...");
                    default -> System.out.println("Opção inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: Digite um número válido.");
                scanner.nextLine();
            } catch (ClienteNaoEncontradoException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);
    }


    private void venderPet(Scanner scanner, String tipoPet) {
        int id = -1;
        boolean inputValido = false;
        do {
            try {
                System.out.print("Digite o ID do " + tipoPet + " que deseja vender: ");
                id = scanner.nextInt();
                scanner.nextLine();
                inputValido = true;
            } catch (InputMismatchException e) {
                System.out.println("Digite um número válido.");
                scanner.next();
            }
        } while (!inputValido);

        if (tipoPet.equals("cachorro")) {
            valor = petManager.venderCachorro(id, valor);
        } else if (tipoPet.equals("gato")) {
            valor = petManager.venderGato(id, valor);
        }
    }


    private void realizarPagamento(Scanner scanner) {
        boolean pagamentoRealizado = false;
        do {
            try {
                System.out.print("Digite o ID do cliente que está realizando a compra: ");
                int clienteId = scanner.nextInt();
                scanner.nextLine();
                String nomeCliente = clienteManager.obterCliente(clienteId);
                System.out.println("Cliente: " + nomeCliente);
                System.out.println("Valor a ser pago: R$" + valor);
                pagamentoRealizado = true;
            } catch (ClienteNaoEncontradoException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Erro: Digite um número válido.");
                scanner.nextLine();
            }
        } while (!pagamentoRealizado);
    }

}