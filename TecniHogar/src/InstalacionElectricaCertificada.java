public class InstalacionElectricaCertificada extends Instalacion{
    private double valorMateriales;

    public InstalacionElectricaCertificada(String codigo, String nombreCliente, String direccion, double horasEstimadas, Zona zona, double valorMateriales, double materiales) {
        super(codigo, nombreCliente, direccion, horasEstimadas, zona, materiales);
        if (valorMateriales <= 0) {
            throw new IllegalArgumentException("El valor de los materiales no puede ser negativo.");
        }
        this.valorMateriales = valorMateriales;
    }

    @Override
    public double calcularCosto() {
        double costoInstalacion = super.calcularCosto();
        double costoCertificado = 120.00;
        double costoGarantia = valorMateriales * 0.05;

        return costoInstalacion
                + costoCertificado
                + costoGarantia;
    }

    @Override
    public int calcularTiempoAtencion() {
        return super.calcularTiempoAtencion() + 2;
    }

    @Override
    public int obtenerMaximoVisitas() {
        return 2;
    }

    @Override
    public String obtenerCategoria() {
        return "Instalación eléctrica certificada";
    }

    public double getValorMateriales() {
        return valorMateriales;
    }
}
