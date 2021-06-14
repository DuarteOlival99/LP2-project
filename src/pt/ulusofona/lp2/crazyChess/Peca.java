package pt.ulusofona.lp2.crazyChess;


public class Peca {

    public CrazyPiece pec (int id ,int tipo ,int idEquipa , String alcunha,int nrRonda){
        //List<CrazyPiece> pecaEmJogo = null;
        //erro nao se pode inicializar a null;
        CrazyPiece pecaA = null;
        switch (tipo) {
            case 0:
                pecaA = new Rei(id, tipo, idEquipa, alcunha, nrRonda);
                break;
            case 1:
                pecaA = new Rainha(id, tipo, idEquipa, alcunha, nrRonda);
                break;
            case 2:
                pecaA = new PoneiMagico(id, tipo, idEquipa, alcunha, nrRonda);
                break;
            case 3:
                pecaA = new PadreDaVila(id, tipo, idEquipa, alcunha, nrRonda);
                break;
            case 4:
                pecaA = new TorreHor(id, tipo, idEquipa, alcunha, nrRonda);
                break;
            case 5:
                pecaA = new TorreVert(id, tipo, idEquipa, alcunha, nrRonda);
                break;
            case 6:
                pecaA = new Lebre(id, tipo, idEquipa, alcunha, nrRonda);
                break;
            case 7:
                pecaA = new Joker(id, tipo, idEquipa, alcunha, nrRonda);
                break;
            case 8:
                pecaA = new PecaMesmoMaluca(id, tipo, idEquipa, alcunha, nrRonda);
                break;
        }
        //pecaEmJogo = pecaA;
        return pecaA;
    }
}