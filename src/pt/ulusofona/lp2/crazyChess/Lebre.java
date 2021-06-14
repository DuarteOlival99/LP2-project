package pt.ulusofona.lp2.crazyChess;

import java.util.List;

public class Lebre extends CrazyPiece {

    Lebre(int idPersonagem,int idTipoPeca,int idEquipa,String alcunha, int nrRonda) {
        super(idPersonagem,idTipoPeca,idEquipa,alcunha,nrRonda);
    }

    Lebre(int idPersonagem,int idTipoPeca,int idEquipa,String alcunha,int x, int y){
        super(idPersonagem,idTipoPeca,idEquipa,alcunha,x,y);
    }
    @Override
    public String tipoPeca() {
        return "Lebre";
    }

    @Override
    public String valorRelativo() {
        return "2";
    }

    @Override
    public String nomeImagem() {

        if(idEquipa==10){//preta
            return "lebre_black.png";
        }else{//brancas
            return "lebre_white.png";
        }
    }

    @Override
    public String getTipoAtual() {
        return "Lebre";
    }

    @Override
    public boolean moverPeca(int novoX, int novoY, int tamanho, List<CrazyPiece> pecasEmJogo) {
        if (nrRonda % 2 == 0) {
            int qtdMoveX = Math.abs(novoX - this.getX());
            int qtdMoveY = Math.abs(novoY - this.getY());
            if (this.x != novoX && this.y != novoY && dentroTabuleiro(novoX, novoY, tamanho)) {

                if (qtdMoveY == 1 && qtdMoveX == 1 ) {
                    if (pecaNaPosicaoIdEquipa(novoX,novoY, pecasEmJogo) != idEquipa) {
                        xAnt = this.x;
                        yAnt = this.y;
                        alteraX(novoX);
                        alteraY(novoY);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}