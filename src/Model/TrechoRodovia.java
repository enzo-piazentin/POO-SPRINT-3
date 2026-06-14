package Model;

public class TrechoRodovia implements MonitoravelViaIoT {

    private int km;
    private double alturaVegetacao;
    private String tipoTerreno;

    public TrechoRodovia(int km, double alturaVegetacao, String tipoTerreno) {
        this.km = km;
        this.alturaVegetacao = alturaVegetacao;
        this.tipoTerreno = tipoTerreno;
    }

    @Override
    public void transmitirDadosSensor() {
        System.out.println(
                "KM " + km +
                        " transmitindo altura: " + alturaVegetacao + " cm"
        );
    }

    public double calcularCrescimentoDiario() {
        if(tipoTerreno.equalsIgnoreCase("umido")) {
            return 5.0;
        }
        return 2.0;
    }

    public void atualizarVegetacao() {
        alturaVegetacao += calcularCrescimentoDiario();
    }

    public double getAlturaVegetacao() {
        return alturaVegetacao;
    }

    public int getKm() {
        return km;
    }
}
