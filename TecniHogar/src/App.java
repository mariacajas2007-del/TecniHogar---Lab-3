import java.util.Scanner;

public class App {

    private static final Scanner scanner =
            new Scanner(System.in);

    public static void main(String[] args) {

        SistemaTecniHogar sistema =
                new SistemaTecniHogar();

        cargarDatosIniciales(sistema);
        mostrarMenu(sistema);

        scanner.close();
    }

    private static void mostrarMenu(
            SistemaTecniHogar sistema) {

        int opcion;

        do {
            System.out.println("\n===== TecniHogar =====");
            System.out.println("1. Cotizar y registrar orden");
            System.out.println("2. Registrar cuadrilla");
            System.out.println("3. Crear jornada");
            System.out.println("4. Asignar orden a jornada");
            System.out.println("5. Retirar orden de jornada");
            System.out.println("6. Iniciar jornada");
            System.out.println("7. Registrar resultado");
            System.out.println("8. Consultar órdenes");
            System.out.println("9. Consultar cuadrillas");
            System.out.println("10. Reporte de órdenes");
            System.out.println("11. Reporte de ingresos");
            System.out.println("12. Jornadas no cerradas");
            System.out.println("0. Salir");

            opcion = leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1:
                        cotizarYRegistrar(sistema);
                        break;

                    case 2:
                        registrarCuadrilla(sistema);
                        break;

                    case 3:
                        crearJornada(sistema);
                        break;

                    case 4:
                        asignarOrden(sistema);
                        break;

                    case 5:
                        retirarOrden(sistema);
                        break;

                    case 6:
                        iniciarJornada(sistema);
                        break;

                    case 7:
                        registrarResultado(sistema);
                        break;

                    case 8:
                        sistema.mostrarOrdenes();
                        break;

                    case 9:
                        sistema.mostrarCuadrillas();
                        break;

                    case 10:
                        sistema.mostrarReporteOrdenes();
                        break;

                    case 11:
                        sistema.mostrarReporteIngresos();
                        break;

                    case 12:
                        sistema.mostrarJornadasAbiertas();
                        break;

                    case 0:
                        System.out.println(
                                "Programa finalizado.");
                        break;

                    default:
                        System.out.println(
                                "Opción no válida.");
                }

            } catch (IllegalArgumentException
                    | IllegalStateException e) {

                System.out.println(
                        "Error: " + e.getMessage());
            }

        } while (opcion != 0);
    }

    private static void cotizarYRegistrar(
            SistemaTecniHogar sistema) {

        System.out.println("\n--- Tipo De Servicio ---");
        System.out.println("1. Diagnóstico");
        System.out.println("2. Instalación");
        System.out.println(
                "3. Instalación eléctrica certificada");
        System.out.println(
                "4. Mantenimiento de aire acondicionado");

        int tipo = leerEntero(
                "Seleccione la categoría: ");

        if (tipo < 1 || tipo > 4) {
            throw new IllegalArgumentException(
                    "La categoría seleccionada no es válida.");
        }

        String codigo = leerTexto(
                "Código de la orden: ");

        String nombre = leerTexto(
                "Nombre del cliente: ");

        String direccion = leerTexto(
                "Dirección: ");

        double horas = leerDouble(
                "Horas estimadas: ");

        Zona zona = leerZona();

        OrdenServicio orden;

        switch (tipo) {
            case 1:
                orden = new Diagnostico( codigo, nombre, direccion, horas, zona);
                break;

            case 2:
                int puntos = leerEntero("Cantidad de puntos: ");

                orden = new Instalacion(codigo, nombre, direccion, horas, zona, puntos);
                break;

            case 3:
                int puntosCertificados = leerEntero( "Cantidad de puntos: ");

                double materiales = leerDouble("Valor de los materiales: Q");

                orden =
                        new InstalacionElectricaCertificada(codigo, nombre, direccion, horas, zona, puntosCertificados, materiales);
                break;

            case 4:
                int capacidadBTU = leerEntero(
                        "Capacidad del equipo en BTU: ");

                orden =
                        new MantenimientoAireAcondicionado(codigo, nombre,direccion, horas, zona, capacidadBTU);
                break;

            default:
                return;
        }

        sistema.cotizarOrden(orden);

        String respuesta = leerTexto(
                "¿El cliente acepta la cotización? (S/N): ");

        if (respuesta.equalsIgnoreCase("S")) {
            sistema.registrarOrden(orden);

            System.out.println(
                    "Orden registrada correctamente.");
        } else {
            System.out.println(
                    "La cotización no fue registrada.");
        }
    }

    private static void registrarCuadrilla(
            SistemaTecniHogar sistema) {

        String codigo = leerTexto(
                "Código de la cuadrilla: ");

        double capacidad = leerDouble(
                "Capacidad máxima de horas: ");

        String respuesta = leerTexto(
                "¿Está certificada en refrigeración? (S/N): ");

        boolean certificada =
                respuesta.equalsIgnoreCase("S");

        Cuadrilla cuadrilla = new Cuadrilla(
                codigo,
                capacidad,
                certificada);

        sistema.registrarCuadrilla(cuadrilla);

        System.out.println(
                "Cuadrilla registrada correctamente.");
    }

    private static void crearJornada(
            SistemaTecniHogar sistema) {

        String codigoJornada = leerTexto(
                "Código de la jornada: ");

        String codigoCuadrilla = leerTexto(
                "Código de la cuadrilla: ");

        Cuadrilla cuadrilla =
                sistema.buscarCuadrilla(codigoCuadrilla);

        if (cuadrilla == null) {
            throw new IllegalArgumentException(
                    "La cuadrilla no existe.");
        }

        sistema.crearJornada(
                codigoJornada,
                cuadrilla);

        System.out.println(
                "Jornada creada correctamente.");
    }

    private static void asignarOrden(
            SistemaTecniHogar sistema) {

        String codigoJornada = leerTexto(
                "Código de la jornada: ");

        Jornada jornada =
                sistema.buscarJornada(codigoJornada);

        if (jornada == null) {
            throw new IllegalArgumentException(
                    "La jornada no existe.");
        }

        String codigoOrden = leerTexto(
                "Código de la orden: ");

        OrdenServicio orden =
                sistema.buscarOrden(codigoOrden);

        if (orden == null) {
            throw new IllegalArgumentException(
                    "La orden no existe.");
        }

        jornada.asignarOrden(orden);

        System.out.println(
                "Orden asignada correctamente.");
    }

    private static void retirarOrden(
            SistemaTecniHogar sistema) {

        String codigoJornada = leerTexto(
                "Código de la jornada: ");

        Jornada jornada =
                sistema.buscarJornada(codigoJornada);

        if (jornada == null) {
            throw new IllegalArgumentException(
                    "La jornada no existe.");
        }

        String codigoOrden = leerTexto(
                "Código de la orden: ");

        OrdenServicio orden =
                sistema.buscarOrden(codigoOrden);

        if (orden == null) {
            throw new IllegalArgumentException(
                    "La orden no existe.");
        }

        jornada.retirarOrden(orden);

        System.out.println(
                "Orden retirada correctamente.");
    }

    private static void iniciarJornada(
            SistemaTecniHogar sistema) {

        String codigo = leerTexto(
                "Código de la jornada: ");

        Jornada jornada =
                sistema.buscarJornada(codigo);

        if (jornada == null) {
            throw new IllegalArgumentException(
                    "La jornada no existe.");
        }

        jornada.iniciar();

        System.out.println(
                "Jornada iniciada correctamente.");
    }

    private static void registrarResultado(
            SistemaTecniHogar sistema) {

        String codigoJornada = leerTexto(
                "Código de la jornada: ");

        Jornada jornada =
                sistema.buscarJornada(codigoJornada);

        if (jornada == null) {
            throw new IllegalArgumentException(
                    "La jornada no existe.");
        }

        String codigoOrden = leerTexto(
                "Código de la orden: ");

        OrdenServicio orden =
                sistema.buscarOrden(codigoOrden);

        if (orden == null) {
            throw new IllegalArgumentException(
                    "La orden no existe.");
        }

        System.out.println("1. Visita completada");
        System.out.println("2. Visita fallida");

        int opcion = leerEntero(
                "Seleccione el resultado: ");

        ResultadoVisita resultado;

        if (opcion == 1) {
            resultado = ResultadoVisita.COMPLETADA;
        } else if (opcion == 2) {
            resultado = ResultadoVisita.FALLIDA;
        } else {
            throw new IllegalArgumentException(
                    "El resultado no es válido.");
        }

        jornada.registrarResultado(
                orden,
                resultado);

        System.out.println(
                "Resultado registrado correctamente.");

        System.out.println(
                "Nuevo estado de la orden: "
                + orden.getEstado());

        if (jornada.estaCerrada()) {
            System.out.println(
                    "La jornada ha quedado cerrada.");
        }
    }

    private static Zona leerZona() {

        System.out.println("1. Metropolitana");
        System.out.println("2. Periférica");

        int opcion = leerEntero(
                "Seleccione la zona: ");

        if (opcion == 1) {
            return Zona.METROPOLITANA;
        }

        if (opcion == 2) {
            return Zona.PERIFERICA;
        }

        throw new IllegalArgumentException(
                "La zona seleccionada no es válida.");
    }

    private static String leerTexto(String mensaje) {

        System.out.print(mensaje);

        String texto =
                scanner.nextLine().trim();

        if (texto.isEmpty()) {
            throw new IllegalArgumentException(
                    "El texto no puede estar vacío.");
        }

        return texto;
    }

    private static int leerEntero(String mensaje) {

        while (true) {
            try {
                System.out.print(mensaje);

                return Integer.parseInt(
                        scanner.nextLine().trim());

            } catch (NumberFormatException e) {
                System.out.println(
                        "Ingrese un número entero válido.");
            }
        }
    }

    private static double leerDouble(String mensaje) {

        while (true) {
            try {
                System.out.print(mensaje);

                String entrada =
                        scanner.nextLine()
                                .trim()
                                .replace(",", ".");

                return Double.parseDouble(entrada);

            } catch (NumberFormatException e) {
                System.out.println(
                        "Ingrese un número válido.");
            }
        }
    }

    private static void cargarDatosIniciales(
            SistemaTecniHogar sistema) {

        sistema.agregarOrdenInicial(
                new Diagnostico("D001", "Ana López", "Zona 1", 1.0, Zona.METROPOLITANA));

        sistema.agregarOrdenInicial(
                new Diagnostico("D002", "Carlos Pérez", "Villa Nueva", 1.5, Zona.PERIFERICA));

        sistema.agregarOrdenInicial(
                new Instalacion("I001","María García","Zona 10",2.5,Zona.METROPOLITANA,5));

        sistema.agregarOrdenInicial(
                new Instalacion("I002","Luis Morales","Mixco",4.0,Zona.PERIFERICA,3));

        sistema.agregarOrdenInicial(
                new InstalacionElectricaCertificada("EC001","Sofía Ramírez","Zona 14",3.0,Zona.METROPOLITANA,4,2000.00));

        sistema.agregarOrdenInicial(
                new InstalacionElectricaCertificada("EC002","José Hernández","Amatitlán",4.0,Zona.PERIFERICA,6,3500.00));

        sistema.agregarOrdenInicial(
                new MantenimientoAireAcondicionado("MA001","Laura Castillo","Zona 9",2.0,Zona.METROPOLITANA,12000));

        sistema.agregarOrdenInicial(
                new MantenimientoAireAcondicionado("MA002","Pedro Gómez","Zona 15",3.0,Zona.METROPOLITANA,24000));

        sistema.registrarCuadrilla(
                new Cuadrilla("C001",8.0,true));

        sistema.registrarCuadrilla(
                new Cuadrilla("C002",6.0,false));

        sistema.registrarCuadrilla(
                new Cuadrilla("C003",10.0,true));
    }
}
