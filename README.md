
# Sistema PetShop

Este é um sistema de gerenciamento de um petshop desenvolvido em Java. O sistema permite a administração de clientes e animais, com funcionalidades de CRUD (Criar, Ler, Atualizar e Excluir) para ambos. O sistema também oferece a opção de adicionar e remover animais do estoque, bem como realizar a venda de animais.

## Funcionalidades

- **Gerenciamento de Clientes**
  - Adicionar um novo cliente.
  - Remover um cliente.
  - Listar todos os clientes.
  - Obter detalhes de um cliente específico.

- **Gerenciamento de Animais**
  - Adicionar um novo animal (cachorro ou gato).
  - Listar todos os animais (cachorros e gatos).
  - Editar as informações de um animal.
  - Vender um animal, removendo-o do estoque e atualizando o saldo da venda.

## Requisitos

- Java 11 ou superior
- Biblioteca padrão do Java (não há dependências externas)

## Estrutura de Arquivos

O sistema armazena as informações em arquivos CSV. Os arquivos são:
- `clientes.csv` — Armazena os dados dos clientes.
- `cachorros.csv` — Armazena os dados dos cachorros disponíveis no petshop.
- `gatos.csv` — Armazena os dados dos gatos disponíveis no petshop.

### Exemplo de Estrutura de Arquivo CSV

**clientes.csv**

```csv
ID,Nome
1,João Silva
2,Maria Oliveira
```

**cachorros.csv**

```csv
ID,Nome,Raça,Preço
1,Max,Golden Retriever,1000.0
2,Luna,Labrador,1200.0
```

**gatos.csv**

```csv
ID,Nome,Raça,Preço
1,Tom,Persa,800.0
2,Spike,Siamês,900.0
```

## Como Usar

1. **Clonando o repositório**

```bash
git clone https://github.com/valentimdev/sistema_petshop
cd sistema_petshop
```

2. **Compilando e Executando o Sistema**

Certifique-se de ter o Java instalado em seu computador e depois compile e execute o código.

```bash
javac Main.java
java Main
```

3. **Interação com o Sistema**

Ao executar o sistema, você verá um menu interativo que permitirá gerenciar clientes e animais. O sistema pedirá para você escolher as opções disponíveis, como adicionar ou listar clientes, adicionar animais, vender animais, entre outros.

## Estrutura do Código

O sistema é composto pelas seguintes classes principais:

- **ClienteManager**: Gerencia as operações relacionadas aos clientes.
- **PetManager**: Gerencia as operações relacionadas aos animais.
- **Animal** (classe base para **Cachorro** e **Gato**): Representa os animais no sistema.
- **CrudCliente** e **CrudPet**: Interfaces que definem os métodos CRUD para clientes e animais, respectivamente.
- **Exceptions**: Personalizadas para tratamento de erros, como a classe `ClienteNaoEncontradoException` e `PetNaoEncontradoException`.

## Como Contribuir

Contribuições são bem-vindas! Se você tiver sugestões ou melhorias para o sistema, fique à vontade para abrir um *pull request*. Antes de contribuir, por favor, certifique-se de que o código esteja bem testado e não quebre funcionalidades existentes.

1. Fork o projeto.
2. Crie uma branch com sua feature ou correção (`git checkout -b feature/nova-feature`).
3. Faça os commits (`git commit -am 'Adiciona nova feature'`).
4. Push para a branch (`git push origin feature/nova-feature`).
5. Abra um pull request.

