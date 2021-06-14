package pt.ulusofona.lp2.crazyChess;

import java.util.ArrayList;
import java.util.List;

public class PecaMesmoMaluca extends CrazyPiece {
    List<CrazyPiece> pecasCapturadas = new ArrayList<>();

    PecaMesmoMaluca(int idPersonagem, int idTipoPeca, int idEquipa, String alcunha, int nrRonda) {
        super(idPersonagem, idTipoPeca, idEquipa, alcunha,nrRonda);
    }
    PecaMesmoMaluca(int idPersonagem, int idTipoPeca, int idEquipa, String alcunha,int x , int y) {
        super(idPersonagem, idTipoPeca, idEquipa, alcunha,x,y);
    }

    @Override
    public String tipoPeca() {
        return "Zorro";
    }

    @Override
    public String valorRelativo() {
        return "7";
    }

    @Override
    public String nomeImagem() {

        if (idEquipa == 10) {//preta
            return "PecaMesmoMaluca_black.png";
        } else {//brancas
            return "PecaMesmoMaluca_white.png";
        }

    }

    @Override
    public String getTipoAtual() {
        return "Zorro";
    }

    public int getPecasCapturadas(){

        return pecasCapturadas.size();
    }

    private  void percorrerNaDiagonal(int novoX ,int novoY, int x, List<CrazyPiece> pecasEmJogo){
        if(novoX > x && novoY > this.y){ // direita baixo
            int coluna,linha;
            for(coluna = x + 1 , linha = this.y  + 1; coluna!= novoX  && linha != novoY ; coluna++ , linha++){

                for(CrazyPiece peca : pecasEmJogo){
                    if(peca.getX() == coluna && peca.getY() == linha && peca.emJogo){
                        pecasCapturadas.add(peca);
                        peca.atualizaParaNEstarJogo();
                    }
                }
            }
        }else if(novoX > x && novoY < this.y){ // direita cima
            int coluna,linha;
            for(coluna = x  + 1, linha = this.y - 1; coluna!= novoX && linha != novoY ; coluna++ , linha--){

                for(CrazyPiece peca : pecasEmJogo){
                    if(peca.getX() == coluna && peca.getY() == linha && peca.emJogo){
                        pecasCapturadas.add(peca);
                        peca.atualizaParaNEstarJogo();

                    }
                }
            }
        }else if (novoX < x && novoY > this.y){ // esquerda baixo
            int coluna,linha;
            for(coluna = x - 1, linha = this.y  + 1; coluna!= novoX && linha != novoY ; coluna-- , linha++){

                for(CrazyPiece peca : pecasEmJogo){
                    if(peca.getX() == coluna && peca.getY() == linha && peca.emJogo){
                        pecasCapturadas.add(peca);
                        peca.atualizaParaNEstarJogo();

                    }
                }
            }
        }else{ // esquerda cima
            int coluna,linha;
            for(coluna = novoX  + 1, linha = novoY  + 1; coluna!= x  && linha != this.y ; coluna++ , linha++){

                for(CrazyPiece peca : pecasEmJogo){
                    if(peca.getX() == coluna && peca.getY() == linha && peca.emJogo){
                        pecasCapturadas.add(peca);
                        peca.atualizaParaNEstarJogo();
                    }
                }
            }
        }

    }

    private void percorrerHorizontalDireita(int novoX ,int novoY, List<CrazyPiece> pecasEmJogo){

        for (int coluna = this.x + 1; coluna <= novoX ; coluna++) {

            for (CrazyPiece peca : pecasEmJogo) {
                if (peca.getX() == coluna && peca.getY() == this.y && peca.estaEmJogo()) {
                    pecasCapturadas.add(peca);
                    peca.atualizaParaNEstarJogo();
                }
            }
        }

        for (int coluna = this.x ; coluna <= novoX ; coluna++) {//Direita
            for (CrazyPiece peca : pecasEmJogo) {
                if (peca.getX() == coluna && peca.getY() == novoY && peca.estaEmJogo()) {
                    pecasCapturadas.add(peca);
                    peca.atualizaParaNEstarJogo();
                }
            }
        }

    }

    private void percorrerHorrizontalEsquerda(int novoX, int novoY, List<CrazyPiece> pecasEmJogo){


        for (int coluna = novoX ; coluna != this.x ; coluna++) {

            for (CrazyPiece peca : pecasEmJogo) {
                if (peca.getX() == coluna && peca.getY() == this.y && peca.estaEmJogo()) {
                    pecasCapturadas.add(peca);
                    peca.atualizaParaNEstarJogo();
                }
            }

        }
        for (int coluna = novoX ; coluna <= this.x ; coluna++) {

            for (CrazyPiece peca : pecasEmJogo) {
                if (peca.getX() == coluna && peca.getY() == novoY && peca.estaEmJogo()) {
                    pecasCapturadas.add(peca);
                    peca.atualizaParaNEstarJogo();
                }
            }

        }

    }

    @Override
    public boolean moverPeca(int novoX, int novoY, int tamanho, List<CrazyPiece> pecasEmJogo) {
        if (this.x != novoX && this.y != novoY && dentroTabuleiro(novoX, novoY, tamanho)) {
            if (this.x != novoX && Math.abs(novoX - this.x) == Math.abs(novoY - this.y)) {
                pecasCapturadas.clear();
                if (this.x < novoX) {//direita
                    percorrerHorizontalDireita(novoX,novoY,pecasEmJogo);

                } else {
                    percorrerHorrizontalEsquerda(novoX,novoY,pecasEmJogo);
                }
                percorrerNaDiagonal(this.x,novoY,novoX,pecasEmJogo);
                xAnt = this.x;
                yAnt = this.y;
                alteraX(novoX);
                alteraY(novoY);
                return true;
            }
        }
        return false;
    }

    public void undoJogadaZorro(){
        if (pecasCapturadas.size() != 0){
            for (CrazyPiece peca : pecasCapturadas){
                peca.atualizaParaEstarJogo();
            }
        }
        pecasCapturadas.clear();
    }

}