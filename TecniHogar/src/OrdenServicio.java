public abstract class OrdenServicio {
    private String codigo;
    private String nombreCliente; 
    private String direccion;
    private double horasEstimadas;
    private Zona zona;
    private EstadoOrden estado;
    private int cantidadVisitas;
    private double ingresoRegistro;
    private double ingresoVisitas;

    public OrdenServicio(String codigo, String nombreCliente, String direccion, double horasEstimadas, Zona zona) {
    
     if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El código no puede estar vacío.");
        }

        if (nombreCliente == null || nombreCliente.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre del cliente no puede estar vacío.");
        }

        if (direccion == null || direccion.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "La dirección no puede estar vacía.");
        }

        if (horasEstimadas <= 0) {
            throw new IllegalArgumentException(
                    "Las horas estimadas deben ser positivas.");
        }

        if (zona == null) {
            throw new IllegalArgumentException(
                    "Debe seleccionar una zona.");
        }

        this.codigo = codigo;
        this.nombreCliente = nombreCliente;
        this.direccion = direccion;
        this.horasEstimadas = horasEstimadas;
        this.zona = zona;
        this.estado = EstadoOrden.PENDIENTE;
        this.cantidadVisitas = 0;
        this.ingresoRegistro = 0;
        this.ingresoVisitas = 0;
    }

    public abstract double calcularCosto();
    public abstract int calcularTiempoAtencion();
    public abstract int obtenerMaximoVisitas();
    public abstract boolean esCompatibleCon(Cuadrilla cuadrilla);
    public abstract String obtenerCategoria();
    
    public void registrarCobroInicial () {
        if (ingresoRegistro > 0) {
            throw new IllegalStateException(
                    "El cobro inicial ya fue registrado.");
        }

        ingresoRegistro = calcularCosto();
    }

    public void asignar(){
         if (estado != EstadoOrden.PENDIENTE) {
            throw new IllegalStateException(
                    "La orden debe estar pendiente para asignarla.");
        }

        estado = EstadoOrden.ASIGNADA;
    }

    public void retirarAsignacion () {
        if (estado != EstadoOrden.ASIGNADA) {
            throw new IllegalStateException(
                    "La orden no está asignada.");
        }

        estado = EstadoOrden.PENDIENTE;
    }

    public void iniciarVisita () {
        if (estado != EstadoOrden.ASIGNADA) {
            throw new IllegalStateException(
                    "La orden debe estar asignada.");
        }

        cantidadVisitas++;

        if (cantidadVisitas > 1) {
            ingresoVisitas += 40;
        }

        estado = EstadoOrden.EN_ATENCION;
    }


    public void registrarResultado (ResultadoVisita resultado) {
         if (estado != EstadoOrden.EN_ATENCION) {
            throw new IllegalStateException(
                    "La orden debe estar en atención.");
        }

        if (resultado == null) {
            throw new IllegalArgumentException(
                    "Debe indicar el resultado de la visita.");
        }

        if (resultado == ResultadoVisita.COMPLETADA) {
            estado = EstadoOrden.COMPLETADA;
        } else {
            if (cantidadVisitas >= obtenerMaximoVisitas()) {
                estado = EstadoOrden.CANCELADA;
            } else {
                estado = EstadoOrden.PENDIENTE;
            }
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public double getHorasEstimadas() {
        return horasEstimadas;
    }

    public Zona getZona() {
        return zona;
    }

    public EstadoOrden getEstado() {
        return estado;
    }

    public int getCantidadVisitas() {
        return cantidadVisitas;
    }

    public double getIngresoRegistro() {
        return ingresoRegistro;
    }

    public double getIngresoVisitas() {
        return ingresoVisitas;
    }

    public double getIngresoTotal() {
        return ingresoRegistro + ingresoVisitas;
    }

    @Override 
    public String toString() {
        return "OrdenSerivicio{" +
                "codigo='" + codigo + '\'' +
                ", nombreCliente='" + nombreCliente + '\'' +
                ", direccion='" + direccion + '\'' +
                ", horasEstimadas=" + horasEstimadas +
                ", zona=" + zona +
                ", estado=" + estado +
                ", cantidadVisitas=" + cantidadVisitas +
                ", ingresoRegistro=" + ingresoRegistro +
                ", ingresoVisitas=" + ingresoVisitas +
                '}';
    }

}