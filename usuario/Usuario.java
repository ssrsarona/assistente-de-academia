package usuario;
import java.util.*;

public class Usuario {
    private String nome;
    private String cpf;
    private String senhaLogin;
    
    public Usuario(String nomeInit, String cpfInit, String senhaLoginInit){
        this.nome = nomeInit;
        this.cpf = cpfInit;
        this.senhaLogin = senhaLoginInit;
    } 

    public static Usuario cadastroUsuario(Scanner scanner, String  cpfInformado){

        System.out.println(" CADASTRO DE UM NOVO USUARIO");
        System.out.println("**************************************");
        System.out.print(" NOME COMPLETO: ");
        String nome = scanner.nextLine();
        System.out.print("INSIRA O CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("CRIE UMA SENHA PARA O LOGIN: ");
        String senhaLogin = scanner.nextLine();

        return new Usuario(nome, cpf, senhaLogin);

    }

    public String getNome() { return nome; }
    public String getCpf(){ return cpf; }
    public String getSenha() { return senhaLogin; }
    
    
}
