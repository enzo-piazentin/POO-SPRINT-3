package Model;

public class TrechoMonitoradoIoT extends TrechoRodovia implements MonitoravelViaIoT {

    public TrechoMonitoradoIoT(int km, double alturaVegetacao, String tipoTerreno) {
        super(km, alturaVegetacao, tipoTerreno);
    }

    @Override
    public void transmitirDadosSensor() {
        System.out.println(
                "KM " + getKm() + " transmitindo altura: " + getAlturaVegetacao() + " cm"
        );
    }
}
