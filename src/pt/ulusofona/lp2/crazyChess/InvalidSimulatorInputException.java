package pt.ulusofona.lp2.crazyChess;

import java.io.IOException;

class InvalidSimulatorInputException extends IOException {

    private int linhaErro;
    private int dadosEsperados;
    private int dadosObtidos;

    InvalidSimulatorInputException(int linhaErro, int dadosEsperados, int dadosObtidos) {
        this.linhaErro = linhaErro;
        this.dadosEsperados = dadosEsperados;
        this.dadosObtidos = dadosObtidos;
    }

    public int getDadosEsperados(){
        return dadosEsperados;
    }

    public int getDadosObtidos(){
        return dadosObtidos;
    }

    public int getLinhaErro(){
        return linhaErro;
    }

    public String getDescricaoProblema(){

        String DadosMais = "DADOS A MAIS (Esperava: " + getDadosEsperados() + " ; Obtive: " + getDadosObtidos() + ")";
        String DadosMenos = "DADOS A MENOS (Esperava: " + getDadosEsperados() + " ; Obtive: " + getDadosObtidos() + ")";

        if(getDadosObtidos() > getDadosEsperados()){//dados obtidos maiores que dados esperados
            return DadosMais;
        }else{// dados obtidos menor que dados esperados
            return DadosMenos;
        }

    }

}