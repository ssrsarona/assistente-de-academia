package menu;
import java.util.*;
import usuario.*;

public class MenuMaster {
    public void exibir(Scanner scanner, Map<String, Usuario> mapUsuario){
        boolean logadoMaster = true;
        while(logadoMaster) {
            System.out.println("\n=== PAINEL DO DONO (MASTER) ===");
            System.out.println("1 - Cadastrar Novo Usuário (Personal ou Aluno)");
            System.out.println("2 - Excluir Usuário");
            System.out.println("3 - Análise de Faturamento 💰");
            System.out.println("4 - Fazer Logout");
            System.out.print("Opção: ");
            int opMaster = lerNumeroSeguro(scanner);
            if (opMaster == 1) {
                String cpfInformado = ""; 
                while(true){//while para verificar se cpf ja foi cadastrado
                    System.out.print("Digite o CPF para o novo cadastro: ");
                    cpfInformado = scanner.nextLine();
                    if(cpfInformado.length() != 11){// se o cpf nao tiver 11 numeros ele da erro e volta dnv ate digitar o numero correto
                        System.out.println("ERRO: o CPF deve conter 11 numeros!");
                        System.out.println(" tente novamente!");
                    } 
                    else if(mapUsuario.containsKey(cpfInformado)){// aqui confirma se o cpf ja existe, se sim volta para o inicio se nao sai do loop
                        System.out.println("ERRO: CPF ja esta cadastrado");
                        System.out.println("Tente Novamente! ");
                    }
                    else{
                        break;
                    }
   
                }
            
                if(mapUsuario.containsKey(cpfInformado)){
                        
                     System.out.println("ERRO: Este CPF já está cadastrado!");
                        System.out.println("Digite novamente:");
                            
                       
                } else {
                    if(cpfInformado.length() == 11 || cpfInformado.equals("08124297100")){
                            
                        // Menu interno para o Master escolher o tipo de conta
                        System.out.println("Qual o perfil do usuário?");
                        System.out.println("1 - Personal / Professor");
                        System.out.println("2 - Aluno");
                        System.out.print("Escolha: ");
                        int escolhaPerfil = lerNumeroSeguro(scanner);
                            
                        String perfilDefinido = (escolhaPerfil == 1) ? "PERSONAL" : "ALUNO";
                            
                        //ERRO AQUI!!! CASO DIGITE OUTRO VALOR ELE NAO VOLTA
                        Usuario novoUsuario = Usuario.cadastroUsuario(scanner, cpfInformado, perfilDefinido);
                        mapUsuario.put(novoUsuario.getCpf(), novoUsuario);
                        System.out.println("✓ " + perfilDefinido + " CADASTRADO COM SUCESSO!");
                    } else {
                        System.out.println("ERRO: CPF inválido. Deve conter 11 dígitos.");
                    }
                }
                
             } 
            
            else if (opMaster == 2) { 
                System.out.print("Digite o CPF do usuário que deseja remover: ");
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
            
            else if (opMaster == 3) {
                System.out.println("\n--- 📊 ANÁLISE DE FATURAMENTO ---");
                int totalAlunos = 0;
                for (Usuario u : mapUsuario.values()) {
                    if (u.getPerfil().equals("ALUNO")) totalAlunos++;
                }
                System.out.println("Total de alunos matriculados ativos: " + totalAlunos); 
                System.out.println("Faturamento Bruto Estimado: R$ " + (totalAlunos * 119.90));
                System.out.println("---------------------------------");
            } 
            
            else if (opMaster == 4) {
                System.out.println("Saindo do painel Master...");
                logadoMaster = false;
            } else {
                System.out.println("Opção inválida!");
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
                System.out.println("Digite apenas números inteiros!");
                scanner.nextLine(); // Limpa a sujeira do buffer
                System.out.print("Tente novamente: ");
            }
        }
    } 
}
    

