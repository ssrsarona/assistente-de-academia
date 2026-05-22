package menu;

import java.util.*;
import usuario.*;
import services.*;

public class MenuMaster {
    public void exibir(Scanner scanner, Map<String, Usuario> mapUsuario){
        boolean logadoMaster = true;
        while(logadoMaster) {
            System.out.println("\n=== PAINEL DO DONO (MASTER) ===");
            System.out.println("1 - Cadastrar Novo Usuário (Personal ou Aluno)");
            System.out.println("2 - Excluir Usuário");
            System.out.println("3 - Análise de Faturamento 💰");
            System.out.println("4 - Lista de Usuarios Cadastrados");
            System.out.println("5 - Fazer Logout");
            System.out.print("Opção: ");
            int opMaster = lerNumeroSeguro(scanner);
            
            // =========================================================
            // OPÇÃO 1: CADASTRAR NOVO USUÁRIO (COM VALIDAÇÃO DO GOV)
            // =========================================================
            if (opMaster == 1) {
                String cpfInformado = ""; 
                
                // 1º Loop: Validação de CPF na internet
                while (true) {
                    System.out.print("Digite o CPF para o novo cadastro: ");
                    cpfInformado = scanner.nextLine();
                    
                    System.out.println("🔄 Consultando base de dados do Governo... Aguarde.");
                    
                    if (!ServicoCPF.verificarCpfNoGov(cpfInformado)) {
                        System.out.println("❌ ERRO: CPF não existe na Receita Federal ou está irregular!");
                        System.out.println("Tente novamente.\n");
                    } 
                    else if (mapUsuario.containsKey(cpfInformado)) {
                        System.out.println("❌ ERRO: Este CPF já está cadastrado no sistema!");
                        System.out.println("Tente novamente.\n");
                    } 
                    else {
                        System.out.println("✅ CPF validado com sucesso direto na base oficial!");
                        break; // Sai do loop do CPF
                    }
                } 
                
                // 2º Loop: Definição do Perfil (Trata o erro se digitar outro valor)
                String perfilDefinido = "";
                while (true) {
                    System.out.println("\nQual o perfil do usuário?");
                    System.out.println("1 - Personal / Professor");
                    System.out.println("2 - Aluno");
                    System.out.print("Escolha: ");
                    int escolhaPerfil = lerNumeroSeguro(scanner);
                    
                    if (escolhaPerfil == 1) {
                        perfilDefinido = "PERSONAL";
                        break;
                    } else if (escolhaPerfil == 2) {
                        perfilDefinido = "ALUNO";
                        break;
                    } else {
                        System.out.println("❌ Opção inválida! Digite 1 para Personal ou 2 para Aluno.");
                    }
                }
                
                // Salva o novo usuário de forma limpa na memória
                Usuario novoUsuario = Usuario.cadastroUsuario(scanner, cpfInformado, perfilDefinido);
                mapUsuario.put(novoUsuario.getCpf(), novoUsuario);
                System.out.println("✓ " + perfilDefinido + " CADASTRADO COM SUCESSO!");
            } 
            
            // =========================================================
            // OPÇÃO 2: EXCLUIR USUÁRIO
            // =========================================================
            else if (opMaster == 2) { 
                System.out.print("Digite o CPF do usuário que deseja remover: ");
                // Correção sutil: se lerNumeroSeguro deixou quebra de linha, limpamos aqui
                String cpfExcluir = scanner.nextLine();

                if (mapUsuario.containsKey(cpfExcluir)) {
                    Usuario user = mapUsuario.get(cpfExcluir);
                    
                    if (user.getPerfil().equals("MASTER")) {
                        System.out.println("Erro: O administrador Master não pode ser excluído.");
                    } else {
                        System.out.print("Tem certeza que deseja remover " + user.getNome() + " (" + user.getPerfil() + ")? (S/N): ");
                        String conf = scanner.nextLine();
                        if (conf.equalsIgnoreCase("S")) {
                            mapUsuario.remove(cpfExcluir);
                            System.out.println("✓ Usuário removido com sucesso.");
                        } else {
                            System.out.println("Operação cancelada.");
                        }
                    }
                } else {
                    System.out.println("Erro: CPF não encontrado.");
                }
            } 
            
            // =========================================================
            // OPÇÃO 3: ANÁLISE DE FATURAMENTO
            // =========================================================
            else if (opMaster == 3) {
                System.out.println("\n---  ANÁLISE DE FATURAMENTO ---");
                int totalAlunos = 0;
                for (Usuario u : mapUsuario.values()) {
                    if (u.getPerfil().equals("ALUNO")) totalAlunos++;
                }
                System.out.println("Total de alunos matriculados ativos: " + totalAlunos); 
                System.out.println("Faturamento Bruto Estimado: R$ " + (totalAlunos * 119.90));
                System.out.println("---------------------------------");
            } 
            
            // =========================================================
            // OPÇÃO 4: LISTAR USUÁRIOS CADASTRADOS
            // =========================================================
            else if(opMaster == 4){
                System.out.println("\n========== USÚARIOS CADASTRADOS ==========");
                System.out.println("\n      PROFESSORES / PERSONAL");

                boolean encontrouPersonal = false;
                for(Usuario u : mapUsuario.values()){
                    if(u.getPerfil().equals("PERSONAL")){
                        System.out.println("# NOME: " + u.getNome() + " | CPF: " + u.getCpf());
                        encontrouPersonal = true;
                    }
                }
                if(!encontrouPersonal){ System.out.println("Nenhum Personal cadastrado!");}

                System.out.println("\n========== ALUNOS MATRICULADOS ==========");

                boolean encontrouAluno = false;
                for(Usuario u : mapUsuario.values()){
                    if(u.getPerfil().equals("ALUNO")){
                        System.out.println("# NOME: " + u.getNome() + " | CPF: " + u.getCpf());
                        encontrouAluno = true;
                    }
                }
                if(!encontrouAluno){ System.out.println("Nenhum Aluno cadastrado!");}
            }
            
            // =========================================================
            // OPÇÃO 5: LOGOUT
            // =========================================================
            else if (opMaster == 5) {
                System.out.println("Saindo do painel Master...");
                logadoMaster = false;
            } 
            
            // TRATAMENTO DE OPÇÃO INVÁLIDA DO MENU PRINCIPAL
            else {
                System.out.println("Opção inválida!");
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
                System.out.println("Digite apenas números inteiros!");
                scanner.nextLine(); // Limpa a sujeira do buffer
                System.out.print("Tente novamente: ");
            }
        }
    } 
}