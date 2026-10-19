public class Cuadrilla {
    private String codigo;
    private double capacidadMaxima;
    private boolean certificadaRefrigeracion;

    public Cuadrilla(String codigo, double capacidadMaxima, boolean certificadaRefrigeracion) {
        this.codigo = codigo;
        this.capacidadMaxima = capacidadMaxima;
        this.certificadaRefrigeracion = certificadaRefrigeracion;

        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El código de la cuadrilla no puede estar vacío.");
        }

        if (capacidadMaxima <= 0) {
            throw new IllegalArgumentException(
                    "La capacidad máxima debe ser positiva.");
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public double getCapacidadMaxima() {
        return capacidadMaxima;
    }
    
    public boolean isCertificadaRefrigeracion() {
        return certificadaRefrigeracion;
    }

    @Override
    public String toString() {
        String certificacion;

        if (certificadaRefrigeracion) {
            certificacion = "Sí";
        } else {
            certificacion = "No";
        }

        return "Código: " + codigo
                + ", capacidad máxima: "
                + capacidadMaxima + " horas"
                + ", certificada en refrigeración: "
                + certificacion;
    }
}
