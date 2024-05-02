package aed;
import java.util.*;
import aed.*;

public class SistemaCNE {
    public class VotosPartido{
        private int presidente;
        private int diputados;

        VotosPartido(int presidente, int diputados){
            this.presidente = presidente; this.diputados = diputados;
        }
        public int votosPresidente(){
            return presidente;
        }
        public int votosDiputados(){
            return diputados;
        }
    }
    
    private Distrito[] distritos;
    private Partido[] partidos;

    private SistemaPresidente sysPresidente;
    private SistemaDiputados sysDiputados;

    //ANALISIS DE COMPLEJIDAD:
    // - D es cantidad de distritos
    // - P cantidad de partidos politicos (incluyendo Blanco)
    // - Dd Cantidad de bancas de diputados en distrito d.

    public SistemaCNE (String[] nombresDistritos, int[] diputadosPorDistrito, String[] nombresPartidos, int[] ultimasMesasDistritos) {

        // nos guardamos la info de los partidos
        partidos = new Partido[nombresPartidos.length]; // O(P), por inicializar array de tamaño P
        for(int p = 0; p < partidos.length; p++) { // itera P veces
            partidos[p] = new Partido(nombresPartidos[p]); // O(1)
        } // complejidad del bucle: O(P)

        // nos guardamos la info de los distritos
        distritos = new Distrito[nombresDistritos.length]; // O(D), por inicializar array de tamaño D
        for(int d = 0; d < distritos.length; d++) {  // itera D veces
            distritos[d] = new Distrito(d, nombresDistritos[d], diputadosPorDistrito[d], ultimasMesasDistritos[d]); // O(1)
        } // complejidad del bucle: O(D)

        // inicializamos los dos sistemas
        sysPresidente = new SistemaPresidente(partidos.length); // O(P)
        sysDiputados = new SistemaDiputados(partidos.length, distritos.length); // O(D)
        
    } // complejidad total por suma de varios O(D) y O(P) y O(1) ≡ O(P + D).
    
    public String nombrePartido(int idPartido) {

        return partidos[idPartido].nombre; // O(1), por acceder a una posicion de un array

    } // complejidad total : O(1)


    public String nombreDistrito(int idDistrito) {

        return distritos[idDistrito].nombre; // O(1), por acceder a una posicion de un array

    } // complejidad total : O(1)


    public int diputadosEnDisputa(int idDistrito) {

        return distritos[idDistrito].bancas; // O(1), por acceder a una posicion de un array

    } // complejidad total : O(1)


    public String distritoDeMesa(int idMesa) {

        return distritos[idDistritoDeMesa(idMesa)].nombre; // O(log(D)), por idDistritoDeMesa() que es O(log(D))

    } // complejidad total : O(log(D))

     
    public void registrarMesa(int idMesa, VotosPartido[] actaMesa) {

        sysPresidente.registrarVotos(actaMesa); // O(P)
        sysDiputados.registrarVotos(actaMesa, distritos[idDistritoDeMesa(idMesa)]); // O(log(D)), por búsqueda binaria de idDistritoDeMesa + O(P) por registrarVotos()

    } // complejidad total por suma de sucesivos O(P) y el O(log(D)) ≡ O(P + log(D))

    public int votosPresidenciales(int idPartido) {

        return sysPresidente.votosDePartido(idPartido); // O(1), por acceder a una posicion de un array

    } // complejidad total : O(1)



    public int votosDiputados(int idPartido, int idDistrito) {

        return sysDiputados.votosDePartidoEnDistrito(idPartido, idDistrito); // O(1), por acceder a una posicion de un array adentro de un array.


    } // complejidad total : ≡ O(1)



    public int[] resultadosDiputados(int idDistrito) {

        return sysDiputados.resultadosDeDistrito(idDistrito);

    } // complejidad total : O(Dd * log(P))


    public boolean hayBallotage() {
        
        return sysPresidente.hayBallotage();

    } // complejidad total : O(1)

    private int idDistritoDeMesa(int idMesa){

        // busqueda binaria en un array de D elementos ordenado es O(log(D))
        // D / 2^k = 1 ⇒ D = 2^K ⇒ Log2(D) = k ⇒ O(log2(D)) ≡ O(log(D))

        int low = 0; // O(1) 
        int high = distritos.length - 1; // O(1)
        while(low + 1 < high) { // itera log2(D)
            int mid = (low + high)/2; // O(1)
            if (distritos[mid].ultimaMesa <= idMesa ) { // O(1)
                low = mid; // O(1)
            } else {  // idMesa <= listaDistritos[mid]
                high = mid; // O(1)
            }
        } // complejidad del bucle : O(log2(D))

        // Una vez que solo tengo dos distritos:
        int idDistrito = low; // O(1)
        if (idMesa >= (distritos[low].ultimaMesa )) { // O(1)
            idDistrito = high; // O(1)
        }
        
        return idDistrito; // O(1)

    } // complejidad total : O(log2(D)) ≡ O(log(D))
    

}   