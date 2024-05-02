import java.io.FileNotFoundException;
import java.io.IOException;

public class VintageAPP {
    public static void main (String[] args) {
        TextUI textUI = new TextUI();
        textUI.run();
        try {
            textUI.getModel().guardaEstado("estado.txt");
        }
        catch (FileNotFoundException e){
            System.out.println("Ficheiro não encontrado");
        }
        catch (IOException e){
            System.out.println("Ocorreu um erro na gravação dos dados    " + e.getMessage());
        }
    }
}
