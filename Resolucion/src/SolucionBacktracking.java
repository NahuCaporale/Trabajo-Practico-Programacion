import java.util.*;

public class SolucionBacktracking {


    Fabrica fabrica;
    int contadorEstados;
    ExpresarSolucionBacktracking solucion;

    public SolucionBacktracking(Fabrica fabrica) {
        this.fabrica = fabrica;
        this.solucion = null;
        contadorEstados = 0;
    }

    //cada maquina produce M piezas, no se puede detener una maquina una vez empezo. Solo 1 a la vez puede funcionar
    //se puede usar la misma maquina
    public Solucion generarSolucionBacktracking(int cantPiezas) {
        List<Maquina> maquinas = fabrica.getMaquinas();
        contadorEstados = 0;
        backtracking(maquinas, new ArrayList<>(), 0, cantPiezas, 0);
        return this.solucion;

    }
    /*
     * <<Breve explicación de la estrategia de resolución. Por ejemplo:
     * -"El árbol de exploracion se genera recursivamente partiendo de un estado vacío, en cada nivel del árbol
     *  se explora la agregacion de una nueva máquina. Para cualquier solución parcial, el algoritmo intenta
     *  la adicion de cada una de las máquinas disponibles (incluso algunas ya utilizadas),así se van creando ramas
     *  que reflejan cada una de las soluciones posibles de las cuales vamos a obtener una sola solucion.
     * - Un estado final es aquella suma parcial de piezas producidas en cada llamado recursivo
     *    es igual a la cantidad total que se quiere producir.
     *  El estado solucion es aquel que cumple con el estado final y ademas utiliza menos cantidad de maquinas.
     * - Una poda que implementamos es cuando el parcial de piezas que se viene produciendo se pasa de la cantidad total que se
     * producir(no queremso producir mas de lo que necesitamos).
     * -Otra poda que implementamos es la de checkear en cada iteracion si la cantidad de el arreglo en cada llamado recursivo
     * es mayor a la mejor solucion que tenemos, siempre y cuando tengamos ya una solucion.
     */
    private void backtracking(List<Maquina> maquinas, List<String> solucionParcial, int parcialCompletado, int total, int puestas) {
        // Caso base: encontramos una solución.
        if (parcialCompletado == total) {
            if (solucion == null) {
                solucion = new ExpresarSolucionBacktracking();// no funcionaba sin hacer new de solucion.
                solucion.copiar(solucionParcial, parcialCompletado, puestas, contadorEstados);
            } else if (solucionParcial.size() < solucion.getMaquinas().size()) {
                solucion.copiar(solucionParcial, parcialCompletado, puestas, contadorEstados);
            }
        }

        if (solucion != null && solucionParcial.size() >= solucion.getMaquinas().size()) {
            return;
        }

        // Poda: si nos pasamos de piezas.
        if (parcialCompletado > total) {
            return;
        }


        Iterator<Maquina> totalMaquinas = maquinas.iterator();
        while (totalMaquinas.hasNext()) {

            Maquina actual = totalMaquinas.next();

            asignarMaquina(actual, actual.getMaxPiezas());
            contadorEstados++;
            parcialCompletado += actual.getMaxPiezas();
            puestas += 1;
            solucionParcial.add(actual.getMaquinaId());

            backtracking(maquinas, solucionParcial, parcialCompletado, total, puestas);

            // Deshacer cambios
            parcialCompletado -= actual.getMaxPiezas();
            puestas -= 1;
            desasignarMaquina(actual);
            solucionParcial.remove(solucionParcial.size() - 1); // Siempre el último

        }

    }

    private void desasignarMaquina(Maquina actual) {
        actual.desasignarPiezas();
        actual.desactivarMaquina();
    }

    public void asignarMaquina(Maquina actual, int producido) {
        actual.asignarPiezas(producido);
        actual.activarMaquina();
    }
}




