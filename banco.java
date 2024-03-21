import java.util.ArrayList;
import java.util.Scanner;

public class Banco {
    private static ArrayList<ContaCorrente> contas = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nBanco X");
            System.out.println("1. Criar nova conta");
            System.out.println("2. Exibir saldo de uma conta");
            System.out.println("3. Sacar");
            System.out.println("4. Depositar");
            System.out.println("5. Transferir");
            System.out.println("6. Sair");

            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // limpa o buffer do scanner

            switch (opcao) {
                case 1:
                    criarConta();
                    break;
                case 2:
                    exibirExtrato();
                    break;
                case 3:
                    sacar();
                    break;
                case 4:
                    depositar();
                    break;
                case 5:
                    transferir();
                    break;
                case 6:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void criarConta() {
        System.out.print("Digite o número da conta: ");
        int numero = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite o nome do dono da conta: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o CPF do dono da conta: ");
        String cpf = scanner.nextLine();

        System.out.print("Digite o saldo inicial da conta: ");
        double saldo = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Digite o limite da conta: ");
        double limite = scanner.nextDouble();
        scanner.nextLine();

        Titular titular = new Titular(nome, cpf);
        contas.add(new ContaCorrente(numero, titular, saldo, limite));
        System.out.println("Conta criada com sucesso.");
    }

    private static void exibirExtrato() {
        System.out.print("Digite o número da conta: ");
        int numero = scanner.nextInt();
        scanner.nextLine();

        ContaCorrente conta = buscarConta(numero);
        if (conta != null) {
            conta.extrato();
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private static void sacar() {
        System.out.print("Digite o número da conta: ");
        int numero = scanner.nextInt();
        scanner.nextLine();

        ContaCorrente conta = buscarConta(numero);
        if (conta != null) {
            System.out.print("Digite o valor a ser sacado: ");
            double valor = scanner.nextDouble();
            scanner.nextLine();

            if (conta.sacar(valor)) {
                System.out.println("Saque realizado com sucesso.");
            } else {
                System.out.println("Saldo insuficiente.");
            }
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private static void depositar() {
        System.out.print("Digite o número da conta: ");
        int numero = scanner.nextInt();
        scanner.nextLine();

        ContaCorrente conta = buscarConta(numero);
        if (conta != null) {
            System.out.print("Digite o valor a ser depositado: ");
            double valor = scanner.nextDouble();
            scanner.nextLine();

            conta.depositar(valor);
            System.out.println("Depósito realizado com sucesso.");
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private static void transferir() {
        System.out.print("Digite o número da conta de origem: ");
        int numeroOrigem = scanner.nextInt();
        scanner.nextLine();

        ContaCorrente contaOrigem = buscarConta(numeroOrigem);
        if (contaOrigem != null) {
            System.out.print("Digite o número da conta de destino: ");
            int numeroDestino = scanner.nextInt();
            scanner.nextLine();

            ContaCorrente contaDestino = buscarConta(numeroDestino);
            if (contaDestino != null) {
                System.out.print("Digite o valor a ser transferido: ");
                double valor = scanner.nextDouble();
                scanner.nextLine();

                if (contaOrigem.transferir(contaDestino, valor)) {
                    System.out.println("Transferência realizada com sucesso.");
                } else {
                    System.out.println("Saldo insuficiente para transferência.");
                }
            } else {
                System.out.println("Conta não encontrada.");
            }
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private static ContaCorrente buscarConta(int numero) {
        for (ContaCorrente conta : contas) {
            if (conta.getNumero() == numero) {
                return conta;
            }
        }
        return null;
    }
}

class Titular {
    private String nome;
    private String cpf;

    public Titular(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }
}

class ContaCorrente {
    private int numero;
    private Titular titular;
    private double saldo;
    private double limite;

    public ContaCorrente(int numero, Titular titular, double saldo, double limite) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = saldo;
        this.limite = limite;
    }

    public boolean sacar(double valor) {
        if (saldo + limite >= valor) {
            saldo -= valor;
            return true;
        }
        return false;
    }

    public void depositar(double valor) {
        saldo += valor;
    }

    public boolean transferir(ContaCorrente destino, double valor) {
        if (this.sacar(valor)) {
            destino.depositar(valor);
            return true;
        }
        return false;
    }

    public int getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public void extrato() {
        System.out.println("Extrato");
        System.out.println("Número da Conta: " + numero);
        System.out.println("Saldo: R$" + saldo);
        System.out.println("Titular: " + titular.getNome());
        System.out.println("CPF: " + titular.getCpf());
    }
}
