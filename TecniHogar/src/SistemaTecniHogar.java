import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SistemaTecniHogar {

    private List<OrdenServicio> ordenes;
    private List<Cuadrilla> cuadrillas;
    private List<Jornada> jornadas;

    public SistemaTecniHogar() {
        ordenes = new ArrayList<>();
        cuadrillas = new ArrayList<>();
        jornadas = new ArrayList<>();
    }

    public void cotizarOrden(OrdenServicio orden) {

        if (orden == null) {
            throw new IllegalArgumentException(
                    "La orden no puede ser nula.");
        }

        System.out.println("\n--- COTIZACIÓN ---");
        System.out.println("Categoría: "
                + orden.obtenerCategoria());

        System.out.printf(
                "Costo: Q%.2f%n",
                orden.calcularCosto());

        System.out.println(
                "Tiempo estimado: "
                + orden.calcularTiempoAtencion()
                + " días hábiles.");

        System.out.println(
                "La cotización no ha sido registrada.");
    }

    public void registrarOrden(OrdenServicio orden) {

        if (orden == null) {
            throw new IllegalArgumentException(
                    "La orden no puede ser nula.");
        }

        if (buscarOrden(orden.getCodigo()) != null) {
            throw new IllegalArgumentException(
                    "Ya existe una orden con ese código.");
        }

        orden.registrarCobroInicial();
        ordenes.add(orden);
    }

    public void agregarOrdenInicial(OrdenServicio orden) {

        if (orden == null) {
            throw new IllegalArgumentException(
                    "La orden no puede ser nula.");
        }

        if (buscarOrden(orden.getCodigo()) != null) {
            throw new IllegalArgumentException(
                    "Ya existe una orden con ese código.");
        }

        // No registra cobro porque es una orden inicial.
        ordenes.add(orden);
    }

    public void registrarCuadrilla(Cuadrilla cuadrilla) {

        if (cuadrilla == null) {
            throw new IllegalArgumentException(
                    "La cuadrilla no puede ser nula.");
        }

        if (buscarCuadrilla(cuadrilla.getCodigo()) != null) {
            throw new IllegalArgumentException(
                    "Ya existe una cuadrilla con ese código.");
        }

        cuadrillas.add(cuadrilla);
    }

    public Jornada crearJornada(
            String codigo,
            Cuadrilla cuadrilla) {

        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El código de la jornada no puede estar vacío.");
        }

        if (buscarJornada(codigo) != null) {
            throw new IllegalArgumentException(
                    "Ya existe una jornada con ese código.");
        }

        if (cuadrilla == null
                || buscarCuadrilla(cuadrilla.getCodigo()) == null) {

            throw new IllegalArgumentException(
                    "La cuadrilla no está registrada.");
        }

        for (Jornada jornada : jornadas) {
            boolean mismaCuadrilla =
                    jornada.getCuadrilla().getCodigo()
                            .equalsIgnoreCase(
                                    cuadrilla.getCodigo());

            if (mismaCuadrilla && !jornada.estaCerrada()) {
                throw new IllegalStateException(
                        "La cuadrilla ya tiene una jornada "
                        + "sin cerrar.");
            }
        }

        Jornada nuevaJornada =
                new Jornada(codigo, cuadrilla);

        jornadas.add(nuevaJornada);

        return nuevaJornada;
    }

    public OrdenServicio buscarOrden(String codigo) {

        if (codigo == null) {
            return null;
        }

        for (OrdenServicio orden : ordenes) {
            if (orden.getCodigo().equalsIgnoreCase(codigo)) {
                return orden;
            }
        }

        return null;
    }

    public Cuadrilla buscarCuadrilla(String codigo) {

        if (codigo == null) {
            return null;
        }

        for (Cuadrilla cuadrilla : cuadrillas) {
            if (cuadrilla.getCodigo()
                    .equalsIgnoreCase(codigo)) {

                return cuadrilla;
            }
        }

        return null;
    }

    public Jornada buscarJornada(String codigo) {

        if (codigo == null) {
            return null;
        }

        for (Jornada jornada : jornadas) {
            if (jornada.getCodigo()
                    .equalsIgnoreCase(codigo)) {

                return jornada;
            }
        }

        return null;
    }

    public void mostrarOrdenes() {

        System.out.println("\n--- ÓRDENES REGISTRADAS ---");

        if (ordenes.isEmpty()) {
            System.out.println(
                    "No existen órdenes registradas.");
            return;
        }

        for (OrdenServicio orden : ordenes) {
            System.out.println(orden);
        }
    }

    public void mostrarCuadrillas() {

        System.out.println("\n--- CUADRILLAS REGISTRADAS ---");

        if (cuadrillas.isEmpty()) {
            System.out.println(
                    "No existen cuadrillas registradas.");
            return;
        }

        for (Cuadrilla cuadrilla : cuadrillas) {
            System.out.println(cuadrilla);
        }
    }

    public void mostrarReporteOrdenes() {

        Map<String, Integer> cantidades =
                new LinkedHashMap<>();

        for (OrdenServicio orden : ordenes) {
            String clave = orden.obtenerCategoria()
                    + " - " + orden.getEstado();

            cantidades.put(
                    clave,
                    cantidades.getOrDefault(clave, 0) + 1);
        }

        System.out.println(
                "\n--- ÓRDENES POR CATEGORÍA Y ESTADO ---");

        if (cantidades.isEmpty()) {
            System.out.println(
                    "No existen órdenes registradas.");
            return;
        }

        for (Map.Entry<String, Integer> dato
                : cantidades.entrySet()) {

            System.out.println(
                    dato.getKey() + ": " + dato.getValue());
        }
    }

    public void mostrarReporteIngresos() {

        double ingresosRegistro = 0;
        double ingresosVisitas = 0;

        Map<String, Double> ingresosCategoria =
                new LinkedHashMap<>();

        for (OrdenServicio orden : ordenes) {

            ingresosRegistro +=
                    orden.getIngresoRegistro();

            ingresosVisitas +=
                    orden.getIngresoVisitas();

            String categoria = orden.obtenerCategoria();

            double ingresoAnterior =
                    ingresosCategoria.getOrDefault(
                            categoria, 0.0);

            ingresosCategoria.put(
                    categoria,
                    ingresoAnterior + orden.getIngresoTotal());
        }

        double ingresoTotal =
                ingresosRegistro + ingresosVisitas;

        System.out.println("\n--- REPORTE DE INGRESOS ---");

        System.out.printf(
                "Ingresos por registro: Q%.2f%n",
                ingresosRegistro);

        System.out.printf(
                "Ingresos por visitas adicionales: Q%.2f%n",
                ingresosVisitas);

        System.out.printf(
                "Ingreso total: Q%.2f%n",
                ingresoTotal);

        System.out.println("\nIngresos por categoría:");

        for (Map.Entry<String, Double> dato
                : ingresosCategoria.entrySet()) {

            System.out.printf(
                    "%s: Q%.2f%n",
                    dato.getKey(),
                    dato.getValue());
        }
    }

    public void mostrarJornadasAbiertas() {

        System.out.println(
                "\n--- JORNADAS NO CERRADAS ---");

        boolean encontrada = false;

        for (Jornada jornada : jornadas) {
            if (!jornada.estaCerrada()) {
                System.out.println(jornada);
                encontrada = true;
            }
        }

        if (!encontrada) {
            System.out.println(
                    "No existen jornadas abiertas.");
        }
    }

    public List<OrdenServicio> getOrdenes() {
        return new ArrayList<>(ordenes);
    }

    public List<Cuadrilla> getCuadrillas() {
        return new ArrayList<>(cuadrillas);
    }

    public List<Jornada> getJornadas() {
        return new ArrayList<>(jornadas);
    }
}
