package pt.ulusofona.lp2.crazyChess;


import java.util.List;

public class PoneiMagico extends CrazyPiece {



    PoneiMagico(int idPersonagem, int idTipoPeca, int idEquipa, String alcunha, int nrRonda) {
        super(idPersonagem, idTipoPeca, idEquipa, alcunha,nrRonda);
    }

    PoneiMagico(int idPersonagem,int idTipoPeca,int idEquipa,String alcunha,int x, int y){
        super(idPersonagem,idTipoPeca,idEquipa,alcunha,x,y);
    }

    @Override
    public String tipoPeca() {
        return "Ponei Mágico";
    }

    @Override
    public String valorRelativo() {
        return "5";
    }

    @Override
    public String nomeImagem() {

        if (idEquipa == 10) {//preta
            return "ponei_magico_black.png";
        } else {//brancas
            return "ponei_magico_white.png";
        }
    }

    @Override
    public String getTipoAtual() {
        return "Ponei Mágico";
    }

    @Override
    public boolean moverPeca(int novoX, int novoY, int tamanho, List<CrazyPiece> pecasEmJogo){
        if (dentroTabuleiro(novoX, novoY, tamanho)) {
            if (pecaNaPosicaoIdEquipa(novoX, novoY, pecasEmJogo) != idEquipa) {
                int qtdMoveX = (Math.abs(novoX - this.getX()));
                int qtdMoveY = Math.abs(novoY - this.getY());
                if ((qtdMoveX == 2) && (qtdMoveY == 2)) {

                    if (percorrePecaXRei(novoX, this.y, pecasEmJogo)) {
                        int x = this.x + 2;
                        if (percorrePecaYRei(x, novoY, pecasEmJogo)) {
                            xAnt = this.x;
                            yAnt = this.y;
                            alteraX(novoX);
                            alteraY(novoY);
                            return true;
                        }
                    }

                    if (percorrePecaYRei(this.x, novoY, pecasEmJogo)) {
                        int y = this.y + 2;
                        if (percorrePecaXRei(novoX, y, pecasEmJogo)) {
                            xAnt = this.x;
                            yAnt = this.y;
                            alteraX(novoX);
                            alteraY(novoY);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}