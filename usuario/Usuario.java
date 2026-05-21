package usuario;
import java.util.*;
import treino.FichaTreino; // Importa o pacote de treino

public class Usuario {
    private String nome;
    private String cpf;
    private String senhaLogin;
    private String perfil; // "MASTER", "PERSONAL" ou "ALUNO"
    private FichaTec fichatec;
    private List<FichaTreino> listaTreinos; // O treino do aluno fica salvo aqui
    
    // Construtor atualizado para receber o Perfil
    public Usuario(String nomeInit, String cpfInit, String senhaLoginInit, String perfilInit){
        this.nome = nomeInit;
        this.cpf = cpfInit;
        this.senhaLogin = senhaLoginInit;
        this.perfil = perfilInit;
        this.listaTreinos = new ArrayList<>();
    } 

    // Modificado para o cadastro geral (onde definimos o tipo do usuário)
    public static Usuario cadastroUsuario(Scanner scanner, String cpfInformado, String perfilDefinido){
        System.out.println("\n CADASTRO DE " + perfilDefinido);
        System.out.println("**************************************");
        System.out.print(" NOME COMPLETO: ");
        String nome = scanner.nextLine();
        String senhaLogin = "";
        
        while(true){
            System.out.print("CRIE UMA SENHA PARA O LOGIN: ");
            senhaLogin = scanner.nextLine().trim();            
            if(senhaLogin.length() >= 6){
                break;
            } else {
                System.out.println(" A SENHA TEM QUE TER NO MINIMO 6 CARACTERES!");
            }
        }
        return new Usuario(nome, cpfInformado, senhaLogin, perfilDefinido);
    }

    public String getNome() { return nome; }
    public String getCpf(){ return cpf; }
    public String getSenha() { return senhaLogin; }
    public String getPerfil() { return perfil; }
    
    public FichaTec getFichaTec() { return fichatec; }
    public void setFichaTec(FichaTec ficha){ this.fichatec = ficha; }

    //public FichaTreino getFichaTreino() { return fichaTreino; }
    //public void setFichaTreino(FichaTreino fichaTreino) { this.fichaTreino = fichaTreino; }
    public List<FichaTreino> getListaTreinos() { 
    return listaTreinos; 
    }

    public void adicionarNovoTreino(FichaTreino novoTreino) {
        this.listaTreinos.add(novoTreino); // Adiciona sem apagar os anteriores!
    }
    
    public void gerenciarFichaTech(Scanner scanner){
        if (this.fichatec == null){
            this.fichatec = FichaTec.cadastrarFicha(scanner);
            System.out.println("Ficha Cadastrada com Sucesso!");
        } else {
            this.fichatec.exibirFicha();
        }
    }
}