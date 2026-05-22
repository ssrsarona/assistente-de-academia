package menu;
import java.util.*;

import treino.FichaTreino;
import usuario.*;

public class MenuAluno {
    public void exibir(Scanner scanner, Usuario alunoLogado){
        boolean logado = true;
        while(logado) {
            System.out.println("\n=== ÁREA DO ALUNO ===");
            System.out.println("1 - Ver Ficha de Treino");
            System.out.println("2 - Ficha Técnica (Medidas/IMC)");
            System.out.println("3 - Sair");
            System.out.print("Opção: ");
            int op = lerNumeroSeguro(scanner);
            

            switch(op) {
                case 1:
                    {
                        if (alunoLogado.getListaTreinos().isEmpty()) { // Checa se a lista está vazia
                            System.out.println("Você ainda não tem nenhum treino montado pelo Personal.");
                        } else {
                            System.out.println("\n=== SEUS TREINOS CADASTRADOS ===");
                             // Esse loop vai passar por cada treino que o Personal adicionou
                         for (FichaTreino treino : alunoLogado.getListaTreinos()) {
                                 treino.exibirFichaCompleta();
                            }           
                        }
                        break;
                    }
                case 2:
                    {
                        if (alunoLogado.getHistoricoFichas().isEmpty()) {
                            System.out.println("Suas medidas ainda não foram cadastradas pelo Personal.");
                     } else {
                            System.out.println("\n========= SEU HISTÓRICO DE EVOLUÇÃO =========");
                            int avaliacaoNum = 1;
                            
                            // O loop passa por cada registro de peso/altura que o Personal salvou
                            for (FichaTec ficha : alunoLogado.getHistoricoFichas()) {
                                System.out.println("\n[ " + avaliacaoNum + "ª Avaliação Física ]");
                                ficha.exibirFicha(); // Chama o método print que você já criou na FichaTec
                                avaliacaoNum++;
                            }
                            System.out.println("=============================================");
                    }
                    break;
                    }
                case 3:
                    {
                        logado = false;
                        break;
                    }
                default:
                    System.out.println("Opção incorreta!");
            }
        }
    }
    private static int lerNumeroSeguro(Scanner scanner) {
        while (true) {
            try {
                int num = scanner.nextInt();
                scanner.nextLine(); // Limpa o buffer se der certo
                return num; // Retorna o número e sai do laço
            } catch (InputMismatchException e) {
                System.out.println(" Digite apenas números inteiros!");
                scanner.nextLine(); // Limpa a sujeira do buffer
                System.out.print("Tente novamente: ");
            }
        }
    }
}

