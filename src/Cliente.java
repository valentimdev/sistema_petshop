public class Cliente extends Pessoa{
    private int id;

    public Cliente(int id, String nome) {
        super(nome);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
    public void imprimirDetalhes(){
        System.out.println("Nome do cliente: " + getNome()+"\n ID: " + getId());
    }
    @Override
    public String toString() {
        return "Cliente" + "id=" + id + ", nome=" + nome;
    }
}