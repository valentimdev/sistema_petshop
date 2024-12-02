public class Cliente implements Pagavel {
    private int id;
    private String nome;

    public Cliente(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public void calcularPagamento(double valor) {
        System.out.println("Cliente " + nome + " deve pagar R$" + valor);
    }
}
