package compilador.modelos;

import java.util.ArrayList;

public class AnalisisSintactico {
    
    private ArrayList<String> lexemas;
    private int token;

    public AnalisisSintactico(ArrayList<String> lexemas) {
        this.lexemas = lexemas;
    }
    
    private int getNextToken() {
        return 0;
    }
    
    public void programa() {
        token = getNextToken();
        
        if (token != 276) {
            
        }
    }
    
}
