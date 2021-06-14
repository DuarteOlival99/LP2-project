package pt.ulusofona.lp2.crazyChess;

import java.util.List;

public class Estatistica {

    public int capturasParaPretas=0 , capturasParaBrancas=0;
    public int jogadasValidPretas=0 , jogadasValidBrancas=0;
    public int tentativasInvPretas=0 , tentativasInvBrancas=0;

    private int capturasParaPretasAnterior = -1 , capturasParaBrancasAnterior = -1;
    private int jogadasValidPretasAnterior = -1 , jogadasValidBrancasAnterior = -1;
    private int tentativasInvPretasAnterior = -1 , tentativasInvBrancasAnterior = -1;

    public Estatistica(){}


    public int getCapturasParaPretas(){
        return capturasParaPretas;
    }
    public int getCapturasParaBrancas(){
        return capturasParaBrancas;
    }
    public int getJogadasValidPretas(){
        return jogadasValidPretas;
    }
    public int getJogadasValidBrancas(){
        return jogadasValidBrancas;
    }
    public int getTentativasInvPretas(){
        return tentativasInvPretas;
    }
    public int getTentativasInvBrancas(){
        return tentativasInvBrancas;
    }

    public void setCapturasParaPretas(int e){
        capturasParaPretas = e;
    }
    public void setCapturasParaBrancas(int e){
        capturasParaBrancas = e;
    }
    public void setJogadasValidPretas(int e){
        jogadasValidPretas = e;
    }
    public void setJogadasValidBrancas(int e){
        jogadasValidBrancas = e;
    }
    public void setTentativasInvPretas(int e){
        tentativasInvPretas = e;
    }
    public void setTentativasInvBrancas(int e){
        tentativasInvBrancas = e;
    }


    public void adicionaCapturasParaPretas(){
        capturasParaPretas++;
        capturasParaPretasAnterior++;
    }
    public void adicionaCapturasParaBrancas(){
        capturasParaBrancas++;
        capturasParaBrancasAnterior++;
    }
    public void adicionaJogadasValidPretas(){
        jogadasValidPretas++;
        jogadasValidPretasAnterior++;
    }
    public void adicionaJogadasValidBrancas(){
        jogadasValidBrancas++;
        jogadasValidBrancasAnterior++;
    }
    public void adicionaTentativasInvPretas(){
        tentativasInvPretas++;
        tentativasInvPretasAnterior++;
    }
    public void adicionaTentativasInvBrancas(){
        tentativasInvBrancas++;
        tentativasInvBrancasAnterior++;
    }
    public void limpaVariaveis(){
        capturasParaPretas = 0;
        capturasParaBrancas = 0;
        jogadasValidPretas = 0;
        jogadasValidBrancas = 0;
        tentativasInvPretas = 0;
        tentativasInvBrancas = 0;
        capturasParaPretasAnterior = -1;
        capturasParaBrancasAnterior = -1;
        jogadasValidPretasAnterior = -1;
        jogadasValidBrancasAnterior = -1;
        tentativasInvPretasAnterior = -1;
        tentativasInvBrancasAnterior = -1;
    }

    public void undoEstatistica() {
        if (capturasParaPretasAnterior == -1){
            capturasParaPretas = 0;
        }else{
            capturasParaPretas = capturasParaPretasAnterior;
        }
        if (capturasParaBrancasAnterior == -1){
            capturasParaBrancas = 0;
        }else {
            capturasParaBrancas = capturasParaBrancasAnterior;
        }
        if(jogadasValidPretasAnterior == -1){
            jogadasValidPretas = 0;
        }else{
            jogadasValidPretas = jogadasValidPretasAnterior;
        }
        if( jogadasValidBrancasAnterior == -1){

        }else{
            jogadasValidBrancas = jogadasValidBrancasAnterior;
        }
        if(tentativasInvPretasAnterior == -1){
            tentativasInvPretas = 0;
        }else{
            tentativasInvPretas = tentativasInvPretasAnterior;
        }
        if(tentativasInvBrancasAnterior == -1){
            tentativasInvBrancas = 0;
        }else{



            tentativasInvBrancas = tentativasInvBrancasAnterior;
        }

    }
}