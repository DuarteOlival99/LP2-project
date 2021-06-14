package pt.ulusofona.lp2.crazyChess;

import java.util.List;

public class Rainha extends CrazyPiece {
    Rainha(int idPersonagem, int idTipoPeca, int idEquipa, String alcunha, int nrRonda) {
        super(idPersonagem,idTipoPeca,idEquipa,alcunha,nrRonda);
    }

    Rainha(int idPersonagem,int idTipoPeca,int idEquipa,String alcunha,int x, int y){
        super(idPersonagem,idTipoPeca,idEquipa,alcunha,x,y);
    }

    @Override
    public String tipoPeca() {
        return "Rainha";
    }

    @Override
    public String valorRelativo() {
        return "8";
    }

    @Override
    public String nomeImagem() {

        if(idEquipa==10){//preta
            return "rainha_black.png";
        }else{//brancas
            return "rainha_white.png";
        }
    }

    @Override
    public String getTipoAtual() {
        return "Rainha";
    }

    public boolean verificarRainha(int novoX, int novoY, List<CrazyPiece> pecasEmJogo){

        for(CrazyPiece peca : pecasEmJogo) {

            if (peca.getX() == novoX && peca.getY() == novoY) {
                if (peca.getIdTipoPeca() == 1 || (peca.getIdTipoPeca() == 7 && nrRonda % 6 == 0)) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean rainhaMenos2UnidadesPadre(int novoX, int novoY, List<CrazyPiece> pecasEmJogo){

        for(CrazyPiece peca: pecasEmJogo) {

            if (peca.idEquipa != this.idEquipa && peca.emJogo) {
                if ((peca.getIdTipoPeca() == 3 || (peca.getIdTipoPeca() == 7 && nrRonda % 6 == 2))) {

                    int qtdX = Math.abs(peca.x - novoX);
                    int qtdY = Math.abs(peca.y - novoY);


                    if ((qtdX < 1 && qtdY < 1) && (qtdX + qtdY != 0)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }



    @Override
    public boolean moverPeca(int novoX, int novoY, int tamanho, List<CrazyPiece> pecasEmJogo) {
        if (dentroTabuleiro(novoX,novoY,tamanho)) {
            if (pecaNaPosicaoIdEquipa(novoX, novoY, pecasEmJogo) != idEquipa) {
                int qtdMoveX = Math.abs(novoX - this.getX());
                int qtdMoveY = Math.abs(novoY - this.getY());
                if ((qtdMoveX <= 5 && qtdMoveX > 0) && (qtdMoveY == 0)) {
                    if (percorrePecasX(novoX, pecasEmJogo) && verificarRainha(novoX, novoY, pecasEmJogo) && rainhaMenos2UnidadesPadre(novoX,novoY, pecasEmJogo)) {
                        xAnt = this.x;
                        yAnt = this.y;
                        alteraX(novoX);
                        alteraY(novoY);
                        return true;
                    } else {
                        return false;
                    }

                } else if ((qtdMoveY <= 5 && qtdMoveY > 0) && (qtdMoveX == 0)) {
                    if (percorrePecasY(novoY, pecasEmJogo) && verificarRainha(novoX, novoY, pecasEmJogo) && rainhaMenos2UnidadesPadre(novoX,novoY, pecasEmJogo)) {
                        xAnt = this.x;
                        yAnt = this.y;
                        alteraX(novoX);
                        alteraY(novoY);
                        return true;
                    } else {
                        return false;
                    }
                } else if ((qtdMoveX <= 5 && qtdMoveX > 0) && (qtdMoveY <= 5 && qtdMoveY > 0) && qtdMoveX == qtdMoveY) {
                    if (percorrePecaXY(novoX, novoY, pecasEmJogo) && verificarRainha(novoX, novoY, pecasEmJogo) && rainhaMenos2UnidadesPadre(novoX,novoY, pecasEmJogo)) {
                        xAnt = this.x;
                        yAnt = this.y;
                        alteraX(novoX);
                        alteraY(novoY);
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }
}