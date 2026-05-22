package assistentegym;
import java.util.*;
import menu.MenuAluno;
import menu.MenuMaster;
import menu.MenuPersonal;
import usuario.Usuario;

public class Main {
    
    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, Usuario> mapUsuario = new HashMap<>();

    
    public static void main(String[] args) {
        // COLOCANDO O DONO (MASTER) DE FÁBRICA NO SISTEMA
        mapUsuario.put("000", new Usuario("Dono da Academia", "000", "admin123", "MASTER"));
        
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
                               MenuMaster menuMaster = new MenuMaster();
                               menuMaster.exibir(scanner, mapUsuario);
                            } else if (userEncontrado.getPerfil().equalsIgnoreCase("PERSONAL")) {
                                MenuPersonal menuPersonal = new MenuPersonal();
                                menuPersonal.exibir(scanner, mapUsuario);
                            } else {
                               MenuAluno menuAluno = new MenuAluno();
                               menuAluno.exibir(scanner, userEncontrado);
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

    private static int lerNumeroSeguro() {
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
