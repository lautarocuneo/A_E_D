package aed;

import aed.SistemaCNE.VotosPartido;

public class SistemaDiputados {
    private int cantPartidos;

    private int[][] votosPorPartidoPorDistrito;
    private DHondt[] dhondtPorDistrito;

    public SistemaDiputados(int cantidadPartidos, int cantidadDistritos) {

        cantPartidos = cantidadPartidos; // O(1) ; guardamos la cantidad de partidos para usarla más tarde
        dhondtPorDistrito = new DHondt[cantidadDistritos]; // O(D)
        votosPorPartidoPorDistrito = new int[cantidadDistritos][]; // O(D)

    } // complejidad total por sumas de O(D) : O(D)

    public void registrarVotos(VotosPartido[] votos, Distrito distrito){
        
        // si no están inicializados el array de votos y la d'hondt correspondiente al distrito, los inicializamos
        if(votosPorPartidoPorDistrito[distrito.id] == null){ //O(1)
            votosPorPartidoPorDistrito[distrito.id] = new int[cantPartidos]; // O(P)
            dhondtPorDistrito[distrito.id] = new DHondt(votosPorPartidoPorDistrito[distrito.id], distrito.bancas); // O(P)
        }

        // sumamos los votos al array correspondiente al distrito
        for(int partido = 0; partido < cantPartidos; partido++){ // itera P veces // O(1) por chequear condición
            votosPorPartidoPorDistrito[distrito.id][partido] += votos[partido].votosDiputados(); // O(1)
        } // complejidad del bucle : // O(P)

        // al finalizar la suma de votos, actualizamos la d'hondt correspondiente
        dhondtPorDistrito[distrito.id].actualizar(); // O(P), por varias acciones de O(P)

    } // complejidad total por suma de sucesivos O(P) ≡ O(P)  

    public int votosDePartidoEnDistrito(int idPartido, int idDistrito) {

        if(votosPorPartidoPorDistrito[idDistrito] == null){ // O(1)
            return 0;
        } else {
            return votosPorPartidoPorDistrito[idDistrito][idPartido]; // O(1), por acceder a una posicion de una matriz
        }
        
    } // complejidad total : O(1)

    public int[] resultadosDeDistrito(int idDistrito){

        return dhondtPorDistrito[idDistrito].resultados(); // O(Dd * log(P))

    }
}