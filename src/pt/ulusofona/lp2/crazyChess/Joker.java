package pt.ulusofona.lp2.crazyChess;


import java.util.List;

public class Joker extends CrazyPiece {
    private int idPecaAtual=7;


    public Joker(int idPersonagem,int idTipoPeca,int idEquipa,String alcunha, int nrRonda) {
        super(idPersonagem,idTipoPeca,idEquipa,alcunha,nrRonda);
    }

    public Joker(int idPersonagem,int idTipoPeca,int idEquipa,String alcunha,int x, int y){
        super(idPersonagem,idTipoPeca,idEquipa,alcunha,x,y);
    }

    @Override
    public String tipoPeca() {
        return "Joker";
    }

    @Override
    public String valorRelativo() {
        return "4";
    }

    @Override
    public String nomeImagem() {

        if(idEquipa==10){//preta
            return "joker_black.png";
        }else{//brancas
            return "joker_white.png";
        }
    }

    @Override
    public String getTipoAtual() {

        idDoTipoPeca();

        return tipoAtual;
    }

    @Override
    public boolean moverPeca(int novoX, int novoY, int tamanho, List<CrazyPiece> pecasEmJogo) {
        CrazyPiece pecaA;
        if (dentroTabuleiro(novoX,novoY,tamanho)) {
            if (pecaNaPosicaoIdEquipa(novoX, novoY, pecasEmJogo) != idEquipa) {

                int idJoker = idDoTipoPeca();
                Peca pecaJoker= new Peca();
                pecaA = pecaJoker.pec(this.idPecaAtual,idJoker,this.idEquipa,this.alcunha,nrRonda);
                pecaA.setTurno(nrRonda);
                pecaA.alteraX(this.x);
                pecaA.alteraY(this.y);
                pecaA.atualizaParaEstarJogo();

                if(pecaA.moverPeca(novoX,novoY,tamanho, pecasEmJogo)){
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

    public int idDoTipoPeca(){
        switch (nrRonda%6){
            case 0:
                idPecaAtual=1;
                tipoAtual = "Rainha";
                return idPecaAtual;
            case 1:
                idPecaAtual=2;
                tipoAtual = "Ponei Mágico";
                return idPecaAtual;
            case 2:
                idPecaAtual=3;
                tipoAtual = "Padre da Vila";
                return idPecaAtual;
            case 3:
                idPecaAtual=4;
                tipoAtual = "TorreH";
                return idPecaAtual;
            case 4:
                idPecaAtual=5;
                tipoAtual = "TorreV";
                return idPecaAtual;
            case 5:
                idPecaAtual=6;
                tipoAtual = "Lebre";
                return idPecaAtual;
            default:
                return 7;
        }
    }


}