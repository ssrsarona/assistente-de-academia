package treino;

public class Exercicios {
    private String nome;
    private int series;
    private int repeticoes;
    private String carga;

    public Exercicios(String nome, int series, int repeticoes, String carga) {
        this.nome = nome;
        this.series = series;
        this.repeticoes = repeticoes;
        this.carga = carga;
    }

    public void exibirExercicio() {
        System.out.printf("- %s: %dx%d (Peso: %s)\n", nome, series, repeticoes, carga);
    }
}