public class Gato extends Animal {
    public Gato(int id, String nome, String raca, double preco) {
        super(id, nome, raca, preco);
    }

    @Override
    public void emitirSom() {
        System.out.println("Gato faz: Miau!");
    }

    @Override
    public void imprimirDetalhes() {
        System.out.println(this);
    }
}
