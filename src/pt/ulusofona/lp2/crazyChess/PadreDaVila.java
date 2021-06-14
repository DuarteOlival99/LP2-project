package pt.ulusofona.lp2.crazyChess;


import java.util.List;

public class PadreDaVila extends CrazyPiece {
    PadreDaVila(int idPersonagem,int idTipoPeca,int idEquipa,String alcunha, int nrRonda) {
        super(idPersonagem,idTipoPeca,idEquipa,alcunha,nrRonda);
    }

    PadreDaVila(int idPersonagem,int idTipoPeca,int idEquipa,String alcunha,int x, int y){
        super(idPersonagem,idTipoPeca,idEquipa,alcunha,x,y);
    }

    @Override
    public String tipoPeca() {
        return "Padre da Vila";
    }

    @Override
    public String valorRelativo() {
        return "3";
    }

    @Override
    public String nomeImagem() {

        if(idEquipa==10){//preta
            return "padre_vila_black.png";
        }else{//brancas
            return "padre_vila_white.png";
        }
    }

    @Override
    public String getTipoAtual() {
        return "Padre da Vila";
    }

    public boolean padreMenos2UnidadesRainha(int novoX, int novoY, List<CrazyPiece> pecasEmJogo) {

        for (CrazyPiece peca : pecasEmJogo) {

            if (peca.getIdEquipa() != this.idEquipa && peca.estaEmJogo()) {
                if ((peca.getIdTipoPeca() == 1 || (peca.getIdTipoPeca() == 7 && nrRonda % 6 == 0))) {

                    int qtdX = Math.abs(peca.x - novoX);
                    int qtdY = Math.abs(peca.y - novoY);

                    if ((qtdX < 2 && qtdY <2 ) && (qtdX + qtdY != 0)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean moverPeca(int novoX, int novoY, int tamanho, List<CrazyPiece> pecasEmJogo) {
        if ((this.x !=novoX || this.y != novoY )&& dentroTabuleiro(novoX,novoY,tamanho)){
            int qtdMoveX = (Math.abs(novoX - this.getX()));
            int qtdMoveY = Math.abs(novoY - this.getY());
            if ((qtdMoveX <= 3 && qtdMoveX > 0) && (qtdMoveY <= 3 && qtdMoveY > 0) && qtdMoveX == qtdMoveY){
                if(percorrePecaXY(novoX,novoY,pecasEmJogo) && padreMenos2UnidadesRainha(novoX, novoY,pecasEmJogo)) {
                    if (pecaNaPosicaoIdEquipa(novoX,novoY,pecasEmJogo) != idEquipa) {
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