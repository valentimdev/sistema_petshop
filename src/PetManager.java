import java.io.*;
import java.util.*;

public class PetManager {
    private static final String CACHORROS_FILE = "cachorros.csv";
    private static final String GATOS_FILE = "gatos.csv";

    private Map<Integer, Animal> cachorros = new HashMap<>();
    private Map<Integer, Animal> gatos = new HashMap<>();
    private int petIdCounter = 1;

    public PetManager() {
        carregarPets(CACHORROS_FILE, cachorros);
        carregarPets(GATOS_FILE, gatos);
    }

    public void listarCachorros() {
        listarPets(CACHORROS_FILE, "=== Lista de Cachorros ===");
    }

    public void listarGatos() {
        listarPets(GATOS_FILE, "=== Lista de Gatos ===");
    }

    private void listarPets(String fileName, String titulo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            System.out.println(titulo);
            while ((line = reader.readLine()) != null) {
                String[] partes = line.split(",");
                System.out.println("ID: " + partes[0] + ", Nome: " + partes[1] + ", Raça: " + partes[2] + ", Preço: " + partes[3]);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + fileName);
        }
    }

    public void adicionarCachorro(Scanner scanner) {
        adicionarPet(scanner, CACHORROS_FILE, "cachorro");
    }

    public void adicionarGato(Scanner scanner) {
        adicionarPet(scanner, GATOS_FILE, "gato");
    }

    private void adicionarPet(Scanner scanner, String fileName, String tipo) {
        System.out.print("Digite o nome do " + tipo + ": ");
        String nome = scanner.nextLine();
        System.out.print("Digite a raça do " + tipo + ": ");
        String raca = scanner.nextLine();
        System.out.print("Digite o preço do " + tipo + ": ");
        double preco = scanner.nextDouble();
        scanner.nextLine(); // Limpa o buffer

        int id = petIdCounter++;
        Animal pet;
        if (tipo.equals("cachorro")) {
            pet = new Cachorro(id, nome, raca, preco);
            cachorros.put(id, pet);
        } else {
            pet = new Gato(id, nome, raca, preco);
            gatos.put(id, pet);
        }

        salvarPets(fileName, petsMapToList(fileName.equals(CACHORROS_FILE) ? cachorros : gatos));
        System.out.println(tipo + " adicionado com sucesso.");
    }

    public void removerCachorro(Scanner scanner) throws PetNaoEncontradoException {
        removerPet(scanner, CACHORROS_FILE, "cachorro", cachorros);
    }

    public void removerGato(Scanner scanner) throws PetNaoEncontradoException {
        removerPet(scanner, GATOS_FILE, "gato", gatos);
    }

    private void removerPet(Scanner scanner, String fileName, String tipo, Map<Integer, Animal> petsMap) throws PetNaoEncontradoException {
        System.out.print("Digite o ID do " + tipo + " para remover: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer

        if (!petsMap.containsKey(id)) {
            throw new PetNaoEncontradoException("Um " + tipo + " com o ID " + id + " não foi encontrado.");
        }

        petsMap.remove(id);
        reindexIds(petsMap);

        salvarPets(fileName, petsMapToList(fileName.equals(CACHORROS_FILE) ? cachorros : gatos));
        System.out.println(tipo + " removido com sucesso.");
    }

    private void carregarPets(String fileName, Map<Integer, Animal> petsMap) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
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
                String raca = partes[2].trim();
                double preco = Double.parseDouble(partes[3].trim());

                Animal pet;
                if (fileName.equals(CACHORROS_FILE)) {
                    pet = new Cachorro(id, nome, raca, preco);
                    cachorros.put(id, pet);
                } else {
                    pet = new Gato(id, nome, raca, preco);
                    gatos.put(id, pet);
                }

                if (id >= petIdCounter) {
                    petIdCounter = id + 1;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar o arquivo: " + fileName);
        }
    }

    private void salvarPets(String fileName, List<String> petsData) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("ID,Nome,Raça,Preço");
            writer.newLine();
            for (String petData : petsData) {
                writer.write(petData);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo de pets.");
        }
    }

    private List<String> petsMapToList(Map<Integer, Animal> petsMap) {
        List<String> petsData = new ArrayList<>();
        for (Animal pet : petsMap.values()) {
            petsData.add(pet.toString());
        }
        return petsData;
    }

    private void reindexIds(Map<Integer, Animal> petsMap) {
        int newId = 1;
        for (Map.Entry<Integer, Animal> entry : new HashMap<>(petsMap).entrySet()) {
            Animal pet = entry.getValue();
            petsMap.remove(entry.getKey());
            pet.setId(newId++);
            petsMap.put(pet.getId(), pet);
        }
        petIdCounter = newId;
    }
}
