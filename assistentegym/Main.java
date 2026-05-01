package assistentegym;
import java.util.*;
import usuario.Usuario;

public class Main{
    
    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, Usuario> mapUsuario = new HashMap<>();
    public static void main(String[] agrs){
     
        boolean rodando = true;
        while(rodando){
            System.out.println("---ASSISTENTE DE ACADEMIA ---");
            System.out.println(" 1- LOGIN");
            System.out.println(" 2- CADASTRO");
            System.out.println(" 3- SAIR");
            System.out.print(" opção: ");
            int op = scanner.nextInt();
        scanner.nextLine();
            switch(op){
                case 1:
                    System.out.println("----TELA DE LOGIN ----");
                    System.out.print("CPF: ");  
                    String cpfLogin = scanner.nextLine();
                    System.out.print("SENHA: ");
                    String senhaLogin = scanner.nextLine();

                    Usuario userEncontrado = mapUsuario.get(cpfLogin);

                    if(userEncontrado != null && userEncontrado.getSenha().equals(senhaLogin)){
                        System.out.println("\nLOGIN REALIZADO COM SUCESSO!");
                         System.out.println("Bem-vindo(a), " + userEncontrado.getNome() + "!");
                    }else{
                        System.out.println("ERRO: CPF ou Senha incorreto");
                        System.out.println(" ");
                    }
                    break;
                case 2:
                    System.out.print("Digite o cpf:");
                    String cpfInformado = scanner.nextLine();
                    if(mapUsuario.containsKey(cpfInformado)){
                        System.out.println("ERRO: O cpf informado ja esta ATIVO!");
                        System.out.println(" ");
                    }else{
                        Usuario novoUsuario = Usuario.cadastroUsuario(scanner, cpfInformado);
                        mapUsuario.put(novoUsuario.getCpf(), novoUsuario);
                        System.out.println("NOVO USUARIO CADASTRADO COM SUCESSO");
                        System.out.println(" ");
                    }
                    break;
                case 3: 
                    rodando = false;
                    break;
                default:

            }
        }    
    }
}