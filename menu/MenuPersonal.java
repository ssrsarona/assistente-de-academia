package menu;

import java.util.*;
import treino.Exercicios;
import treino.FichaTreino;
import usuario.*;
import services.*;

public class MenuPersonal {
    public void exibir(Scanner scanner, Map<String, Usuario> mapUsuario) {
        boolean logado = true;
        while(logado) {
            System.out.println("\n=== PAINEL DO PERSONAL ===");
            System.out.println("1 - Cadastrar Aluno");
            System.out.println("2 - Montar Ficha de Treino para um Aluno");
            System.out.println("3 - Cadastrar/Atualizar Ficha Técnica (Medidas) de um Aluno"); 
            System.out.println("4 - Deslogar");
            System.out.print("Opção: ");
            int op = lerNumeroSeguro(scanner);
            
            switch(op) {
                
                // CASE 1: CADASTRAR ALUNO 
                
                case 1:
                    {
                        String cpf = "";
                        
                        // Loop para validação de CPF 
                        while (true) {
                            System.out.print("Digite o CPF do Aluno: ");
                            cpf = scanner.nextLine();
                            
                            System.out.println(" Consultando base de dados do Governo... Aguarde.");
                            
                            if (!ServicoCPF.verificarCpfNoGov(cpf)) {
                                System.out.println("ERRO: CPF não existe na Receita Federal ou está irregular!");
                                System.out.println("Tente novamente.\n");
                            } 
                            else if (mapUsuario.containsKey(cpf)) {
                                System.out.println("ERRO: Este CPF já está cadastrado no sistema!");
                                System.out.println("Tente novamente.\n");
                            } 
                            else {
                                System.out.println("CPF validado com sucesso direto na base oficial!");
                                break; // CPF aprovado, sai do loop do CPF
                            }
                        }
                        
                        // Como o metodo cadastroUsuario solicita os  dados (Nome, Senha, etc)
                        Usuario novoA = Usuario.cadastroUsuario(scanner, cpf, "ALUNO");
                        mapUsuario.put(novoA.getCpf(), novoA);
                        System.out.println("Aluno Cadastrado com Sucesso!");
                        break;
                    }
                
                
                // CASE 2: MONTAR FICHA DE TREINO
                
                case 2:
                    {
                        System.out.print("Digite o CPF do aluno para montar o treino: ");
                        String cpfAluno = scanner.nextLine();
                        Usuario aluno = mapUsuario.get(cpfAluno);

                        if (aluno != null && aluno.getPerfil().equals("ALUNO")) {
                            System.out.print("Nome do bloco de treino (Ex: Treino A - Superior): ");
                            String nomeT = scanner.nextLine();
                            FichaTreino novaFicha = new FichaTreino(nomeT);

                            boolean addExercicio = true;
                            while(addExercicio) {
                                System.out.print("Nome do Exercício (ou 'sair' para finalizar): ");
                                String nomeEx = scanner.nextLine();
                                if(nomeEx.equalsIgnoreCase("sair")) break;

                                System.out.print("Séries: ");
                                int s = lerNumeroSeguro(scanner);
                                System.out.print("Repetições: ");
                                int r = lerNumeroSeguro(scanner);
                                System.out.print("Carga/Peso: ");
                                String c = scanner.nextLine();

                                novaFicha.adicionarExercicio(new Exercicios(nomeEx, s, r, c));
                                System.out.println("Exercício adicionado ao treino!");
                            }
                            aluno.adicionarNovoTreino(novaFicha); 
                            System.out.println(" Treino saved com sucesso para " + aluno.getNome());
                        } else {
                            System.out.println("Aluno não encontrado.");
                        }
                        break;
                    }
                
                
                // CASE 3: FICHA TÉCNICA
                
                case 3:
                    {
                        System.out.print("Digite o CPF do aluno para atualizar as medidas: ");
                        String cpfAluno = scanner.nextLine();
                        Usuario aluno = mapUsuario.get(cpfAluno);

                        if (aluno != null && aluno.getPerfil().equals("ALUNO")) {
                            FichaTec novaFicha = FichaTec.cadastrarFicha(scanner);
                            aluno.adicionarNovaFichaTec(novaFicha);
                            System.out.println(" Nova avaliação física adicionada ao histórico com sucesso!");
                        } else {
                            System.out.println("Erro: Aluno não encontrado.");
                        }
                        break;
                    }
                
                
                // CASE 4: DESLOGAR
                
                case 4:
                    {
                        logado = false; 
                        break;
                    }
                default:
                    System.out.println("Erro: opção inválida!");  
            }
        }
    }
    
    // Método auxiliar estável para leitura de inteiros
    private static int lerNumeroSeguro(Scanner scanner) {
        while (true) {
            try {
                int num = scanner.nextInt();
                scanner.nextLine(); // Limpa o buffer se der certo
                return num; 
            } catch (InputMismatchException e) {
                System.out.println(" Digite apenas números inteiros!");
                scanner.nextLine(); // Limpa a sujeira do buffer
                System.out.print("Tente novamente: ");
            }
        }
    }
}