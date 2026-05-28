package compilador.modelos;

import java.util.ArrayList;
import compilador.vistas.*;


public class AnSintaxis {
    private ArrayList<Lexema> lexemas;
    private VistaCompilador vista;
    private int tok;
    private int indice = 0;

    public AnSintaxis(ArrayList<Lexema> lexemas, VistaCompilador vista) {
        this.lexemas = lexemas;
        this.vista = vista;
    }

    private int getNextToken() {
        if (indice >= lexemas.size()) {
            error("Fin de archivo inesperado")
            return -1; 
        }
        
        Lexema elementoActual = lexemas.get(indice);
        indice++;
        
        return elementoActual.getToken(); 
    }

    private void error(String mensaje) {
        String posIn = "";
        
        if (indice > 0 && indice <= lexemas.size()) {
            Lexema last = lexemas.get(indice - 1);
        }
        
    }

    // <Programa> -> <Bloque> "."
    public void programa() {
        tok = getNextToken();
        bloque();
        if (tok != ListaLexemas.PUNTO) {
            error("Faltó el punto final.");
        } else {
            System.out.println("Análisis Sintáctico Correcto");
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
    // FIRST(<CONSTS>) = { "const" } + epsilon
    private void consts() {
        if (tok == ListaLexemas.CONST) {
            tok = getNextToken();
            c_consts();
            if (tok == ListaLexemas.PUNTO_COMA) {
                tok = getNextToken();
            } else {
                error("Se esperaba ';'");
            }
        }
    }

    // <C_CONSTS> -> "id" "=" "num" <R_CONSTS>
    private void c_consts() {
        if (tok == ListaLexemas.ID) {
            tok = getNextToken();
            if (tok == ListaLexemas.IGUAL) {
                tok = getNextToken();
                if (tok == ListaLexemas.NUM) {
                    tok = getNextToken();
                    r_consts();
                } else error("Se esperaba número");
            } else error("Se esperaba '='");
        } else error("Se esperaba identificador");
    }

    // <R_CONSTS> -> "," "id" "=" "num" <R_CONSTS> | epsilon
    private void r_consts() {
        if (tok == ListaLexemas.COMA) {
            tok = getNextToken();
            if (tok == ListaLexemas.ID) {
                tok = getNextToken();
                if (tok == ListaLexemas.IGUAL) {
                    tok = getNextToken();
                    if (tok == ListaLexemas.NUM) {
                        tok = getNextToken();
                        r_consts();
                    } else error("Se esperaba número");
                } else error("Se esperaba '='");
            } else error("Se esperaba identificador");
        }
    }

    // <VARS> -> "var" <C_VARS> ";" | epsilon
    private void vars() {
        if (tok == ListaLexemas.VAR) {
            tok = getNextToken();
            c_vars();
            if (tok == ListaLexemas.PUNTO_COMA) {
                tok = getNextToken();
            } else {
                error("Se esperaba ';'");
            }
        }
    }

    // <C_VARS> -> "id" <R_VARS>
    private void c_vars() {
        if (tok == ListaLexemas.ID) {
            tok = getNextToken();
            r_vars();
        } else {
            error("Se esperaba identificador en declaración de variables");
        }
    }

    // <R_VARS> -> "," "id" <R_VARS> | epsilon
    private void r_vars() {
        if (tok == ListaLexemas.COMA) {
            tok = getNextToken();
            if (tok == ListaLexemas.ID) {
                tok = getNextToken();
                r_vars();
            } else {
                error("Se esperaba identificador después de la coma");
            }
        }
    }

    // <PROCEDS> -> "proced" "id" ";" <Bloque> ";" | epsilon
    private void proceds() {
        if (tok == ListaLexemas.PROCED) {
            tok = getNextToken();
            if (tok == ListaLexemas.ID) {
                tok = getNextToken();
                if (tok == ListaLexemas.PUNTO_COMA) {
                    tok = getNextToken();
                    bloque();
                    if (tok == ListaLexemas.PUNTO_COMA) {
                        tok = getNextToken();
                        proceds();
                    } else error("Se esperaba ';' al final del proced");
                } else error("Se esperaba ';'");
            } else error("Se esperaba identificador del procedimiento");
        }
    }

    // <PROPS> -> <INIT> | <IDE> | <WRITE> | <READ> | <CALL> | <IF> | <WHILE> | <FOR>
    private void props() {

        if (tok == ListaLexemas.BEGIN) {
            init();
        } else if (tok == ListaLexemas.ID) {
            ide();
        } else if (tok == ListaLexemas.WRITE) {
            write();
        } else if (tok == ListaLexemas.READ) {
            read();
        } else if (tok == ListaLexemas.CALL) {
            call();
        } else if (tok == ListaLexemas.IF) {
            if_prop();
        } else if (tok == ListaLexemas.WHILE) {
            while_prop();
        } else if (tok == ListaLexemas.FOR) {
            for_prop();
        } else {
            error("Se esperaba una proposición válida (FIRST de PROPS)");
        }
    }

    // <INIT> -> "begin" <D_PROP> "end"
    private void init() {
        if (tok == ListaLexemas.BEGIN) {
            tok = getNextToken();
            d_prop();
            if (tok == ListaLexemas.END) {
                tok = getNextToken();
            } else error("Se esperaba 'end'");
        }
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
        if (tok == ListaLexemas.ID) {
            tok = getNextToken();
            if (tok == ListaLexemas.IGUAL) {
                tok = getNextToken();
                exp();
            } else error("Se esperaba '=' en asignación");
        }
    }

// <WRITE> -> "write" <ARG>
    private void write() {
        if (tok == ListaLexemas.WRITE) {
            tok = getNextToken();
            arg();
        } else error("Se esperaba 'write'");
    }

    // <ARG> -> "id" | "num"
    private void arg() {
        if (tok == ListaLexemas.ID || tok == ListaLexemas.NUM) {
            tok = getNextToken();
        } else {
            error("Se esperaba 'id' o 'num' como argumento de write");
        }
    }

    // <READ> -> "read" "id"
    private void read() {
        if (tok == ListaLexemas.READ) {
            tok = getNextToken();
            if (tok == ListaLexemas.ID) {
                tok = getNextToken();
            } else error("Se esperaba identificador para el read");
        } else error("Se esperaba 'read'");
    }

    // <CALL> -> "call" "id"
    private void call() {
        if (tok == ListaLexemas.CALL) {
            tok = getNextToken();
            if (tok == ListaLexemas.ID) {
                tok = getNextToken();
            } else error("Se esperaba identificador para el call");
        } else error("Se esperaba 'call'");
    }

    // <IF> -> "if" <COND> "then" <PROPS>
    private void if_prop() {
        if (tok == ListaLexemas.IF) {
            tok = getNextToken();
            cond();
            if (tok == ListaLexemas.THEN) {
                tok = getNextToken();
                props();
            } else error("Se esperaba 'then' en la estructura if");
        } else error("Se esperaba 'if'");
    }

    // <WHILE> -> "while" <COND> "do" <PROPS>
    private void while_prop() {
        if (tok == ListaLexemas.WHILE) {
            tok = getNextToken();
            cond();
            if (tok == ListaLexemas.DO) {
                tok = getNextToken();
                props();
            } else error("Se esperaba 'do' en la estructura while");
        } else error("Se esperaba 'while'");
    }

    // <FOR> -> "for" "id" "=" <EXP> <D_FOR> <EXP> "do" <PROPS>
    private void for_prop() {
        if (tok == ListaLexemas.FOR) {
            tok = getNextToken();
            if (tok == ListaLexemas.ID) {
                tok = getNextToken();
                if (tok == ListaLexemas.IGUAL) {
                    tok = getNextToken();
                    exp();
                    d_for();
                    exp();
                    if (tok == ListaLexemas.DO) {
                        tok = getNextToken();
                        props();
                    } else error("Se esperaba 'do' en la estructura for");
                } else error("Se esperaba '=' en la estructura for");
            } else error("Se esperaba identificador en la estructura for");
        } else error("Se esperaba 'for'");
    }

    // <D_FOR> -> "to" | "down"
    private void d_for() {
        if (tok == ListaLexemas.TO || tok == ListaLexemas.DOWN) {
            tok = getNextToken();
        } else error("Se esperaba 'to' o 'down'");
    }
    
    // <COND> -> <EXP> <OP> <EXP>
    private void cond() {
        exp();
        op();
        exp();
    }

    // <OP> -> "==" | "!=" | "<" | ">" | "<=" | ">="
    private void op() {
        if (tok == ListaLexemas.COMPARA || 
            tok == ListaLexemas.DIF || 
            tok == ListaLexemas.MENOR_QUE || 
            tok == ListaLexemas.MAYOR_QUE || 
            tok == ListaLexemas.MENOR_IGUAL || 
            tok == ListaLexemas.MAYOR_IGUAL) {
            
            tok = getNextToken(); 
        } else {
            error("Se esperaba un operador relacional (==, !=, <, >, <=, >=)");
        }
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
        // epsilon
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
        // epsilon
    }

    // <FACT> -> "(" <EXP> ")" | "id" | "num"
    private void fact() {
        if (tok == ListaLexemas.ID || tok == ListaLexemas.NUM) {
            tok = getNextToken();
        } else if (tok == ListaLexemas.ABRE_PARENT) {
            tok = getNextToken();
            exp();
            if (tok == ListaLexemas.CIERRA_PARENT) {
                tok = getNextToken();
            } else error("Se esperaba ')'");
        } else {
            error("Se esperaba id, num o '('");
        }
    }
}
