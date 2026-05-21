package treino;
import java.util.*;

public class FichaTreino {
    private String nomeDoTreino; // Ex: "Treino A - Peito", "Treino B"
    private List<Exercicios> listaExercicios;

    public FichaTreino(String nomeDoTreino) {
        this.nomeDoTreino = nomeDoTreino;
        this.listaExercicios = new ArrayList<>();
    }

    public void adicionarExercicio(Exercicios ex) {
        this.listaExercicios.add(ex);
    }

    public String getNomeDoTreino() { return nomeDoTreino; }

    public void exibirFichaCompleta() {
        System.out.println("\n===== " + nomeDoTreino.toUpperCase() + " =====");
        if (listaExercicios.isEmpty()) {
            System.out.println("Nenhum exercício montado para este treino ainda.");
        } else {
            for (Exercicios ex : listaExercicios) {
                ex.exibirExercicio();
            }
        }
        System.out.println("===============================");
    }
}