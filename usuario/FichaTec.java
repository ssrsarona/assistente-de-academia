package usuario;
import java.util.*;


public class FichaTec {
    private double peso;
    private double altura;
    private double bf;
    private String dataAvaliacao;
    

    public FichaTec(Double peso, double altura, double bf, String data){
        this.peso = peso;
        this.altura = altura;
        this.bf = bf;
        this.dataAvaliacao = data;
    }

    public double calImc(){
        if(altura == 0){return 0;}
        double alturaMetros = altura/100;
        return peso / (alturaMetros * alturaMetros);
    }
    
    public double getPeso() {  return peso;}
    public double getAltura() { return altura;}
    public double getBf() { return bf;}
    public void setPeso(double peso) { this.peso = peso; }  // se eu quiser alterar o peso

     public static FichaTec cadastrarFicha(Scanner scanner){
        System.out.println("\n--- CADASTRO DE MEDIDAS ---");
        System.out.print("Peso (kg): ");
        double p = scanner.nextDouble();
        System.out.print("Altura (m): ");
        double a = scanner.nextDouble();
        System.out.print("% Gordura: ");
        double g = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Mês/Ano da avaliação (Ex: Mai/2026): ");
        String dataAvaliacao = scanner.nextLine();
        return new FichaTec(p, a, g, dataAvaliacao);
     }

     public void exibirFicha() {
        System.out.println("--------- MINHAS MEDIDAS ---------");
        System.out.printf("Peso: %.2f kg | Altura: %.2f m\n", peso, altura);
        System.out.printf("Gordura Corporal: %.2f%%\n", bf);
        double imcExibe = calImc();
        System.out.printf("IMC Atual: %.2f\n", imcExibe);
        System.out.println("Data: " + dataAvaliacao);
        System.out.println("---------------------------------");

    }
}
