public class Diagnostico extends OrdenServicio {
    
    public Diagnostico(String codigo, String nombreCliente, String direccion, double horasEstimadas, Zona zona) {
        super(codigo, nombreCliente, direccion, horasEstimadas, zona);
        
        if (horasEstimadas > 1.5) {
            throw new IllegalArgumentException("El tiempo estimado para un diagnóstico no puede superar 1.5 horas.");
        } 
    }

    @Override 
     public double calcularCosto() {
        if (getZona() == Zona.METROPOLITANA) {
            return 150.00;
        } else {
            return 225.00;
        }
    }

    @Override
    public int calcularTiempoAtencion() {
        if (getZona() == Zona.METROPOLITANA) {
            return 1;
        } else {
            return 2;
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
        return "Diagnóstico";
    }
}
