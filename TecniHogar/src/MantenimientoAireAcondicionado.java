public class MantenimientoAireAcondicionado extends OrdenServicio {
    private int capacidadBTU;

    public MantenimientoAireAcondicionado(String codigo, String nombreCliente, String direccion, double horasEstimadas, Zona zona, int capacidadBTU) {  
        super(codigo, nombreCliente, direccion, horasEstimadas, zona);
        if (capacidadBTU <= 0) {
            throw new IllegalArgumentException("La capacidad en BTU debe ser mayor que cero.");
        }
        this.capacidadBTU = capacidadBTU;
    }

    @Override
    public double calcularCosto() {
        double tarifaBase = 100.00;
        double costoHoras = getHorasEstimadas() * 110.00;
        double costoGasRefrigerante = 80.00;

        return tarifaBase + costoHoras + costoGasRefrigerante;
    }

    @Override
    public int calcularTiempoAtencion() {
        return 2;
    }

    @Override
    public int obtenerMaximoVisitas() {
        return 1;
    }

    @Override
    public boolean esCompatibleCon(Cuadrilla cuadrilla) {
        return cuadrilla != null
                && cuadrilla.isCertificadaRefrigeracion();
    }

    @Override
    public String obtenerCategoria() {
        return "Mantenimiento de aire acondicionado";
    }

    public int getCapacidadBTU() {
        return capacidadBTU;
    }
}
