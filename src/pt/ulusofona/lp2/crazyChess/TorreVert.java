package pt.ulusofona.lp2.crazyChess;


import java.util.List;

public class TorreVert extends CrazyPiece {
    TorreVert(int idPersonagem,int idTipoPeca,int idEquipa,String alcunha, int nrRonda) {
        super(idPersonagem,idTipoPeca,idEquipa,alcunha,nrRonda);
    }

    TorreVert(int idPersonagem,int idTipoPeca,int idEquipa,String alcunha,int x, int y){
        super(idPersonagem,idTipoPeca,idEquipa,alcunha,x,y);
    }

    @Override
    public String tipoPeca() {
        return "TorreV";
    }

    @Override
    public String valorRelativo() {
        return "3";
    }

    @Override
    public String nomeImagem() {

        if(idEquipa==10){//preta
            return "torre_v_black.png";
        }else{//brancas
            return "torre_v_white.png";
        }
    }

    @Override
    public String getTipoAtual() {
        return "TorreV";
    }

    @Override
    public boolean moverPeca(int novoX, int novoY, int tamanho, List<CrazyPiece> pecasEmJogo) {
        if (this.x == novoX && this.y != novoY && dentroTabuleiro(novoX,novoY,tamanho)) {
            if (pecaNaPosicaoIdEquipa(novoX, novoY, pecasEmJogo) != idEquipa) {
                //peças no caminho
                if (percorrePecasY(novoY, pecasEmJogo)) {
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