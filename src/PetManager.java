import java.io.*;
import java.util.*;

public class PetManager implements CrudPet {
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
        System.out.println("=== Lista de Cachorros ===");
        for (Animal cachorro : cachorros.values()) {
            cachorro.imprimirDetalhes();
        }
    }

    public void listarGatos() {
        System.out.println("=== Lista de Gatos ===");
        for (Animal gato : gatos.values()) {
            gato.imprimirDetalhes();
        }
    }

    @Override
    public void adicionarPet(Scanner scanner, String fileName, String tipo) {
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
    @Override
    public void editarPet(Scanner scanner, String tipo) throws PetNaoEncontradoException {
        System.out.print("Digite o ID do " + tipo + " para editar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer

        Map<Integer, Animal> petsMap;
        String fileName;
        if (tipo.equals("cachorro")) {
            petsMap = cachorros;
            fileName = CACHORROS_FILE;
        } else {
            petsMap = gatos;
            fileName = GATOS_FILE;
        }

        if (!petsMap.containsKey(id)) {
            throw new PetNaoEncontradoException(tipo.substring(0, 1).toUpperCase() + tipo.substring(1) + " com o ID " + id + " não encontrado.");
        }

        Animal pet = petsMap.get(id);
        System.out.print("Digite o novo nome do " + tipo + ": ");
        String novoNome = scanner.nextLine();
        System.out.print("Digite a nova raça do " + tipo + ": ");
        String novaRaca = scanner.nextLine();
        System.out.print("Digite o novo preço do " + tipo + ": ");
        double novoPreco = scanner.nextDouble();
        scanner.nextLine(); // Limpa o buffer

        pet.setNome(novoNome);
        pet.setRaca(novaRaca);
        pet.setPreco(novoPreco);

        salvarPets(fileName, petsMapToList(petsMap));
        System.out.println(tipo.substring(0, 1).toUpperCase() + tipo.substring(1) + " editado com sucesso.");
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
