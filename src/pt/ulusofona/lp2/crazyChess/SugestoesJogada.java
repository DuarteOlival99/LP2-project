package pt.ulusofona.lp2.crazyChess;

public class SugestoesJogada implements Comparable<SugestoesJogada>{

    int x;
    int y;
    int nrPontos;
    boolean resultaCaptura;

    SugestoesJogada(int x, int y, int nrPontos, boolean resultaCaptura){
        this.x = x;
        this.y = y;
        this.nrPontos = nrPontos;
        this.resultaCaptura = resultaCaptura;
    }

    public String toString(){
        if(getNrPontos() == 1000) {
            return x + ", " + y + ", " + "(infinito)";
        }else{
            return x + ", " + y + ", " + nrPontos;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getNrPontos() {
        return nrPontos;
    }

    public boolean isResultaCaptura() {
        return resultaCaptura;
    }

    @Override
    public int compareTo(SugestoesJogada o) {

        if(resultaCaptura == o.isResultaCaptura()){
            if(nrPontos == o.getNrPontos()){
                return 0;
            }else {
                if(nrPontos > o.getNrPontos()){
                    return -1;
                }else {
                    return 1;
                }
            }
        }else{
            if(resultaCaptura){
                return -1;
            }else {
                return 1;
            }
        }
    }


}