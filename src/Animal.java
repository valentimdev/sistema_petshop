public abstract class Animal implements Imprimivel {
    protected int id;
    protected String nome;
    protected String raca;
    protected double preco;

    public Animal(int id, String nome, String raca, double preco) {
        this.id = id;
        this.nome = nome;
        this.raca = raca;
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public abstract void emitirSom();

    @Override
    public String toString() {
        return id + "," + nome + "," + raca + "," + preco;
    }
}
