package pt.ulusofona.lp2.crazyChess;


import java.util.Comparator;
import java.util.List;


import static java.util.stream.Collectors.toList;


public  abstract class CrazyPiece {
    public int idPersonagem;
    public int idTipoPeca;
    public int idEquipa;
    public String alcunha;
    public int x;
    public int y;
    public int xAnt = -1;
    public int yAnt = -1;
    public String tipoAtual;
    public boolean emJogo=false;
    public int nrRonda=0;

    public int valorRelativo;

    //Estatisticas
    public int nrPontos = 0;
    public int nrCapturas = 0;
    public int nrJogadasValidas = 0;
    public int nrJogadasInvalidas = 0;

    public int nrPontosAntigos = 0;
    public int nrCapturasAntigos = 0;
    public int nrJogadasValidasAntigos = 0;
    public int nrJogadasInvalidasAntigos = 0;

    public int nrCapturasTotalPorIDTipo = 0;

    CrazyPiece(int idPersonagem,int idTipoPeca,int idEquipa,String alcunha, int nrRonda){
        this.idPersonagem = idPersonagem;
        this.idTipoPeca = idTipoPeca;
        this.idEquipa =idEquipa;
        this.alcunha = alcunha;
        this.nrRonda = nrRonda;
    }
    CrazyPiece(int idPersonagem,int idTipoPeca,int idEquipa,String alcunha ,int x, int y){
        this.idPersonagem = idPersonagem;
        this.idTipoPeca = idTipoPeca;
        this.idEquipa =idEquipa;
        this.alcunha = alcunha;
        this.x = x;
        this.y = y;
    }
    public String toString(){
        if (estaEmJogo() && idTipoPeca != 7){
            return idPersonagem + " | " + tipoPeca()+ " | " + valorRelativo() + " | " + idEquipa + " | " + alcunha + " @ (" + x + ", " + y + ")";
        }else if (!estaEmJogo() && idTipoPeca != 7){
            return idPersonagem + " | " + tipoPeca()+ " | " + valorRelativo() + " | "+ idEquipa + " | " + alcunha + " @ (n/a)";
        }else if(estaEmJogo() && idTipoPeca == 7)  {
            return idPersonagem + " | " + tipoPeca() +"/"+getTipoAtual()+ " | " + valorRelativo() + " | " + idEquipa + " | " + alcunha + " @ (" + x + ", " + y + ")";
        }else {
            return idPersonagem + " | " + tipoPeca() +"/"+getTipoAtual()+ " | " + valorRelativo() + " | " + alcunha + " @ (n/a)";
        }
    }

    public int getValorRelativo(){
        if(valorRelativo().equals("(infinito)")) {
            valorRelativo = 1000;
        }else{
            valorRelativo = Integer.parseInt(valorRelativo());
        }
        return valorRelativo;
    }

    public int getNrPontos(){
        return nrPontos;
    }
    public int getNrCapturas(){
        return nrCapturas;
    }
    public int getNrJogadasValidas(){
        return nrJogadasValidas;
    }
    public int getNrJogadasInvalidas(){
        return nrJogadasInvalidas;
    }

    public void anularJogadaNrPontos(){
        this.nrPontos = nrPontosAntigos;
    }
    public void anularJogadaNrCapturas(){
        this.nrCapturas = nrCapturasAntigos;
    }
    public void anularJogadaNrJogadaValida(){
        this.nrJogadasValidas = nrJogadasValidasAntigos;
    }
    public void anularJogadasNrJogadasInvalidas(){
        this.nrJogadasInvalidas = nrJogadasInvalidasAntigos;
    }

    public void anularJogada(){
        anularJogadaNrCapturas();
        anularJogadaNrPontos();
        anularJogadaNrJogadaValida();
        anularJogadasNrJogadasInvalidas();
    }

    public void aumentaNrPontos(CrazyPiece pecaAjuda1, CrazyPiece pecaAjuda2){

        if(pecaAjuda2.valorRelativo().equals("(infinito)")){
            pecaAjuda1.nrPontosAntigos  = pecaAjuda1.nrPontos;
            pecaAjuda1.nrPontos += 1000;
        }else{
            pecaAjuda1.nrPontosAntigos  = pecaAjuda1.nrPontos;
            pecaAjuda1.nrPontos += Integer.parseInt(pecaAjuda2.valorRelativo());
        }

    }
    public void aumentaNrCapturas(){
        this.nrCapturasAntigos = this.nrCapturas;
        this.nrCapturas++;
    }
    public void aumentaNrJogadasValidas(){
        this.nrJogadasValidasAntigos = this.nrJogadasValidas;
        this.nrJogadasValidas++;
    }
    public void aumentaNrJogadasInvalidas(){
        this.nrJogadasInvalidasAntigos = this.nrJogadasInvalidas;
        this.nrJogadasInvalidas++;
    }


    public int somaTotalJogadas(){
        return getNrJogadasInvalidas() + getNrJogadasValidas();
    }

    public void setTurno(int nrRonda){

        this.nrRonda= nrRonda;
    }

    public String getAlcunha(){
        return alcunha;
    }

    public String escrevePecaFicheiro(){

        if(estaEmJogo()){
            return idPersonagem + ":" + getIdTipoPeca() + ":" + idEquipa + ":" + alcunha;
        }
        return "";

    }

    public abstract String tipoPeca();

    public abstract String valorRelativo();

    public abstract String nomeImagem();

    protected abstract String getTipoAtual();

    public abstract boolean moverPeca(int novoX, int novoY, int tamanho, List<CrazyPiece> pecasEmJogo);

    public int getPecasCapturadas(){

        return 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getId() {
        return idPersonagem;
    }

    public String getImagePNG(){

        return nomeImagem();

    }

    public void alteraX(int novoX){
        this.x = novoX;
    }

    public boolean estaEmJogo(){
        return emJogo;
    }

    public void alteraY(int novoY){
        this.y = novoY;
    }

    public boolean dentroTabuleiro (int x , int y , int tamanho){
        if ((x >= 0 && y >= 0) && (x <= tamanho && y <= tamanho)){
            return true;
        }
        return false;
    }

    public int getIdTipoPeca() {
        return idTipoPeca;
    }

    public int getIdEquipa() {
        return idEquipa;
    }

    public void atualizaParaNEstarJogo(){
        this.emJogo=false;
    }

    public void atualizaParaEstarJogo(){
        this.emJogo=true;
    }

    public boolean percorrePecaXRei(int novoX, int y, List<CrazyPiece> pecasEmJogo){ // verificar se nao existem um rei no caminho na horizontal

        if (novoX > this.x) {// estamos a ir para a esquerda
            for (int coluna = this.x + 1; coluna != novoX ; coluna++) {

                for (CrazyPiece peca : pecasEmJogo) {
                    if (peca.getX() == coluna && peca.getY() == y && peca.idTipoPeca == 0 && peca.estaEmJogo()) {
                        return false;
                    }
                }
            }
        } else {

            for (int coluna = novoX ; coluna <= this.x  ; coluna++) {

                for (CrazyPiece peca : pecasEmJogo) {
                    if (peca.getX() == coluna && peca.getY() == y && peca.idTipoPeca == 0 && peca.estaEmJogo()) {
                        return false;
                    }
                }

            }
        }

        return true;
    }

    public boolean percorrePecaYRei(int x, int novoY, List<CrazyPiece> pecasEmJogo){  // verificar se nao existem um rei no caminho na vertical

        if (novoY > this.y) {// estamos a descer
            for (int linha = this.y + 1; linha != novoY  ; linha++) {

                for (CrazyPiece peca : pecasEmJogo) {
                    if (peca.getX() == x && peca.getY() == linha && peca.idTipoPeca == 0 && peca.estaEmJogo()) {
                        return false;
                    }
                }
            }
        } else {

            for (int linha = novoY ; linha <= this.y  ; linha++) {

                for (CrazyPiece peca : pecasEmJogo) {
                    if (peca.getX() == x && peca.getY() == linha && peca.idTipoPeca == 0 && peca.estaEmJogo()) {
                        return false;
                    }
                }

            }
        }

        return true;
    }

    public boolean percorrePecasX(int novoX, List<CrazyPiece> pecasEmJogo){  // verificar se nao existem pecas no caminho na horizontal
        if (novoX > this.x) {// estamos a ir para a direita
            for (int coluna = this.x + 1; coluna != novoX ; coluna++) {

                for (CrazyPiece peca : pecasEmJogo) {
                    if (peca.getX() == coluna && peca.getY() == this.y && peca.estaEmJogo()) {
                        return false;
                    }
                }
            }
        } else {

            for (int coluna = novoX +1 ; coluna != this.x ; coluna++) {

                for (CrazyPiece peca : pecasEmJogo) {
                    if (peca.getX() == coluna && peca.getY() == this.y && peca.estaEmJogo()) {
                        return false;
                    }
                }

            }
        }
        return true;
    }

    public boolean percorrePecasY(int novoY, List<CrazyPiece> pecasEmJogo){  // verificar se nao existem pecas no caminho na vertical
        if (novoY > this.y) {// estamos a descer
            for (int linha = this.y + 1; linha != novoY ; linha++) {

                for (CrazyPiece peca : pecasEmJogo) {
                    if (peca.getX() == this.x && peca.getY() == linha && peca.estaEmJogo()) {
                        return false;
                    }
                }
            }
        } else {

            for (int linha = novoY + 1 ; linha != this.y ; linha++) {

                for (CrazyPiece peca : pecasEmJogo) {
                    if (peca.getX() == this.x && peca.getY() == linha && peca.estaEmJogo()) {
                        return false;
                    }
                }

            }
        }
        return true;
    }

    public boolean percorrePecaXY(int novoX, int novoY, List<CrazyPiece> pecasEmJogo){ // verificar se nao existem pecas no caminho na diagonal

        if(novoX > this.x && novoY > this.y){ // direita baixo
            int coluna,linha;
            for(coluna = this.x + 1 , linha = this.y  + 1; coluna!= novoX  && linha != novoY ; coluna++ , linha++){

                for(CrazyPiece peca : pecasEmJogo){
                    if(peca.getX() == coluna && peca.getY() == linha && peca.emJogo){
                        return false;
                    }
                }
            }
        }else if(novoX > this.x && novoY < this.y){ // direita cima
            int coluna,linha;
            for(coluna = this.x  + 1, linha = this.y - 1; coluna!= novoX && linha != novoY ; coluna++ , linha--){

                for(CrazyPiece peca : pecasEmJogo){
                    if(peca.getX() == coluna && peca.getY() == linha && peca.emJogo){
                        return false;
                    }
                }
            }
        }else if (novoX < this.x && novoY > this.y){ // esquerda baixo
            int coluna,linha;
            for(coluna = this .x - 1, linha = this.y  + 1; coluna!= novoX && linha != novoY ; coluna-- , linha++){

                for(CrazyPiece peca : pecasEmJogo){
                    if(peca.getX() == coluna && peca.getY() == linha && peca.emJogo){
                        return false;
                    }
                }
            }
        }else{ // esquerda cima
            int coluna,linha;
            for(coluna = this.x - 1  , linha = this.y - 1  ; coluna!= novoX && linha != novoY ; coluna-- , linha--){

                for(CrazyPiece peca : pecasEmJogo){
                    if(peca.getX() == coluna && peca.getY() == linha && peca.emJogo){
                        return false;
                    }
                }
            }
        }

        return true;
    }

    //______//______//

    public int pecaNaPosicaoIdEquipa(int x ,int y, List<CrazyPiece> pecasEmJogo){
        for (CrazyPiece peca :pecasEmJogo){
            if (peca.getX() == x && peca.getY() == y && peca.estaEmJogo()){
                return peca.idEquipa;
            }
        }
        return 0;
    }

    public void undoPosicao (){
        this.x = xAnt;
        this.y = yAnt;
    }

    public String e1(){
        return getIdEquipa() + ":" + getAlcunha() + ":" + getNrPontos() + ":" + getNrCapturas();
    }

    public String e4(){ return getIdEquipa() + ":" + getAlcunha() + ":" + getNrJogadasInvalidas() + ":" + getNrJogadasValidas(); }

    public String e5(){ return getIdTipoPeca() + ":" + getNrCapturas();}






}