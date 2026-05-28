package compilador.modelos;

import java.util.ArrayList;


public class AnSintaxis {
    private ArrayList<Lexema> lexemas;
    private int tok;
    private int indice = 0;
    private StringBuilder listaErrores;

    public AnSintaxis(ArrayList<Lexema> lexemas) {
        this.lexemas = lexemas;
        this.listaErrores = new StringBuilder();
    }

    private int getNextToken() {
        if (indice >= lexemas.size()) {
            error("Fin de archivo inesperado");
            return -1; 
        }
        
        Lexema elementoActual = lexemas.get(indice);
        indice++;
        
        return elementoActual.getToken(); 
    }

    private void error(String mensaje) {
        int linea = 0;
        int col = 0;
        String charError = "Desconocido";

        // Obtenemos las coordenadas del token que causó el problema
        if (indice > 0 && indice <= lexemas.size()) {
            Lexema last = lexemas.get(indice - 1);
            linea = last.getLinea();
            col = last.getColumna();
            charError = last.getDato();
        }

        listaErrores.append("Error: Línea: ").append(linea)
                    .append(", Columna: ").append(col)
                    .append(" - ").append(mensaje).append("\n");
    }
    
    public String getErrores() {
        return listaErrores.toString();
    }

    // <Programa> -> <Bloque> "."
    public void programa() {
        try {
            tok = getNextToken();
            bloque();
            
            if (tok != ListaLexemas.PUNTO) {
                error("Faltó el punto final.");
            } else {

            }
        } catch (RuntimeException e) {
        }
    }

    // <Bloque> -> <CONSTS> <VARS> <PROCEDS> <PROPS>
    private void bloque() {
        consts();
        vars();
        proceds();
        props();
    }

    // <CONSTS> -> "const" <C_CONSTS> ";" | epsilon
    private void consts() {
        if (tok == ListaLexemas.CONST) {
            tok = getNextToken();
            c_consts();
            if (tok != ListaLexemas.PUNTO_COMA) {
                error("Se esperaba ';'");
            }
            tok = getNextToken();
        }
    }

    // <C_CONSTS> -> "id" "=" "num" <R_CONSTS>
    private void c_consts() {
        if (tok != ListaLexemas.ID) { error("Se esperaba identificador"); }
        tok = getNextToken();
        
        if (tok != ListaLexemas.IGUAL) { error("Se esperaba '='"); }
        tok = getNextToken();
        
        if (tok != ListaLexemas.NUM) { error("Se esperaba número"); }
        tok = getNextToken();
        
        r_consts();
    }

    // <R_CONSTS> -> "," "id" "=" "num" <R_CONSTS> | epsilon
    private void r_consts() {
        if (tok == ListaLexemas.COMA) {
            tok = getNextToken();
            
            if (tok != ListaLexemas.ID) { error("Se esperaba identificador"); }
            tok = getNextToken();
            
            if (tok != ListaLexemas.IGUAL) { error("Se esperaba '='"); }
            tok = getNextToken();
            
            if (tok != ListaLexemas.NUM) { error("Se esperaba número"); }
            tok = getNextToken();
            
            r_consts();
        }
    }

    // <VARS> -> "var" <C_VARS> ";" | epsilon
    private void vars() {
        if (tok == ListaLexemas.VAR) {
            tok = getNextToken();
            c_vars();
            if (tok != ListaLexemas.PUNTO_COMA) {
                error("Se esperaba ';'");
            }
            tok = getNextToken();
        }
    }

    // <C_VARS> -> "id" <R_VARS>
    private void c_vars() {
        if (tok != ListaLexemas.ID) { error("Se esperaba identificador"); }
        tok = getNextToken();
        r_vars();
    }

    // <R_VARS> -> "," "id" <R_VARS> | epsilon
    private void r_vars() {
        if (tok == ListaLexemas.COMA) {
            tok = getNextToken();
            if (tok != ListaLexemas.ID) { error("Se esperaba identificador"); }
            tok = getNextToken();
            r_vars();
        }
    }

    // <PROCEDS> -> "proced" "id" ";" <Bloque> ";" | epsilon
    private void proceds() {
        if (tok == ListaLexemas.PROCED) {
            tok = getNextToken();
            
            if (tok != ListaLexemas.ID) { error("Se esperaba identificador"); }
            tok = getNextToken();
            
            if (tok != ListaLexemas.PUNTO_COMA) { error("Se esperaba ';'"); }
            tok = getNextToken();
            
            bloque();
            
            if (tok != ListaLexemas.PUNTO_COMA) { error("Se esperaba ';'"); }
            tok = getNextToken();
            
            proceds();
        }
    }

    // <PROPS> -> <INIT> | <IDE> | <WRITE> | <READ> | <CALL> | <IF> | <WHILE> | <FOR>
    private void props() {
        // Usamos retornos anticipados para evitar "else if" o "else"
        if (tok == ListaLexemas.BEGIN) { init(); return; }
        if (tok == ListaLexemas.ID) { ide(); return; }
        if (tok == ListaLexemas.WRITE) { write(); return; }
        if (tok == ListaLexemas.READ) { read(); return; }
        if (tok == ListaLexemas.CALL) { call(); return; }
        if (tok == ListaLexemas.IF) { if_prop(); return; }
        if (tok == ListaLexemas.WHILE) { while_prop(); return; }
        if (tok == ListaLexemas.FOR) { for_prop(); return; }
        
        // Si no entró a ninguno de los de arriba, es un error
        error("Proposición Inválida");
    }

    // <INIT> -> "begin" <D_PROP> "end"
    private void init() {
        if (tok != ListaLexemas.BEGIN) { error("Se esperaba 'begin'"); }
        tok = getNextToken();
        
        d_prop();
        
        if (tok != ListaLexemas.END) { error("Se esperaba 'end'"); }
        tok = getNextToken();
    }

    // <D_PROP> -> <PROPS> <R_PROP>
    private void d_prop() {
        props();
        r_prop();
    }

    // <R_PROP> -> ";" <PROPS> <R_PROP> | epsilon
    private void r_prop() {
        if (tok == ListaLexemas.PUNTO_COMA) {
            tok = getNextToken();
            // FIRST de PROPS
            if (tok == ListaLexemas.BEGIN || tok == ListaLexemas.ID || tok == ListaLexemas.WRITE || 
                tok == ListaLexemas.READ || tok == ListaLexemas.CALL || tok == ListaLexemas.IF || 
                tok == ListaLexemas.WHILE || tok == ListaLexemas.FOR) {
                props();
                r_prop();
            }
        }
    }

    // <IDE> -> "id" "=" <EXP>
    private void ide() {
        if (tok != ListaLexemas.ID) { error("Se esperaba identificador"); }
        tok = getNextToken();
        
        if (tok != ListaLexemas.IGUAL) { error("Se esperaba '='"); }
        tok = getNextToken();
        
        exp();
    }

    // <WRITE> -> "write" <ARG>
    private void write() {
        if (tok != ListaLexemas.WRITE) { error("Se esperaba 'write'"); }
        tok = getNextToken();
        arg();
    }

    // <ARG> -> "id" | "num"
    private void arg() {
        if (tok == ListaLexemas.ID || tok == ListaLexemas.NUM) {
            tok = getNextToken();
            return; // Retorno anticipado
        }
        error("Se esperaba 'id' o 'num'");
    }

    // <READ> -> "read" "id"
    private void read() {
        if (tok != ListaLexemas.READ) { error("Se esperaba 'read'"); }
        tok = getNextToken();
        
        if (tok != ListaLexemas.ID) { error("Se esperaba identificador"); }
        tok = getNextToken();
    }

    // <CALL> -> "call" "id"
    private void call() {
        if (tok != ListaLexemas.CALL) { error("Se esperaba 'call'"); }
        tok = getNextToken();
        
        if (tok != ListaLexemas.ID) { error("Se esperaba identificador "); }
        tok = getNextToken();
    }

    // <IF> -> "if" <COND> "then" <PROPS>
    private void if_prop() {
        if (tok != ListaLexemas.IF) { error("Se esperaba 'if'"); }
        tok = getNextToken();
        
        cond();
        
        if (tok != ListaLexemas.THEN) { error("Se esperaba 'then'"); }
        tok = getNextToken();
        
        props();
    }

    // <WHILE> -> "while" <COND> "do" <PROPS>
    private void while_prop() {
        if (tok != ListaLexemas.WHILE) { error("Se esperaba 'while'"); }
        tok = getNextToken();
        
        cond();
        
        if (tok != ListaLexemas.DO) { error("Se esperaba 'do'"); }
        tok = getNextToken();
        
        props();
    }

    // <FOR> -> "for" "id" "=" <EXP> <D_FOR> <EXP> "do" <PROPS>
    private void for_prop() {
        if (tok != ListaLexemas.FOR) { error("Se esperaba 'for'"); }
        tok = getNextToken();
        
        if (tok != ListaLexemas.ID) { error("Se esperaba identificador"); }
        tok = getNextToken();
        
        if (tok != ListaLexemas.IGUAL) { error("Se esperaba '=' "); }
        tok = getNextToken();
        
        exp();
        d_for();
        exp();
        
        if (tok != ListaLexemas.DO) { error("Se esperaba 'do'"); }
        tok = getNextToken();
        
        props();
    }

    // <D_FOR> -> "to" | "down"
    private void d_for() {
        if (tok == ListaLexemas.TO || tok == ListaLexemas.DOWN) {
            tok = getNextToken();
            return;
        }
        error("Se esperaba 'to' o 'down'");
    }
    
    // <COND> -> <EXP> <OP> <EXP>
    private void cond() {
        exp();
        op();
        exp();
    }

    // <OP> -> "==" | "!=" | "<" | ">" | "<=" | ">="
    private void op() {
        if (tok == ListaLexemas.COMPARA || tok == ListaLexemas.DIF || 
            tok == ListaLexemas.MENOR_QUE || tok == ListaLexemas.MAYOR_QUE || 
            tok == ListaLexemas.MENOR_IGUAL || tok == ListaLexemas.MAYOR_IGUAL) {
            
            tok = getNextToken(); 
            return;
        }
        error("Se esperaba (==, !=, <, >, <=, >=)");
    }
    
    // <EXP> -> <TERM> <EXP_A>
    private void exp() {
        term();
        exp_a();
    }

    // <EXP_A> -> "+" <EXP> | "-" <EXP> | epsilon
    private void exp_a() {
        if (tok == ListaLexemas.MAS || tok == ListaLexemas.MENOS) {
            tok = getNextToken();
            exp();
        }
    }

    // <TERM> -> <FACT> <TERM_A>
    private void term() {
        fact();
        term_a();
    }

    // <TERM_A> -> "*" <TERM> | "/" <TERM> | epsilon
    private void term_a() {
        if (tok == ListaLexemas.MULT || tok == ListaLexemas.DIV) {
            tok = getNextToken();
            term();
        }
    }

    // <FACT> -> "(" <EXP> ")" | "id" | "num"
    private void fact() {
        if (tok == ListaLexemas.ID || tok == ListaLexemas.NUM) {
            tok = getNextToken();
            return;
        } 
        
        if (tok == ListaLexemas.ABRE_PARENT) {
            tok = getNextToken();
            exp();
            if (tok != ListaLexemas.CIERRA_PARENT) {
                error("Se esperaba ')'");
            }
            tok = getNextToken();
            return;
        } 
        
        error("Se esperaba id, num o '('");
    }
}
