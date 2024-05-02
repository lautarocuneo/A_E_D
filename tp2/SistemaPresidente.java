package aed;

import aed.SistemaCNE.VotosPartido;

public class SistemaPresidente {
    private int cantPartidos;

    private int[] votosPorPartido;
    private int votosTotales;

    private int idPrimero;  //idPartido
    private int idSegundo;  //idPartido

    public SistemaPresidente(int cantidadPartidos) {

        cantPartidos = cantidadPartidos; // O(1)
        idPrimero = 0; // O(1)
        idSegundo = 0; // O(1)
        votosTotales = 0; // O(1) 
        votosPorPartido = new int[cantidadPartidos]; // O(P)

    } // O(P)

    public void registrarVotos(VotosPartido[] votos){

        for(int partido = 0; partido < cantPartidos; partido++){ // itera P veces // O(1) por chequear condición
            // sumamos votos al partido
            votosPorPartido[partido] += votos[partido].votosPresidente(); // O(1)
            //sumamos a votos totales
            votosTotales += votos[partido].votosPresidente(); // O(1)
        } // complejidad del bucle : // O(P)

        actualizarPrimeroYSegundo(); // O(P)

    } // O(P)

    private void actualizarPrimeroYSegundo(){

        // búsqueda lineal comparando con primero y segundo
        idPrimero = 0; // O(1)
        idSegundo = 1; // O(1)
        for(int p = 1; p < cantPartidos - 1; p++){ // itera P veces
            if (votosPorPartido[p] > votosPorPartido[idPrimero]){ // O(1)
                idSegundo = idPrimero; // O(1)
                idPrimero = p; // O(1)
            } else if (votosPorPartido[p]> votosPorPartido[idSegundo]){ // O(1)
                idSegundo = p; // O(1)
            }
        }

    } // complejidad total : O(P)

    public int votosDePartido(int idPartido) {

        return votosPorPartido[idPartido]; // O(1), por acceder a una posicion de un array

    } // complejidad total : O(1)

    public boolean hayBallotage() {

        float porcentajePrimero = porcentajeVotos(idPrimero); // O(1)
        float porcentajeSegundo = porcentajeVotos(idSegundo); // O(1)

        boolean primeroMayorA45 = porcentajePrimero >= 45; // O(1)
        boolean primeroMayorA40 = porcentajePrimero >= 40; // O(1)
        boolean diferenciaMayorA10 = porcentajePrimero - porcentajeSegundo >= 10; // O(1)
        
        return !(primeroMayorA45 || (primeroMayorA40 && diferenciaMayorA10)); // O(1)

    } // complejidad total : O(1)

    private float porcentajeVotos(int idPartido){ 

        return ((float)votosPorPartido[idPartido] / votosTotales) * 100; // O(1), por operaciones basicas y accesos a arrays

    }  // complejidad total : O(1)
}