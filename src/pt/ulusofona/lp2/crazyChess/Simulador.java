package pt.ulusofona.lp2.crazyChess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Scanner;

import static java.util.stream.Collectors.toList;

public class Simulador {
    private HashSet<Integer> listaIDPecas =new HashSet<>();
    private HashMap<Integer,CrazyPiece> mapaPecas = new HashMap<>();
    private HashMap<Integer,CrazyPiece> mapaPecasIniciais = new HashMap<>();
    private List<CrazyPiece> pecasCapturadasS = new ArrayList<>();
  //  private List<CrazyPiece> pecasEmJogoSemCapturadas = new ArrayList<>();
    private int tamanhoTabuleiro;
    private int quantidadePecas = 1;
    private int rondasSemCaptura=0;
    private Estatistica estatistica = new Estatistica();
    private CrazyPiece pecaCapturada = null;
    private CrazyPiece ultimaPecaMovida = null; // ultima peca a ser Movida
    private int equipaAtual = 10;// Inicia as peças pretas //10 Pretas // 20 Brancas
    private List<CrazyPiece> pecasEmJogo =new ArrayList<>();
    private int nrRonda=0;

    private boolean resultaCaptura = false;
    private int nrPontosCaptura = 0;


    public void iniciaJogo(File ficheiroInicial) throws IOException{
        int count =0 ,linhaMat=0;
        Peca pecaEmJogo = new Peca();
        estatistica.limpaVariaveis();
        limpaVariaveis();
        try {
            Scanner leitorFicheiro = new Scanner(ficheiroInicial);
            // enquanto o ficheiro tiver linhas não-

            while(leitorFicheiro.hasNextLine()) {
                // ler uma linha do
                String linha = leitorFicheiro.nextLine();
                // partir a linha no caractere
                String dados[] = linha.split(":");
                if (count < 2 ) {
                    if (count == 0){
                        //verifica o tamanho do
                        if(dados.length != 1){
                            throw new InvalidSimulatorInputException(count+1,1,dados.length);
                        }

                        if (4<=Integer.parseInt(dados[0]) && Integer.parseInt(dados[0])<=12){
                            tamanhoTabuleiro(Integer.parseInt(dados[0]));
                        }

                    }else{
                        if(dados.length != 1){
                            throw new InvalidSimulatorInputException(count+1,1,dados.length);
                        }

                        if (Integer.parseInt(dados[0])< getTamanhoTabuleiro() * getTamanhoTabuleiro()){
                            quantidadePecas = Integer.parseInt(dados[0]);
                        }

                    }
                    count++;
                }else if (count < 2 + quantidadePecas) {
                    // converter as Strings lidas para os tipos esperados
                    //Integer.parseInt(dados[0])-> idPecas
                    //Integer.parseInt(dados[1])-> tipoPeca
                    //Integer.parseInt(dados[2])-> idEquipa
                    //dados[3]-> Alcunha
                    CrazyPiece pecaA = null;
                    if(dados.length != 4){
                        throw new InvalidSimulatorInputException(count+1,4,dados.length);
                    }

                    if(!listaIDPecas.contains(Integer.parseInt(dados[0])) && Integer.parseInt(dados[0])>=1) {
                        if(Integer.parseInt(dados[0])>=0 && Integer.parseInt(dados[1])<=10) {
                            if (Integer.parseInt(dados[2]) == 10 || Integer.parseInt(dados[2]) == 20) {
                                //Alterar para uma lista do tipo CrazyPiece

                                pecaA = pecaEmJogo.pec(Integer.parseInt(dados[0]),Integer.parseInt(dados[1]),Integer.parseInt(dados[2]),dados[3],nrRonda);


                                listaIDPecas.add(Integer.parseInt(dados[0]));
                                mapaPecas.put(Integer.parseInt(dados[0]),pecaA);

                            }
                        }
                    }

                    count++;
                }else if(count < 2 + quantidadePecas + getTamanhoTabuleiro()){
                    CrazyPiece pecaA;

                    if(dados.length != getTamanhoTabuleiro()){
                        throw new InvalidSimulatorInputException(count+1,getTamanhoTabuleiro(),dados.length);
                    }

                    for (int coluna = 0; coluna < getTamanhoTabuleiro(); coluna++) {
                        if( Integer.parseInt(dados[coluna]) != 0 ) {
                            pecaA = mapaPecas.get(Integer.parseInt(dados[coluna]));
                            pecaA.alteraX(coluna);
                            pecaA.alteraY(linhaMat);
                            pecaA.atualizaParaEstarJogo();
                            mapaPecas.put(Integer.parseInt(dados[coluna]),pecaA);

                        }
                    }

                    linhaMat++;
                    count++;

                    /*

                    for(CrazyPiece peca : pecasEmJogo){

                        int ola = peca.getNrCapturas();
                        peca.aumentaNrPontos(Integer.parseInt(peca.valorRelativo()));
                        int pecaa = peca.getNrPontos();

                    }

                    */

                }else{
                    equipaAtual = Integer.parseInt(dados[0]);
                    estatistica.setJogadasValidPretas(Integer.parseInt(dados[1]));
                    estatistica.setCapturasParaPretas(Integer.parseInt(dados[2]));
                    estatistica.setTentativasInvPretas(Integer.parseInt(dados[3]));
                    estatistica.setJogadasValidBrancas(Integer.parseInt(dados[4]));
                    estatistica.setCapturasParaBrancas(Integer.parseInt(dados[5]));
                    estatistica.setTentativasInvBrancas(Integer.parseInt(dados[6]));
                    rondasSemCaptura = Integer.parseInt(dados[7]);

                    count++;
                }



            }

            leitorFicheiro.close();
            if(mapaPecas.size()!=0){
                pecasEmJogo = new ArrayList<>(mapaPecas.values());
                mapaPecasIniciais = mapaPecas;
            }



        } catch(FileNotFoundException exception) {
            throw new IOException();
        }
    }

    public int getRondasSemCaptura(){
        return rondasSemCaptura;
    }

    public void tamanhoTabuleiro (int tamanho){
        this.tamanhoTabuleiro=tamanho;
    }

    public int getTamanhoTabuleiro(){
        return tamanhoTabuleiro;
    }

    public List<CrazyPiece> getPecasMalucas() {
        return pecasEmJogo;
    }

    public List<String> getAutores() {
        List<String> dados = new ArrayList<>();
        dados.add("a21705671 - Afonso Marques");
        dados.add("a21701080 - Duarte Olival");
        return dados;
    }

    public int getIDPeca(int x, int y){
        for (CrazyPiece peca : pecasEmJogo){
            if (peca.estaEmJogo()) {
                if (peca.getX() == x && peca.getY() == y) {
                    return peca.getId();
                }
            }
        }
        return 0;
    }

    public List<String> getResultados() {
        List<String> resultados = new ArrayList<>();
        resultados.add("JOGO DE CRAZY CHESS");
        switch (estadoDoJogo()){
            case 0:
                //case 3:
                resultados.add("Resultado: EMPATE");
                break;
            case 1:
                resultados.add("Resultado: VENCERAM AS PRETAS");
                break;
            case 2:
                resultados.add("Resultado: VENCERAM AS BRANCAS");
                break;
        }
        resultados.add("---");
        resultados.add("Equipa das Pretas");
        resultados.add(" Capturas: "+String.valueOf(estatistica.getCapturasParaPretas()));
        resultados.add(" Jogadas válidas: "+String.valueOf(estatistica.getJogadasValidPretas()));
        resultados.add(" Tentativas inválidas: "+String.valueOf(estatistica.getTentativasInvPretas()));
        resultados.add("Equipa das Brancas");
        resultados.add(" Capturas: "+String.valueOf(estatistica.getCapturasParaBrancas()));
        resultados.add(" Jogadas válidas: "+String.valueOf(estatistica.getJogadasValidBrancas()));
        resultados.add(" Tentativas inválidas: "+String.valueOf(estatistica.getTentativasInvBrancas()));
        // resultados.add("Afonso Marques - a21705671");
        //resultados.add("Duarte Olival - a21701080");
        return resultados;
    }

    public boolean jogoTerminado(){
        switch (estadoDoJogo()){
            case 0:
                return true;
            case 1:
                return true;
            case 2:
                return true;
            case 3:
                return false;
        }
        return false;
    }

    public int getIDEquipaAJogar(){
        return equipaAtual;
    }

    public void limpaVariaveis(){
        listaIDPecas.clear();
        mapaPecas.clear();
        mapaPecasIniciais.clear();
        pecasEmJogo.clear();
        pecasCapturadasS.clear();
        quantidadePecas=1;
        equipaAtual=10;
        rondasSemCaptura=0;
        pecaCapturada = null;
        ultimaPecaMovida = null;

        nrRonda=0;
    }

    public void mudaEquipa(){
        if (this.equipaAtual == 10) {
            this.equipaAtual = 20;
        }else {
            this.equipaAtual = 10;
        }
    }

    public boolean adicionaPeca(CrazyPiece pecaAdicionar){
        for (CrazyPiece peca : pecasEmJogo){
            if (peca.getY() == pecaAdicionar.getY() && peca.getX() == pecaAdicionar.getX()){
                return false;
            }
        }
        pecasEmJogo.add(pecaAdicionar);
        return true;
    }

    public int pecasEmJogo(){return pecasEmJogo.size() - pecasCapturadasS.size();}

    public void percorrePecasAumentaJval(CrazyPiece peca){
                    peca.aumentaNrJogadasValidas();
    }

    public void percorrePecasAumentaJInv(CrazyPiece peca){
                peca.aumentaNrJogadasInvalidas();
    }

    public void jogadaInvalida(CrazyPiece pecaAjuda1){
        if(equipaAtual==10){
            estatistica.adicionaTentativasInvPretas();
            pecaAjuda1.aumentaNrJogadasInvalidas();
        }else{
            estatistica.adicionaTentativasInvBrancas();
            pecaAjuda1.aumentaNrJogadasInvalidas();
        }
    }

    public boolean processaJogada(int xO, int yO, int xD, int yD){//corrigir erro
        CrazyPiece pecaAjuda1 = null;
        CrazyPiece pecaAjuda2 = null;
        resultaCaptura = false;
        nrPontosCaptura = 0;
        boolean result=false;
            for (CrazyPiece peca : pecasEmJogo) {
                if (peca.getX() == xO && peca.getY() == yO && peca.estaEmJogo()) {
                    result=true;
                    pecaAjuda1 = peca;
                    break;
                }
            }
        if (((xO >= 0 && xO < getTamanhoTabuleiro()) && (yO >= 0 && yO < getTamanhoTabuleiro()))&&
                (xD >= 0 && xD < getTamanhoTabuleiro() && yD >= 0 && yD < getTamanhoTabuleiro())) {
            if(result){
                if (pecaAjuda1.getIdEquipa()==equipaAtual){
                    result=false;
                    for (CrazyPiece peca : pecasEmJogo){
                        if (peca.getX()==xD && peca.getY() == yD && peca.estaEmJogo()){
                            result = true;
                            pecaAjuda2 = peca;
                            break;
                        }
                    }
                    if(result){
                        //comer caso seja da outra equipa
                        if (pecaAjuda2.getIdEquipa()==equipaAtual && pecaAjuda2.getId()!=8) {

                            jogadaInvalida(pecaAjuda1);
                            return false;
                        }else {

                            if(pecaAjuda1.moverPeca(xD,yD, getTamanhoTabuleiro(),getPecasMalucas())){
                                if(equipaAtual==10) {

                                    estatistica.adicionaJogadasValidPretas();
                                    if (pecaAjuda1.getIdTipoPeca() == 8) {

                                        for (int i = 0; i < pecaAjuda1.getPecasCapturadas(); i++) {
                                            estatistica.adicionaCapturasParaPretas();
                                        }

                                    }else {
                                        estatistica.adicionaCapturasParaPretas();
                                    }
                                }else{
                                    estatistica.adicionaJogadasValidBrancas();
                                    if(pecaAjuda1.getIdTipoPeca() == 8){

                                        for(int i=0; i<pecaAjuda1.getPecasCapturadas();i++){
                                            estatistica.adicionaCapturasParaBrancas();
                                        }

                                    }else {
                                        estatistica.adicionaCapturasParaBrancas();
                                    }
                                }
                                pecaCapturada = pecaAjuda2 ;
                                pecaAjuda2.atualizaParaNEstarJogo();

                                if(pecaAjuda1.getIdTipoPeca() == 8){

                                    for(CrazyPiece peca: pecasEmJogo){
                                        if(!peca.estaEmJogo()){
                                            pecasCapturadasS.add(pecaAjuda2);
                                        }
                                    }

                                }else {
                                    pecasCapturadasS.add(pecaAjuda2);
                                }


                                ultimaPecaMovida = pecaAjuda1;
                                rondasSemCaptura = 0;
                                nrPontosCaptura = pecaAjuda2.getValorRelativo();
                                resultaCaptura = true;
                                mudaEquipa();
                                nrRonda++;
                                for(CrazyPiece peca: pecasEmJogo){
                                    peca.setTurno(nrRonda);
                                    if(peca.idTipoPeca == pecaAjuda1.idTipoPeca){
                                        peca.nrCapturasTotalPorIDTipo++;
                                    }
                                }

                                percorrePecasAumentaJval(pecaAjuda1);
                                pecaAjuda1.aumentaNrCapturas();
                                pecaAjuda1.aumentaNrPontos(pecaAjuda1,pecaAjuda2);

                                return true;
                            }else{
                                jogadaInvalida(pecaAjuda1);
                                return false;
                            }
                        }
                    }else{
                        if(pecaAjuda1.moverPeca(xD,yD, getTamanhoTabuleiro(), getPecasMalucas())){
                            if(equipaAtual!=10){
                                estatistica.adicionaJogadasValidBrancas();
                                if(pecaAjuda1.getIdTipoPeca() == 8){

                                    for(int i=0; i<pecaAjuda1.getPecasCapturadas();i++){
                                            estatistica.adicionaCapturasParaBrancas();
                                    }
                                }
                            }else{

                                estatistica.adicionaJogadasValidPretas();
                                if(pecaAjuda1.getIdTipoPeca() == 8){

                                    for(int i=0; i<pecaAjuda1.getPecasCapturadas();i++){
                                        estatistica.adicionaCapturasParaPretas();
                                    }
                                }
                            }
                            ultimaPecaMovida = pecaAjuda1;
                            mudaEquipa();
                            rondasSemCaptura++;
                            nrRonda++;
                            for(CrazyPiece peca: pecasEmJogo){

                                peca.setTurno(nrRonda);

                            }
                            percorrePecasAumentaJval(pecaAjuda1);
                            return true;
                        }
                    }
                }
            }
        }
        jogadaInvalida(pecaAjuda1);

        return false;
    }

    // (0-> empate) (1-> Pretas Vencem) (2-> Brancas vencem) (3 -> ainda não se verificaram as condições de paragen)
    public int estadoDoJogo() {
        int reisPretos=0, reisBrancos=0;
        int pecasEmJogoAtuais=0;
        for (CrazyPiece peca : pecasEmJogo){

            if (peca.estaEmJogo()){
                if (peca.getIdEquipa() == 20 && peca.getIdTipoPeca() == 0){
                    reisBrancos++;
                }else if(peca.getIdTipoPeca() == 0){
                    reisPretos++;
                }
            }
            if (peca.estaEmJogo()){
                pecasEmJogoAtuais++;
            }

        }

        if (estatistica.getCapturasParaBrancas() != 0 || estatistica.getCapturasParaPretas()!= 0) {
            if (rondasSemCaptura > 9){
                return 0;
            }
        }
        if (reisBrancos == 1 && reisPretos == 1 && pecasEmJogoAtuais == 2){
            return 0;
        }
        if (reisBrancos == 0 ){
            return 1;
        }else if (reisPretos == 0){
            return 2;
        }
        return 3;
    }
    
    //_________________//_________________//
    public List<Comparable> obterSugestoesJogada(int xO, int yO){
        List<Comparable> sugestoes3Parte = new ArrayList<>();
        CrazyPiece pecaAjuda1 = null;
        int xI ,yI;
        for (CrazyPiece peca : pecasEmJogo) {
            if (peca.getX() == xO && peca.getY() == yO && peca.estaEmJogo()) {

                pecaAjuda1=peca;
                break;
            }
        }

        if(pecaAjuda1!=null && pecaAjuda1.idEquipa == equipaAtual) {
            for (int linha = 0; linha < getTamanhoTabuleiro(); linha++) {

                for (int coluna = 0; coluna < getTamanhoTabuleiro(); coluna++) {

                    xI = pecaAjuda1.getX();
                    yI = pecaAjuda1.getY();

                    if (processaJogada(xI,yI,linha,coluna)) {
                        //sugestoes.add(linha + ", " + coluna);

                        SugestoesJogada sJ = new SugestoesJogada(linha,coluna,nrPontosCaptura,resultaCaptura);
                        sugestoes3Parte.add(sJ);
                        anularJogadaAnterior();
                    }

                }
            }
        }else {
            //sugestoes.add("Pedido inválido");
        }

        return sugestoes3Parte;
    }//fazer

    public boolean gravarJogo(File ficheiroDestino){

        try {

            FileWriter writer = new FileWriter(ficheiroDestino);
            String newLine = System.getProperty("line.separator");

            //Primeira secção-> Dimensoes do tabuleiro
            writer.write(Integer.toString(getTamanhoTabuleiro()));
            writer.write(newLine);

            //Segunda secção-> pecas existentes no tabuleiro
            writer.write(Integer.toString(pecasEmJogo()));
            writer.write(newLine);

            //Terceira secção-> pecas existentes no tabuleiro
            for(CrazyPiece peca:pecasEmJogo){
                if(peca.estaEmJogo()){
                    writer.write(peca.escrevePecaFicheiro());
                    writer.write(newLine);
                }
            }

            //Quarta secção-> conteudo inicial do tabuleiro
            boolean result = false;
            int idPeca = 0;
            for (int linha = 0; linha < getTamanhoTabuleiro(); linha++) {

                for (int coluna = 0; coluna < getTamanhoTabuleiro(); coluna++) {

                    for (CrazyPiece peca : pecasEmJogo) {
                        if (peca.getX() == coluna && peca.getY() == linha && peca.estaEmJogo()) {
                            idPeca = peca.getId();
                            result = true;
                            break;
                        }
                    }

                    if(result){
                        writer.write(Integer.toString(idPeca));
                        idPeca = 0;
                    }else{
                        writer.write(Integer.toString(idPeca));
                    }

                    if(coluna < getTamanhoTabuleiro() -1){
                        writer.write(":");
                    }else{
                        writer.write(newLine);
                    }

                }
            }



            //Quinta secção-> estado dos varios contadores
            writer.write(Integer.toString(getIDEquipaAJogar()));
            writer.write(":");
            writer.write(Integer.toString(estatistica.getJogadasValidPretas()));
            writer.write(":");
            writer.write(Integer.toString(estatistica.getCapturasParaPretas()));
            writer.write(":");
            writer.write(Integer.toString(estatistica.getTentativasInvPretas()));
            writer.write(":");
            writer.write(Integer.toString(estatistica.getJogadasValidBrancas()));
            writer.write(":");
            writer.write(Integer.toString(estatistica.getCapturasParaBrancas()));
            writer.write(":");
            writer.write(Integer.toString(estatistica.getTentativasInvBrancas()));
            writer.write(":");
            writer.write(Integer.toString(getRondasSemCaptura()));
            writer.close();
        }catch (IOException e){
            System.out.println("Ocorreu um erro");
            return false;
        }
        return true;
    }

    public void anularJogadaAnterior(){
        if (pecaCapturada != null){
            pecaCapturada.atualizaParaEstarJogo();
            ultimaPecaMovida.undoPosicao();
            adicionaPeca(pecaCapturada);
            if(ultimaPecaMovida != null && ultimaPecaMovida.getIdTipoPeca() == 8 && nrRonda != 0){

                PecaMesmoMaluca peca = ((PecaMesmoMaluca) ultimaPecaMovida);
                peca.undoJogadaZorro();
                ultimaPecaMovida.undoPosicao();

            }

            pecaCapturada = null;
            rondasSemCaptura--;
        }else if(ultimaPecaMovida != null && ultimaPecaMovida.getIdTipoPeca() == 8 && nrRonda != 0){

            PecaMesmoMaluca peca = ((PecaMesmoMaluca) ultimaPecaMovida);
            peca.undoJogadaZorro();
            ultimaPecaMovida.undoPosicao();


        }else {

            if (ultimaPecaMovida != null) {
                ultimaPecaMovida.undoPosicao();
            }
            ultimaPecaMovida = null;

        }
        estatistica.undoEstatistica();
        mudaEquipa();
        nrRonda--;
        for(CrazyPiece peca: pecasEmJogo){

            peca.setTurno(nrRonda);
            peca.anularJogada();

        }
    }

    public Estatistica getEstatistica() {
        return estatistica;
    }


    //Parte 3 estatistica

    public int pecasEmJogoSemCaptura(){
        int nrpecasTotal = 0;
        for(CrazyPiece peca : pecasEmJogo){

            if(peca.estaEmJogo()){
                nrpecasTotal++;
            }
        }
        return nrpecasTotal;
    }

    public List<String> topCincoCapturas() {

        if (pecasEmJogoSemCaptura() < 5){

            List<String> topCincoCapturas =
                    pecasEmJogo.stream()
                            .filter(p -> p.estaEmJogo())
                            .map(CrazyPiece::e1)
                            .collect(toList());
            return topCincoCapturas;
        }else{

            List<String> topCincoCapturas =
                    pecasEmJogo.stream()
                            .sorted(Comparator.comparing(CrazyPiece::getAlcunha))
                            .sorted((p1,p2) -> p2.getNrCapturas() - p1.getNrCapturas())
                            .limit(5)
                            .map(CrazyPiece::e1)
                            .collect(toList());

            return topCincoCapturas;
        }

    }

    public List<String> top5Pontos(){

        if (pecasEmJogoSemCaptura() < 5){
            List<String> top5Pontos =
                    pecasEmJogo.stream()
                            .filter(p -> p.estaEmJogo())
                            .map(CrazyPiece::e1)
                            .collect(toList());
            return top5Pontos;
        }else{

            List<String> topCincoCapturas =
                    pecasEmJogo.stream()
                            .sorted(Comparator.comparing(CrazyPiece::getAlcunha))
                            .sorted((p1,p2) -> p2.getNrPontos() - p1.getNrPontos())
                            .limit(5)
                            .map(CrazyPiece::e1)
                            .collect(toList());

            return topCincoCapturas;
        }

    }

    public List<String> pecasMais5Capturas(){


        List<String> pecasMaisCincoCapturas =
                pecasEmJogo.stream()
                        .filter( (p) -> p.getNrCapturas() > 5)
                        .map(CrazyPiece::e1)
                        .collect(toList());


        return pecasMaisCincoCapturas;

    }

    public List<String> tresPecasMaisBaralhadas() {

        List<String> tresPecasMaisBaralhadas =
                pecasEmJogo.stream()
                        .filter(p -> p.getNrJogadasValidas() != 0 || p.getNrJogadasInvalidas() != 0)
                        .sorted((p1,p2) -> (p1  .nrJogadasInvalidas / (p1.nrJogadasInvalidas + p1.nrJogadasValidas) - (p2.nrJogadasInvalidas/(p2.nrJogadasValidas+p2.nrJogadasInvalidas))))
                        .limit(3)
                        .map(CrazyPiece::e4)
                        .collect(toList());

        return tresPecasMaisBaralhadas;

    }

    public List<String> tiposPecaCapturados(){


        List<String> tiposPecaCapturados =
                pecasEmJogo.stream()
                        .filter((p)-> p.nrCapturasTotalPorIDTipo > 0)
                        .sorted((p1,p2) -> p2.nrCapturasTotalPorIDTipo - p1.nrCapturasTotalPorIDTipo )
                        .distinct()
                        .map(CrazyPiece::e5)
                        .collect(toList());


        return tiposPecaCapturados;

    }

    public Map<String, List<String>> getEstatisticas(){

        Map<String, List<String>> estatisticasNovas = new HashMap<>();

        estatisticasNovas.put("top5Capturas",topCincoCapturas());
        estatisticasNovas.put("top5Pontos",top5Pontos());
        estatisticasNovas.put("pecasMais5Capturas",pecasMais5Capturas());
        estatisticasNovas.put("3PecasMaisBaralhadas", tresPecasMaisBaralhadas());
        estatisticasNovas.put("tiposPecaCapturados", tiposPecaCapturados());

        return estatisticasNovas;
    }//fazer

    public int getNrRonda() {
        return nrRonda;
    }

    public void setEquipaAtual(int equipaAtual) {
        this.equipaAtual = equipaAtual;
    }

    public void setNrRonda(int nrRonda) {
        this.nrRonda = nrRonda;
    }
}