package Model;

import java.util.Objects;

/** Simple: representa um trecho da rodovia. */
public class TrechoRodovia {

    private final int km;
    private double alturaVegetacao;
    private final String tipoTerreno;
    private Integer equipeId;

    public TrechoRodovia(int km, double alturaVegetacao, String tipoTerreno) {
        this(km, alturaVegetacao, tipoTerreno, null);
    }

    public TrechoRodovia(int km, double alturaVegetacao, String tipoTerreno, Integer equipeId) {
        if (km < 0) throw new IllegalArgumentException("km não pode ser negativo");
        if (alturaVegetacao < 0) throw new IllegalArgumentException("altura da vegetação não pode ser negativa");
        this.km = km;
        this.alturaVegetacao = alturaVegetacao;
        this.tipoTerreno = Objects.requireNonNull(tipoTerreno, "tipoTerreno não pode ser nulo");
        this.equipeId = equipeId;
    }

    /** Crescimento diário baseado no tipo de terreno. */
    public double calcularCrescimentoDiario() {
        if (tipoTerreno.equalsIgnoreCase("umido")) {
            return 5.0;
        }
        return 2.0;
    }

    /** Atualiza a altura da vegetação. */
    public void atualizarVegetacao() {
        alturaVegetacao += calcularCrescimentoDiario();
    }

    public double getAlturaVegetacao() {
        return alturaVegetacao;
    }

    public int getKm() {
        return km;
    }

    public String getTipoTerreno() {
        return tipoTerreno;
    }

    public Integer getEquipeId() {
        return equipeId;
    }

    public void setEquipeId(Integer equipeId) {
        this.equipeId = equipeId;
    }

    @Override
    public String toString() {
        return "KM " + km + " - altura=" + alturaVegetacao + " cm (" + tipoTerreno + ")";
    }
}
