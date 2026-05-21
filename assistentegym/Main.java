package assistentegym;
import java.util.*;
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
            int op = 0;
            try {
                op = scanner.nextInt();
                scanner.nextLine(); 
            } catch (InputMismatchException e) {
                System.out.println("\nERRO: Digite apenas números!");
                scanner.nextLine(); 
                continue;
            }

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
        boolean logado = true;
        while(logado) {
            System.out.println("\n=== PAINEL DO DONO (MASTER) ===");
            System.out.println("1 - Cadastrar Personal/Professor");
            System.out.println("2 - Deslogar");
            System.out.print("Opção: ");
            int op = scanner.nextInt();
            scanner.nextLine();

            if(op == 1) {
                System.out.print("Digite o CPF do novo Personal: ");
                String cpf = scanner.nextLine();
                if (mapUsuario.containsKey(cpf)) {
                    System.out.println("Erro: CPF já cadastrado.");
                } else {
                    Usuario novoP = Usuario.cadastroUsuario(scanner, cpf, "PERSONAL");
                    mapUsuario.put(cpf, novoP);
                    System.out.println("✓ Personal Cadastrado com Sucesso!");
                }
            } else if (op == 2) {
                logado = false;
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
            int op = scanner.nextInt();
            scanner.nextLine();

            switch(op) {
                case 1:
                    {
                        System.out.print("Digite o CPF do Aluno: ");
                        String cpf = scanner.nextLine();
                        if (mapUsuario.containsKey(cpf)) {
                            System.out.println("Erro: CPF ativo.");
                        } else if (cpf.length() == 11) {
                            Usuario novoA = Usuario.cadastroUsuario(scanner, cpf, "ALUNO");
                            mapUsuario.put(cpf, novoA);
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
                                int s = scanner.nextInt();
                                System.out.print("Repetições: ");
                                int r = scanner.nextInt();
                                scanner.nextLine();
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
                            // O Personal chama o método que roda o Scanner pedindo peso, altura, etc.
                            aluno.gerenciarFichaTech(scanner); 
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
            int op = scanner.nextInt();
            scanner.nextLine();

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
                        if (alunoLogado.getFichaTec() == null) {
                            System.out.println("Suas medidas ainda não foram cadastradas pelo Personal.");
                        } else {
                            alunoLogado.getFichaTec().exibirFicha(); // Apenas exibe as medidas, não deixa alterar!
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
}