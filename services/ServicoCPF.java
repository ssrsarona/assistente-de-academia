package services;

public class ServicoCPF {

    public static boolean verificarCpfNoGov(String cpf) {
        if (cpf == null) {
            return false;
        }
        
        // Deixa apenas os números limpos
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");

        // Validação local: se tiver 11 dígitos, o CPF é aceito para o sistema rodar
        if (cpfLimpo.length() == 11) {
            return true;
        }
        
        // Se não tiver 11 dígitos (ex: esqueceram números), ele recusa
        return false;
    }
}