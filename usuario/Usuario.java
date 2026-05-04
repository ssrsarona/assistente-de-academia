package usuario;
import java.util.*;

public class Usuario {
    private String nome;
    private String cpf;
    private String senhaLogin;
    private FichaTec fichatec;
    
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
        String senhaLogin = "";
        

        while(true){
            System.out.print("CRIE UMA SENHA PARA O LOGIN: ");
            senhaLogin = scanner.nextLine().trim();            
            if(senhaLogin.length() >= 6){
                break;
            }else{
                System.out.println(" A SENHA TEM QUE TER NO MINIMO 6 CARACTERES MISTO!");
            }
        }
        

        return new Usuario(nome, cpfInformado, senhaLogin);

    }

    public String getNome() { return nome; }
    public String getCpf(){ return cpf; }
    public String getSenha() { return senhaLogin; }
    
    public FichaTec getFichaTec() {return fichatec;}
    public void setFichaTec(FichaTec ficha){ this.fichatec = ficha;}

    public void gerenciarFichaTech(Scanner scanner){
        if (this.fichatec == null){
            this.fichatec = FichaTec.cadastrarFicha(scanner);
            System.out.println("Ficha Cadastrada");
        }else{
            this.fichatec.exibirFicha();
        }
    }
}
