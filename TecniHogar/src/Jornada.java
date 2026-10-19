import java.util.ArrayList;
import java.util.List;
public class Jornada {
    private String codigo;
    private Cuadrilla cuadrilla;
    private List<OrdenServicio> ordenes;
    private EstadoJornada estado;
    private int resultadosRegistrados;

    public Jornada(String codigo, Cuadrilla cuadrilla) {

        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El código de la jornada no puede estar vacío.");
        }

        if (cuadrilla == null) {
            throw new IllegalArgumentException(
                    "La jornada debe tener una cuadrilla.");
        }

        this.codigo = codigo;
        this.cuadrilla = cuadrilla;
        this.ordenes = new ArrayList<>();
        this.estado = EstadoJornada.EN_PREPARACION;
        this.resultadosRegistrados = 0; 
    }

     public void asignarOrden(OrdenServicio orden) {

        if (estado != EstadoJornada.EN_PREPARACION) {
            throw new IllegalStateException(
                    "Solo se pueden asignar órdenes "
                    + "a una jornada en preparación.");
        }

        if (orden == null) {
            throw new IllegalArgumentException(
                    "La orden no puede ser nula.");
        }

        if (orden.getEstado() != EstadoOrden.PENDIENTE) {
            throw new IllegalStateException(
                    "La orden debe estar pendiente.");
        }

        if (ordenes.contains(orden)) {
            throw new IllegalStateException(
                    "La orden ya está asignada a esta jornada.");
        }

        if (!orden.esCompatibleCon(cuadrilla)) {
            throw new IllegalStateException(
                    "La orden no es compatible con la cuadrilla.");
        }

        double nuevasHoras = calcularHorasAsignadas()
                + orden.getHorasEstimadas();

        if (nuevasHoras > cuadrilla.getCapacidadMaxima()) {
            throw new IllegalStateException(
                    "La orden supera la capacidad disponible.");
        }

        orden.asignar();
        ordenes.add(orden);
    }

    public void retirarOrden(OrdenServicio orden) {

    if (estado != EstadoJornada.EN_PREPARACION) {
        throw new IllegalStateException(
                "Solo se pueden retirar órdenes "
                + "de una jornada en preparación.");
    }

    if (orden == null || !ordenes.contains(orden)) {
        throw new IllegalStateException(
                "La orden no pertenece a esta jornada.");
    }

    orden.retirarAsignacion();
    ordenes.remove(orden);
}

public void iniciar() {

    if (estado != EstadoJornada.EN_PREPARACION) {
        throw new IllegalStateException(
                "La jornada ya fue iniciada o cerrada.");
    }

    if (ordenes.isEmpty()) {
        throw new IllegalStateException(
                "No se puede iniciar una jornada vacía.");
    }

    for (OrdenServicio orden : ordenes) {
        orden.iniciarVisita();
    }

    estado = EstadoJornada.EN_CURSO;
}

public void registrarResultado(
        OrdenServicio orden,
        ResultadoVisita resultado) {

    if (estado != EstadoJornada.EN_CURSO) {
        throw new IllegalStateException(
                "La jornada debe estar en curso.");
    }

    if (orden == null || !ordenes.contains(orden)) {
        throw new IllegalStateException(
                "La orden no pertenece a esta jornada.");
    }

    if (orden.getEstado() != EstadoOrden.EN_ATENCION) {
        throw new IllegalStateException(
                "La orden ya tiene un resultado registrado.");
    }

    orden.registrarResultado(resultado);
    resultadosRegistrados++;

    if (resultadosRegistrados == ordenes.size()) {
        estado = EstadoJornada.CERRADA;
    }
}

public double calcularHorasAsignadas() {
    double totalHoras = 0;

    for (OrdenServicio orden : ordenes) {
        totalHoras += orden.getHorasEstimadas();
    }

    return totalHoras;
}

public double calcularCapacidadDisponible() {
    return cuadrilla.getCapacidadMaxima()
            - calcularHorasAsignadas();
}

public boolean estaCerrada() {
    return estado == EstadoJornada.CERRADA;
}

public String getCodigo() {
    return codigo;
}

public Cuadrilla getCuadrilla() {
    return cuadrilla;
}

public List<OrdenServicio> getOrdenes() {
    return new ArrayList<>(ordenes);
}

public EstadoJornada getEstado() {
    return estado;
}

public int getResultadosRegistrados() {
    return resultadosRegistrados;
}

@Override
public String toString() {
    return "Jornada: " + codigo
            + ", cuadrilla: " + cuadrilla.getCodigo()
            + ", estado: " + estado
            + ", horas asignadas: "
            + calcularHorasAsignadas()
            + ", capacidad disponible: "
            + calcularCapacidadDisponible();
}

    
}
