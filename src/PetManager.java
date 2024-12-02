import java.io.*;
import java.util.*;

public class PetManager {
    private static final String CACHORROS_FILE = "cachorros.csv";
    private static final String GATOS_FILE = "gatos.csv";

    private final Map<Integer, Animal> cachorros = new HashMap<>();
    private final Map<Integer, Animal> gatos = new HashMap<>();
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
            System.out.println("Erro ao ler: " + fileName);
        }
    }

    private void adicionarPet(Scanner scanner, String fileName, String tipo) {
        System.out.print("Digite o nome do " + tipo + ": ");
        String nome = scanner.nextLine();
        System.out.print("Digite a raça do " + tipo + ": ");
        String raca = scanner.nextLine();
        System.out.print("Digite o preço do " + tipo + ": ");
        double preco = scanner.nextDouble();
        scanner.nextLine();

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

    public void adicionarCachorro(Scanner scanner) {
        adicionarPet(scanner, CACHORROS_FILE, "cachorro");
    }

    public void adicionarGato(Scanner scanner) {
        adicionarPet(scanner, GATOS_FILE, "gato");
    }

    public float venderCachorro(int id, float valor) {
        try {
            if (!cachorros.containsKey(id)) {
                throw new PetNaoEncontradoException("Cachorro com o ID " + id + " não encontrado.");
            }

            Animal cachorro = cachorros.get(id);
            valor += cachorro.getPreco();
            cachorros.remove(id);
            reindexarPets(cachorros);
            salvarPets(CACHORROS_FILE, petsMapToList(cachorros));

            System.out.println("Cachorro vendido com sucesso por R$" + cachorro.getPreco());
        } catch (PetNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }
        return valor;
    }

    public float venderGato(int id, float valor) {
        try {
            if (!gatos.containsKey(id)) {
                throw new PetNaoEncontradoException("Gato com o ID " + id + " não encontrado.");
            }

            Animal gato = gatos.get(id);
            valor += gato.getPreco();
            gatos.remove(id);
            reindexarPets(gatos);
            salvarPets(GATOS_FILE, petsMapToList(gatos));

            System.out.println("Gato vendido com sucesso por R$" + gato.getPreco());
        } catch (PetNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }
        return valor;
    }

    private void reindexarPets(Map<Integer, Animal> petsMap) {
        int novoId = 1;
        Map<Integer, Animal> petsReindexados = new HashMap<>();
        for (Animal pet : petsMap.values()) {
            pet.setId(novoId++);
            petsReindexados.put(pet.getId(), pet);
        }
        petsMap.clear();
        petsMap.putAll(petsReindexados);
    }

    private void carregarPets(String fileName, Map<Integer, Animal> petsMap) {
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
}
