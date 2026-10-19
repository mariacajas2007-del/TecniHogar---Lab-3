public class Instalacion extends OrdenServicio {
    private int cantidadPuntos;

    public Instalacion(String codigo, String nombreCliente, String direccion, double horasEstimadas, Zona zona, double materiales) {
        super(codigo, nombreCliente, direccion, horasEstimadas, zona);
        if (materiales <= 0) {
            throw new IllegalArgumentException("La cantidad de puntos a instalar debe ser mayor que cero.");
        }
        this.cantidadPuntos = (int) materiales;
    }

    protected double calcularHorasFacturables() {
        double horasPorPuntos = cantidadPuntos * 0.75;
        double horasMayores = Math.max(
                getHorasEstimadas(), horasPorPuntos);

        return Math.ceil(horasMayores);

    }

    @Override 
    public double calcularCosto() {
         double tarifaBase;

        if (getZona() == Zona.METROPOLITANA) {
            tarifaBase = 100.00;
        } else {
            tarifaBase = 175.00;
        }

        return tarifaBase + (calcularHorasFacturables() * 90.00);
    }

    @Override 
    public int calcularTiempoAtencion() {
        if (getZona() == Zona.METROPOLITANA) {
            return 3;
        } else {
            return 5;
        }
    }

    @Override 
    public int obtenerMaximoVisitas() {
        return 3;
    }

    @Override 
     public boolean esCompatibleCon(Cuadrilla cuadrilla) {
        return cuadrilla != null;
    }

     @Override
    public String obtenerCategoria() {
        return "Instalación";
    }

    public int getCantidadPuntos() {
        return cantidadPuntos;
    }
}