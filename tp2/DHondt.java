package aed;

public class DHondt {

    private int[] refeVotos; // esta refe es para poder usar .actualizar() y .calcularResultados(); no debería modificarse desde esta clase
    private int votosTotales;

    private int cantPartidos; // excluyendo votos en blanco
    private int cantBancas;

    private Nodo[] heap;
    private boolean calculado;
    private int[] resultados;


    private class Nodo {
        int cociente;
        int divididoPor;
        int idPartido;

        Nodo(int votos, int id) {

            cociente = votos;
            divididoPor = 1; // los cocientes de los nodos arrancan siendo divididor por 1 en todos los nodos.
            idPartido = id;

        }
    }

    public DHondt(int[] votos, int cantidadBancas) {

        refeVotos = votos; // O(1); guardamos la refe para poder usar .actualizar() después
        cantBancas = cantidadBancas; // O(1)
        cantPartidos = refeVotos.length-1; // O(1); excluimos los votos en blanco

        heap = null; // O(1)
        resultados = new int[cantPartidos]; // O(1)
        calculado = false; // O(1)

    } // complejidad total : O(1)

    public void actualizar(){
        
        cantPartidos = refeVotos.length-1; // O(1)
        votosTotales = sumatoria(refeVotos); // O(P)
        calculado = false; // O(1)

        // creamos un array auxiliar y copiamos los votos,
        // filtramos partidos que no superan el 3%, cambiando sus votos a 0, y excluimos los votos en blanco 
        int[] votosSobreUmbral = new int[cantPartidos]; // O(P), inicializar un array de tamaño P-1
        for(int id = 0; id < cantPartidos; id++) { // itera P veces // O(1) por chequear condición
            if( porcentajeVotos(id) >= 3 ) { // O(1)
                votosSobreUmbral[id] = refeVotos[id]; // O(1)
            } else {
                votosSobreUmbral[id] = 0; // O(1)
            }
        } // complejidad del bucle : O(P)

        // ya filtrados los partidos que no superan el umbral del 3% y excluidos los votos en blanco
        // procedemos a armar el arreglo de nodos con los valores correspondientes
        heap = new Nodo[cantPartidos]; // O(P), por inicializar array de tamaño P-1
        for (int id = 0; id < cantPartidos; id++) { // itera P veces // O(1) por chequear condición
            heap[id] = new Nodo(votosSobreUmbral[id], id); // O(1)
        } // complejidad del bucle : O(P)

        // heapifeamos, usando algoritmo de Floyd
        for (int i = cantPartidos / 2 - 1; i >= 0; i--) { // itera ((P-1)/2 - 1) veces
            bajar(i);
        } // complejidad del bucle : O(P), por análisis hecho en clase teorica

    } // complejidad total por suma de sucesivos O(P): O(P) 

    public int[] resultados(){
        if(!calculado){ // O(1)
            calcularResultados(); // O(Dd * log(P))
        }
        return resultados; // O(1)
    } // complejidad total : O(Dd * log(P))

    private void calcularResultados(){
        
        for (int i = 0; i < cantBancas; i++) { // itera Dd veces ≡ O(Dd)
            resultados[heap[0].idPartido]++; // O(1)
            heap[0].divididoPor++; //O(1)
            heap[0].cociente = refeVotos[heap[0].idPartido]/heap[0].divididoPor; // O(1)

            bajar(0); // O(log2(P)) ≡ O(log(P))
        }

        calculado = true; // O(1)

    } // complejidad total por realizar Dd veces una funcion que es O(Log(P)): O(Dd * log(P))

    private int sumatoria(int[] array){

        int sum = 0;
        for(int i = 0; i < array.length; i++){
            sum += array[i];
        }
        return sum;

    } // complejidad total : O(n) donde n es la longitud del array pasado por parametro; pero la única vez que usa es un array de tamaño P; por ende, O(P)
    
    private float porcentajeVotos(int idPartido){ // en el distrito correspondiente al d'hondt

        return ((float)refeVotos[idPartido] / votosTotales) * 100;

    } // complejidad total : O(1)
 




    // FUNCIONES del HEAP

    private void bajar(int posicion) {
        int posicionActual = posicion;
        int posicionHijoMayor;
        
        //mientras sea menor que uno de sus hijos intercambiarlo con el mayor de sus hijos
        while(tieneHijoIzquierdo(posicionActual)){
            if (tieneHijoDerecho(posicionActual)) {
                posicionHijoMayor = posicionDelMayor(posicionHijoDerecho(posicionActual), posicionHijoIzquierdo(posicionActual));
            } else {
                posicionHijoMayor = posicionHijoIzquierdo(posicionActual);
            }

            // si el actual es mayor o igual que el hijoMayor, ya está
            if (heap[posicionActual].cociente >= heap[posicionHijoMayor].cociente) {
                return;
            }
            // si no se cumple lo anterior, swapeamos entre el actual y su hijo mayor
            swap(posicionActual, posicionHijoMayor);
            // y seguimos
            posicionActual = posicionHijoMayor;
        } 
    } //realizar esta funcion pertenece a O(log(P)) ("bajar" un nodo a su posicion correspondiente como mucho es la "altura" del heap (que es log(P)))

    private void swap(int posicion1, int posicion2){
        Nodo tmp;
        tmp = heap[posicion1];
        heap[posicion1] = heap[posicion2];
        heap[posicion2] = tmp;
    } // complejidad total : O(1)

    private int posicionHijoIzquierdo(int posicion) {

        return (2 * posicion) + 1;

    } // complejidad total : O(1)
    
    private int posicionHijoDerecho(int posicion){

        return (2 * posicion) + 2;

    } // complejidad total : O(1)

    private boolean tieneHijoIzquierdo(int posicion){

        return posicionHijoIzquierdo(posicion) < cantPartidos;

    } // complejidad total : O(1)
 
    private boolean tieneHijoDerecho(int posicion){

        return posicionHijoDerecho(posicion) < cantPartidos;

    } // complejidad total : O(1)

    private int posicionDelMayor(int posicion1, int posicion2) {
        if (heap[posicion1].cociente >= heap[posicion2].cociente) {
            return posicion1;
        } else {
            return posicion2;
        }
    } // complejidad total : O(1)

}

