import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SolucionPorGreedy {
    ExpresarSolucionGreedy solucion;
    int candidatosCon;
    Fabrica fabrica;


    public SolucionPorGreedy(Fabrica fabrica) {
        this.fabrica = fabrica;
        this.candidatosCon = 0;
        this.solucion = null;
    }
    /*
     * Los candidatos de esta solucion por greedy van a ser todas las maquinas disponibles,
     * - Ordenamos por la mayor cantidad de piezas que puede producir cada maquina en la fabrica.
     * En cada iteracion tomamos la primer maquina(debido a que ya estan ordenadas), y probamos su factibilidad en la solucion
     * que veniamos generando. Si agregando la cantidad que produce esta maquina al parcial de piezas producidas nos pasamos
     * quitamos la maquina de los candidatos. Caso contrario agregamos la maquina y volvemos a probar con la primera
     * de la lista.
     * - Si no encuentra solucion posible retorna null.
     */
    public Solucion generarSolucionGreedy(int piezas) {

        List<Maquina> candidatos = fabrica.getMaquinas();
        Collections.sort(candidatos);
        int cantidadProducida = 0;
        List<String> solucionP = new ArrayList<>();
        int i = 0;
        int puestas = 0;
        while (i < candidatos.size() && !esSolucion(piezas, cantidadProducida) && piezas >= 0) {
            Maquina c = candidatos.get(0);
            if (c != null) {
                candidatosCon++;
                if (Factibilidad(c, piezas, cantidadProducida)) {
                    solucionP.add(c.getMaquinaId());
                    cantidadProducida += c.getMaxPiezas();
                    puestas++;
                } else {
                    candidatos.remove(c);
                }
            }


        }
        if (esSolucion(piezas, cantidadProducida)) {
            solucion = new ExpresarSolucionGreedy();
            solucion.copiar(solucionP, cantidadProducida, puestas, candidatosCon);
            return solucion;
        }
        return null;
    }

    private boolean Factibilidad(Maquina c, int piezas, int cant) {
        return cant + c.maxPiezas <= piezas;
    }

    private boolean esSolucion(int piezas, int cantidadProducida) {
        return piezas == cantidadProducida;
    }



/*Seria mas recomendable usar esta version?, es mucho mas eficiente a la hora de descartar el candidato

public Solucion generarSolucionGreedy(int piezas) {

        List<Maquina> candidatos = fabrica.getMaquinas();
        Collections.sort(candidatos);
        int cantidadProducida = 0;
        List<String> solucionP = new ArrayList<>();
        int i = 0;
        int puestas = 0;
        while (i < candidatos.size() && !esSolucion(piezas, cantidadProducida) && piezas >= 0) {
            Maquina c = seleccionarCandidato(candidatos, i);
            if (c != null) {
                candidatosCon++;
                if (Factibilidad(c, piezas, cantidadProducida)) {
                    solucionP.add(c.getMaquinaId());
                    cantidadProducida += c.getMaxPiezas();
                    puestas++;
                    i = 0;
                } else {
                    i++;
                }
            }


        }
        if (esSolucion(piezas, cantidadProducida)) {
            solucion = new ExpresarSolucionGreedy();
            solucion.copiar(solucionP, cantidadProducida, puestas, candidatosCon);
            return solucion;
        }
        return null;
    }
private Maquina seleccionarCandidato(List<Maquina> candidatos, int indice) {
        if (indice < candidatos.size()) {
            return candidatos.get(indice);
        }
        return null;
 */
}
