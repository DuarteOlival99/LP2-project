package pt.ulusofona.lp2.crazyChess;

import java.util.List;

public class Rei extends CrazyPiece {

    Rei(int idPersonagem, int idTipoPeca, int idEquipa, String alcunha, int nrRonda) {
        super(idPersonagem, idTipoPeca, idEquipa, alcunha,nrRonda);
    }

    Rei(int idPersonagem,int idTipoPeca,int idEquipa,String alcunha,int x, int y){
        super(idPersonagem,idTipoPeca,idEquipa,alcunha,x,y);
    }

    @Override
    public String tipoPeca() {
        return "Rei";
    }

    @Override
    public String valorRelativo() {
        return "(infinito)";
    }

    @Override
    public String nomeImagem() {

        if (idEquipa == 10) {//preta
            return "crazy_emoji_black.png";
        } else {//brancas
            return "crazy_emoji_white.png";
        }
    }

    @Override
    public String getTipoAtual() {
        return "Rei";
    }

    @Override
    public boolean moverPeca(int novoX, int novoY, int tamanho, List<CrazyPiece> pecasEmJogo) {
        if (dentroTabuleiro(novoX, novoY, tamanho)) {
            if (pecaNaPosicaoIdEquipa(novoX,novoY, pecasEmJogo) != idEquipa) {
                int qtdMoveX = Math.abs(novoX - this.getX());
                int qtdMoveY = Math.abs(novoY - this.getY());
                if (qtdMoveX == 1 && (qtdMoveY == 0)) {
                    xAnt = this.x;
                    yAnt = this.y;
                    alteraX(novoX);
                    alteraY(novoY);
                    return true;
                } else if (qtdMoveY == 1 && (qtdMoveX == 0)) {
                    xAnt = this.x;
                    yAnt = this.y;
                    alteraX(novoX);
                    alteraY(novoY);
                    return true;
                } else if (qtdMoveY == 1 && qtdMoveX == 1) {
                    xAnt = this.x;
                    yAnt = this.y;
                    alteraX(novoX);
                    alteraY(novoY);
                    return true;
                }
            }
        }
        return false;
    }
}