public class ProdutoAlimenticio extends Produto {
    public ProdutoAlimenticio(String nome, double preco) {
        super(nome, preco);
    }

    @Override
    public void calcularPagamento(double valor) {
        System.out.println("Pagamento processado para produto: " + nome + ", valor: " + valor);
    }

    @Override
    public void imprimirDetalhes() {
        System.out.println(this);
    }
}
