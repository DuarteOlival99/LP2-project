package pt.ulusofona.lp2.crazyChess;

import java.util.List;

public class TorreHor extends CrazyPiece {
    TorreHor(int idPersonagem,int idTipoPeca,int idEquipa,String alcunha, int nrRonda) {
        super(idPersonagem,idTipoPeca,idEquipa,alcunha,nrRonda);
    }
    TorreHor(int idPersonagem,int idTipoPeca,int idEquipa,String alcunha,int x ,int y) {
        super(idPersonagem,idTipoPeca,idEquipa,alcunha,x,y);
    }

    @Override
    public String tipoPeca() {
        return "TorreH";
    }

    @Override
    public String valorRelativo() {
        return "3";
    }

    @Override
    public String nomeImagem() {

        if(idEquipa==10){//preta
            return "torre_h_black.png";
        }else{//brancas
            return "torre_h_white.png";
        }
    }

    @Override
    public String getTipoAtual() {
        return "TorreH";
    }

    @Override
    public boolean moverPeca(int novoX, int novoY, int tamanho, List<CrazyPiece> pecasEmJogo) {

        if (this.x != novoX && this.y == novoY && dentroTabuleiro(novoX, novoY, tamanho)) {
            if (pecaNaPosicaoIdEquipa(novoX,novoY, pecasEmJogo) != idEquipa) {
                //peças no caminho
                if (percorrePecasX(novoX, pecasEmJogo)) {
                    xAnt = this.x;
                    yAnt = this.y;
                    this.x = novoX;
                    this.y = novoY;

                    return true;
                }
            }
        }
        return false;
    }
}