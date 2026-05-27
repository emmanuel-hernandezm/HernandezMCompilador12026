package compilador.modelos;

import java.util.List;

public class AnalizadorSintactico {

    private final List<Lexema> tokens;
    private int tokenActual;
    private StringBuilder errores;
    private boolean huboError;

    public AnalizadorSintactico(List<Lexema> tokens) {
        this.tokens = tokens;
        this.tokenActual = 0;
        this.errores = new StringBuilder();
        this.huboError = false;
    }

    private Lexema obtenerToken() {
        if (tokenActual < tokens.size()) {
            return tokens.get(tokenActual);
        }
        return new Lexema("", "[EOF]", -1, tokenActual);
    }

    private void emparejar(int tokenEsperado) {
        if (obtenerToken().getToken() == tokenEsperado) {
            tokenActual++;
        } else {
            huboError = true;
            errores.append("Error sintáctico: Se esperaba el token con ID ")
                   .append(tokenEsperado)
                   .append(" pero se encontró '")
                   .append(obtenerToken().getDato())
                   .append("' en la posición ")
                   .append(obtenerToken().getPosError())
                   .append("\n");
        }
    }

    private boolean esFirstPrograma(int t) {
        return t == ListaLexemas.CONST || t == ListaLexemas.VAR || t == ListaLexemas.PROCED ||
               t == ListaLexemas.BEGIN || t == ListaLexemas.ID || t == ListaLexemas.WRITE ||
               t == ListaLexemas.READ || t == ListaLexemas.CALL || t == ListaLexemas.IF ||
               t == ListaLexemas.WHILE || t == ListaLexemas.FOR;
    }

    private boolean esFirstBloque(int t) {
        return t == ListaLexemas.CONST || t == ListaLexemas.VAR || t == ListaLexemas.PROCED ||
               t == ListaLexemas.BEGIN || t == ListaLexemas.ID || t == ListaLexemas.WRITE ||
               t == ListaLexemas.READ || t == ListaLexemas.CALL || t == ListaLexemas.IF ||
               t == ListaLexemas.WHILE || t == ListaLexemas.FOR;
    }

    private boolean esFirstSC(int t) {
        return t == ListaLexemas.CONST;
    }

    private boolean esFirstLC(int t) {
        return t == ListaLexemas.ID;
    }

    private boolean esFirstLCR(int t) {
        return t == ListaLexemas.COMA;
    }

    private boolean esFirstSV(int t) {
        return t == ListaLexemas.VAR;
    }

    private boolean esFirstLV(int t) {
        return t == ListaLexemas.ID;
    }

    private boolean esFirstLVR(int t) {
        return t == ListaLexemas.COMA;
    }

    private boolean esFirstSP(int t) {
        return t == ListaLexemas.PROCED;
    }

    private boolean esFirstProposicion(int t) {
        return t == ListaLexemas.BEGIN || t == ListaLexemas.ID || t == ListaLexemas.WRITE ||
               t == ListaLexemas.READ || t == ListaLexemas.CALL || t == ListaLexemas.IF ||
               t == ListaLexemas.WHILE || t == ListaLexemas.FOR;
    }

    private boolean esFirstLP(int t) {
        return t == ListaLexemas.BEGIN || t == ListaLexemas.ID || t == ListaLexemas.WRITE ||
               t == ListaLexemas.READ || t == ListaLexemas.CALL || t == ListaLexemas.IF ||
               t == ListaLexemas.WHILE || t == ListaLexemas.FOR;
    }

    private boolean esFirstLPR(int t) {
        return t == ListaLexemas.PUNTO_COMA;
    }

    private boolean esFirstExpresion(int t) {
        return t == ListaLexemas.ABRE_PARENT || t == ListaLexemas.ID || t == ListaLexemas.NUM;
    }

    private boolean esFirstExpresionR(int t) {
        return t == ListaLexemas.MAS || t == ListaLexemas.MENOS;
    }

    private boolean esFirstTermino(int t) {
        return t == ListaLexemas.ABRE_PARENT || t == ListaLexemas.ID || t == ListaLexemas.NUM;
    }

    private boolean esFirstTerminoR(int t) {
        return t == ListaLexemas.MULT || t == ListaLexemas.DIV;
    }

    private boolean esFirstFactor(int t) {
        return t == ListaLexemas.ABRE_PARENT || t == ListaLexemas.ID || t == ListaLexemas.NUM;
    }

    private boolean esFirstCondicion(int t) {
        return t == ListaLexemas.ABRE_PARENT || t == ListaLexemas.ID || t == ListaLexemas.NUM;
    }

    private boolean esFirstOR(int t) {
        return t == ListaLexemas.COMPARA || t == ListaLexemas.DIF ||
               t == ListaLexemas.MENOR_QUE || t == ListaLexemas.MAYOR_QUE ||
               t == ListaLexemas.MENOR_IGUAL || t == ListaLexemas.MAYOR_IGUAL;
    }

    private boolean esFirstAW(int t) {
        return t == ListaLexemas.ID || t == ListaLexemas.NUM;
    }

    private boolean esFirstDF(int t) {
        return t == ListaLexemas.TO || t == ListaLexemas.DOWN;
    }

    public String analizar() {
        if (tokens == null || tokens.isEmpty()) {
            return "Error: No hay tokens para analizar.";
        }
        int t = obtenerToken().getToken();
        if (esFirstPrograma(t)) {
            programa();
        } else {
            huboError = true;
            errores.append("Error sintáctico: Token inicial no válido en la posición ")
                   .append(obtenerToken().getPosError())
                   .append("\n");
        }
        if (tokenActual < tokens.size() && !huboError) {
            errores.append("Error sintáctico: Quedaron tokens sin analizar al final del archivo.\n");
            huboError = true;
        }
        if (huboError) {
            return errores.toString();
        }
        return "ANÁLISIS SINTÁCTICO EXITOSO";
    }

    private void programa() {
        bloque();
        emparejar(ListaLexemas.PUNTO);
    }

    private void bloque() {
        if (esFirstBloque(obtenerToken().getToken())) {
            sc();
            sv();
            sp();
            proposicion();
        } else {
            huboError = true;
            errores.append("Error sintáctico: Se esperaba inicio de bloque en la posición ")
                   .append(obtenerToken().getPosError())
                   .append("\n");
        }
    }

    private void sc() {
        if (esFirstSC(obtenerToken().getToken())) {
            emparejar(ListaLexemas.CONST);
            lc();
            emparejar(ListaLexemas.PUNTO_COMA);
        }
    }

    private void lc() {
        if (esFirstLC(obtenerToken().getToken())) {
            emparejar(ListaLexemas.ID);
            emparejar(ListaLexemas.IGUAL);
            emparejar(ListaLexemas.NUM);
            lcr();
        } else {
            huboError = true;
            errores.append("Error sintáctico: Se esperaba ID en la declaración de constantes en la posición ")
                   .append(obtenerToken().getPosError())
                   .append("\n");
        }
    }

    private void lcr() {
        if (esFirstLCR(obtenerToken().getToken())) {
            emparejar(ListaLexemas.COMA);
            emparejar(ListaLexemas.ID);
            emparejar(ListaLexemas.IGUAL);
            emparejar(ListaLexemas.NUM);
            lcr();
        }
    }

    private void sv() {
        if (esFirstSV(obtenerToken().getToken())) {
            emparejar(ListaLexemas.VAR);
            lv();
            emparejar(ListaLexemas.PUNTO_COMA);
        }
    }

    private void lv() {
        if (esFirstLV(obtenerToken().getToken())) {
            emparejar(ListaLexemas.ID);
            lvr();
        } else {
            huboError = true;
            errores.append("Error sintáctico: Se esperaba ID en la declaración de variables en la posición ")
                   .append(obtenerToken().getPosError())
                   .append("\n");
        }
    }

    private void lvr() {
        if (esFirstLVR(obtenerToken().getToken())) {
            emparejar(ListaLexemas.COMA);
            emparejar(ListaLexemas.ID);
            lvr();
        }
    }

    private void sp() {
        if (esFirstSP(obtenerToken().getToken())) {
            emparejar(ListaLexemas.PROCED);
            emparejar(ListaLexemas.ID);
            emparejar(ListaLexemas.PUNTO_COMA);
            bloque();
            emparejar(ListaLexemas.PUNTO_COMA);
            sp();
        }
    }

    private void proposicion() {
        int t = obtenerToken().getToken();
        if (esFirstProposicion(t)) {
            if (t == ListaLexemas.BEGIN) {
                emparejar(ListaLexemas.BEGIN);
                lp();
                emparejar(ListaLexemas.END);
            } else if (t == ListaLexemas.ID) {
                emparejar(ListaLexemas.ID);
                emparejar(ListaLexemas.IGUAL);
                expresion();
            } else if (t == ListaLexemas.WRITE) {
                emparejar(ListaLexemas.WRITE);
                aw();
            } else if (t == ListaLexemas.READ) {
                emparejar(ListaLexemas.READ);
                emparejar(ListaLexemas.ID);
            } else if (t == ListaLexemas.CALL) {
                emparejar(ListaLexemas.CALL);
                emparejar(ListaLexemas.ID);
            } else if (t == ListaLexemas.IF) {
                emparejar(ListaLexemas.IF);
                condicion();
                emparejar(ListaLexemas.THEN);
                proposicion();
            } else if (t == ListaLexemas.WHILE) {
                emparejar(ListaLexemas.WHILE);
                condicion();
                emparejar(ListaLexemas.DO);
                proposicion();
            } else if (t == ListaLexemas.FOR) {
                emparejar(ListaLexemas.FOR);
                emparejar(ListaLexemas.ID);
                emparejar(ListaLexemas.IGUAL);
                expresion();
                df();
                expresion();
                emparejar(ListaLexemas.DO);
                proposicion();
            }
        } else {
            huboError = true;
            errores.append("Error sintáctico: Se esperaba una proposición pero se encontró '")
                   .append(obtenerToken().getDato())
                   .append("' en la posición ")
                   .append(obtenerToken().getPosError())
                   .append("\n");
        }
    }

    private void lp() {
        if (esFirstLP(obtenerToken().getToken())) {
            proposicion();
            lpr();
        } else {
            huboError = true;
            errores.append("Error sintáctico: Se esperaba inicio de lista de proposiciones en la posición ")
                   .append(obtenerToken().getPosError())
                   .append("\n");
        }
    }

    private void lpr() {
        if (esFirstLPR(obtenerToken().getToken())) {
            emparejar(ListaLexemas.PUNTO_COMA);
            proposicion();
            lpr();
        }
    }

    private void expresion() {
        if (esFirstExpresion(obtenerToken().getToken())) {
            termino();
            expresionR();
        } else {
            huboError = true;
            errores.append("Error sintáctico: Se esperaba inicio de expresión en la posición ")
                   .append(obtenerToken().getPosError())
                   .append("\n");
        }
    }

    private void expresionR() {
        if (esFirstExpresionR(obtenerToken().getToken())) {
            int t = obtenerToken().getToken();
            emparejar(t);
            termino();
            expresionR();
        }
    }

    private void termino() {
        if (esFirstTermino(obtenerToken().getToken())) {
            factor();
            terminoR();
        } else {
            huboError = true;
            errores.append("Error sintáctico: Se esperaba un término en la posición ")
                   .append(obtenerToken().getPosError())
                   .append("\n");
        }
    }

    private void terminoR() {
        if (esFirstTerminoR(obtenerToken().getToken())) {
            int t = obtenerToken().getToken();
            emparejar(t);
            factor();
            terminoR();
        }
    }

    private void factor() {
        int t = obtenerToken().getToken();
        if (esFirstFactor(t)) {
            if (t == ListaLexemas.ABRE_PARENT) {
                emparejar(ListaLexemas.ABRE_PARENT);
                expresion();
                emparejar(ListaLexemas.CIERRA_PARENT);
            } else if (t == ListaLexemas.ID) {
                emparejar(ListaLexemas.ID);
            } else if (t == ListaLexemas.NUM) {
                emparejar(ListaLexemas.NUM);
            }
        } else {
            huboError = true;
            errores.append("Error sintáctico: Se esperaba '(', ID o NUM pero se encontró '")
                   .append(obtenerToken().getDato())
                   .append("' en la posición ")
                   .append(obtenerToken().getPosError())
                   .append("\n");
        }
    }

    private void condicion() {
        if (esFirstCondicion(obtenerToken().getToken())) {
            expresion();
            or();
            expresion();
        } else {
            huboError = true;
            errores.append("Error sintáctico: Se esperaba inicio de condición en la posición ")
                   .append(obtenerToken().getPosError())
                   .append("\n");
        }
    }

    private void or() {
        int t = obtenerToken().getToken();
        if (esFirstOR(t)) {
            emparejar(t);
        } else {
            huboError = true;
            errores.append("Error sintáctico: Se esperaba un operador relacional en la posición ")
                   .append(obtenerToken().getPosError())
                   .append("\n");
        }
    }

    private void aw() {
        int t = obtenerToken().getToken();
        if (esFirstAW(t)) {
            emparejar(t);
        } else {
            huboError = true;
            errores.append("Error sintáctico: Se esperaba ID o NUM en la posición ")
                   .append(obtenerToken().getPosError())
                   .append("\n");
        }
    }

    private void df() {
        int t = obtenerToken().getToken();
        if (esFirstDF(t)) {
            emparejar(t);
        } else {
            huboError = true;
            errores.append("Error sintáctico: Se esperaba 'to' o 'down' en la posición ")
                   .append(obtenerToken().getPosError())
                   .append("\n");
        }
    }
}