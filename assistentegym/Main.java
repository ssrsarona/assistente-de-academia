package assistentegym;
import java.util.*;

import usuario.FichaTec;
import usuario.Usuario;
import treino.FichaTreino;
import treino.Exercicios;

public class Main {
    
    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, Usuario> mapUsuario = new HashMap<>();

    
    public static void main(String[] args) {
        // COLOCANDO O DONO (MASTER) DE FÁBRICA NO SISTEMA
        mapUsuario.put("08124297100", new Usuario("Dono da Academia", "08124297100", "admin123", "MASTER"));
        
        boolean rodando = true;
        while(rodando) {
            System.out.println("\n--- ASSISTENTE DE ACADEMIA ---");
            System.out.println(" 1- LOGIN");
            System.out.println(" 2- SAIR");
            System.out.print(" Opção: ");
            int op = lerNumeroSeguro();

            switch(op) {
                case 1:
                    {
                        System.out.println("\n---- TELA DE LOGIN ----");
                        System.out.print("CPF: ");  
                        String cpfLogin = scanner.nextLine();
                        System.out.print("SENHA: ");
                        String senhaLogin = scanner.nextLine();

                        Usuario userEncontrado = mapUsuario.get(cpfLogin);

                        if(userEncontrado != null && userEncontrado.getSenha().equals(senhaLogin)) {
                            System.out.println("\nLOGIN REALIZADO COM SUCESSO!");
                            System.out.println("Bem-vindo(a), " + userEncontrado.getNome() + " [" + userEncontrado.getPerfil() + "]");
                            
                            // DIRECIONAMENTO COM BASE NO PERFIL DO CPF LOGADO
                            if (userEncontrado.getPerfil().equalsIgnoreCase("MASTER")) {
                                menuMaster();
                            } else if (userEncontrado.getPerfil().equalsIgnoreCase("PERSONAL")) {
                                menuPersonal();
                            } else {
                                menuAluno(userEncontrado); // Passa o objeto do aluno logado
                            }

                        } else {
                            System.out.println("ERRO: CPF ou Senha incorreto\n");      
                        }
                        break;
                    }
                case 2: 
                    {                 
                        System.out.println("Fechando o sistema...");
                        rodando = false;
                        break;
                    }
                default:
                    System.out.println("Opção inválida!");
            }
        }    
    }

    // ==========================================
    // MENU DO MASTER (SÓ ELE CADASTRA O PERSONAL)
    // ==========================================
    private static void menuMaster() {
        boolean logadoMaster = true;
        while(logadoMaster) {
            System.out.println("\n=== PAINEL DO DONO (MASTER) ===");
            System.out.println("1 - Cadastrar Novo Usuário (Personal ou Aluno)");
            System.out.println("2 - Excluir Usuário");
            System.out.println("3 - Análise de Faturamento 💰");
            System.out.println("4 - Fazer Logout");
            System.out.print("Opção: ");
            int opMaster = lerNumeroSeguro();
            if (opMaster == 1) {
                String cpfInformado = ""; 
                while(true){
                    System.out.print("Digite o CPF para o novo cadastro: ");
                    cpfInformado = scanner.nextLine();
                    if(cpfInformado.length() != 11){
                        System.out.println("ERRO: o CPF deve conter 11 numeros!");
                        System.out.println(" tente novamente!");
                    } 
                    else if(mapUsuario.containsKey(cpfInformado)){
                        System.out.println("ERRO: CPF ja esta cadastrado");
                        System.out.println("Tente Novamente: ");
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
                        int escolhaPerfil = lerNumeroSeguro();
                            
                        String perfilDefinido = (escolhaPerfil == 1) ? "PERSONAL" : "ALUNO";
                            
                        // Passando corretamente os 3 argumentos exigidos pelo método atualizado!
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

    // ==========================================
    // MENU DO PERSONAL (CADASTRA ALUNO E MONTA TREINO)
    // ==========================================
    private static void menuPersonal() {
        boolean logado = true;
        while(logado) {
            System.out.println("\n=== PAINEL DO PERSONAL ===");
            System.out.println("1 - Cadastrar Aluno");
            System.out.println("2 - Montar Ficha de Treino para um Aluno");
            System.out.println("3 - Cadastrar/Atualizar Ficha Técnica (Medidas) de um Aluno"); 
            System.out.println("4 - Deslogar");
            System.out.print("Opção: ");
            int op = lerNumeroSeguro();
            

            switch(op) {
                case 1:
                    {
                        System.out.print("Digite o CPF do Aluno: ");
                        String cpf = scanner.nextLine();
                        if (mapUsuario.containsKey(cpf)) {
                            System.out.println("Erro: CPF ativo.");
                        } else if (cpf.length() == 11) {
                            Usuario novoA = Usuario.cadastroUsuario(scanner, cpf, "ALUNO");
                            mapUsuario.put(novoA.getCpf(), novoA);
                            System.out.println("✓ Aluno Cadastrado com Sucesso!");
                        } else {
                            System.out.println("CPF Inválido.");
                        }
                        break;
                    }
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
                                int s = lerNumeroSeguro();
                                System.out.print("Repetições: ");
                                int r = lerNumeroSeguro();
                                System.out.print("Carga/Peso: ");
                                String c = scanner.nextLine();

                                novaFicha.adicionarExercicio(new Exercicios(nomeEx, s, r, c));
                                System.out.println("✓ Exercício adicionado ao treino!");
                            }
                            aluno.adicionarNovoTreino(novaFicha); // Vincula a ficha direto na conta daquele aluno
                            System.out.println("✓ Treino salvo com sucesso para " + aluno.getNome());
                        } else {
                            System.out.println("Aluno não encontrado.");
                        }
                        break;
                    }
                case 3:
                    {
                        System.out.print("Digite o CPF do aluno para atualizar as medidas: ");
                        String cpfAluno = scanner.nextLine();
                        Usuario aluno = mapUsuario.get(cpfAluno);

                        if (aluno != null && aluno.getPerfil().equals("ALUNO")) {
                            // 1. O Scanner lê as novas medidas e cria um novo objeto FichaTec
                            FichaTec novaFicha = FichaTec.cadastrarFicha(scanner);
                            
                            // 2. Adiciona esse objeto na lista de histórico do aluno
                            aluno.adicionarNovaFichaTec(novaFicha);
                            System.out.println("✓ Nova avaliação física adicionada ao histórico com sucesso!");
                        } else {
                            System.out.println("Erro: Aluno não encontrado.");
                        }
                        break;
                    }
                case 4:
                    {
                        logado = false; 
                        break;
                    }
                default:
                    System.out.println("Erro: opcão invalida!");  
            }
        }
    }

    // ==========================================
    // MENU DO ALUNO (SÓ CONSULTA E USA A FICHA TÉCNICA)
    // ==========================================
    private static void menuAluno(Usuario alunoLogado) {
        boolean logado = true;
        while(logado) {
            System.out.println("\n=== ÁREA DO ALUNO ===");
            System.out.println("1 - Ver Ficha de Treino");
            System.out.println("2 - Ficha Técnica (Medidas/IMC)");
            System.out.println("3 - Sair");
            System.out.print("Opção: ");
            int op = lerNumeroSeguro();
            

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
    private static int lerNumeroSeguro() {
        while (true) {
            try {
                int num = scanner.nextInt();
                scanner.nextLine(); // Limpa o buffer se der certo
                return num; // Retorna o número e sai do laço
            } catch (InputMismatchException e) {
                System.out.println("❌ Digite apenas números inteiros!");
                scanner.nextLine(); // Limpa a sujeira do buffer
                System.out.print("Tente novamente: ");
            }
        }
    }
}
