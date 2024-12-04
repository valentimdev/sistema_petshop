public class Cachorro extends Animal {
    public Cachorro(int id, String nome, String raca, double preco) {
        super(id, nome, raca, preco);
    }

    @Override
    public void emitirSom() {
        System.out.println("au au");
    }

    @Override
    public void imprimirDetalhes() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Cachorro{" +
                "preco=" + preco +
                ", raca='" + raca + '\'' +
                ", nome='" + nome + '\'' +
                ", id=" + id +
                '}';
    }
}
