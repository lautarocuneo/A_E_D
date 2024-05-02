package aed;

import java.util.*;

public class ListaEnlazada<T> implements Secuencia<T> {
    private Nodo _primero;
    private Nodo _ultimo;  

    private class Nodo {
        T valor;
        Nodo _siguiente;
        Nodo _anterior;
    }

    public ListaEnlazada() {
        _primero = null;
        _ultimo = null;
    }

    public int longitud() {
        Nodo actual = _primero;
        int longitud = 0;
        while (actual != null) {
            longitud++;
            actual = actual._siguiente;
        }
        return longitud;
    }

    public void agregarAdelante(T elem) {
        Nodo nuevo = new Nodo();
        nuevo.valor = elem;

        if (_primero == null) {
            _primero = nuevo;
            _ultimo = nuevo; 
        } else {
            nuevo._siguiente = _primero;
            _primero._anterior = nuevo;
            _primero = nuevo;
        }
    }

    public void agregarAtras(T elem) {
        Nodo nuevo = new Nodo();
        nuevo.valor = elem;

        if (_primero == null) {
            _primero = nuevo;
            _ultimo = nuevo; 
        } else {
            nuevo._anterior = _ultimo;
            _ultimo._siguiente = nuevo;
            _ultimo = nuevo;
        }
    }

    public T obtener(int i) {
        int contador = 0;
        Nodo actual = _primero;
        
        while (contador < i && actual != null) {
            actual = actual._siguiente;
            contador++;
        }
        
        if (actual != null) {
            return actual.valor;
        } else {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
    }
    
    public void eliminar(int i) {
        Nodo actual = _primero;
    
        if (i == 0) {
            
            _primero = actual._siguiente;
            if (_primero != null) {
                _primero._anterior = null;
            } else {
                
                _ultimo = null;
            }
        } else {
            int contador = 0;
            while (contador < i) {
                actual = actual._siguiente;
                contador++;
            }
    
            actual._anterior._siguiente = actual._siguiente;
    
            if (actual._siguiente != null) {
                actual._siguiente._anterior = actual._anterior;
            } else {
            
                _ultimo = actual._anterior;
            }
        }
    }

    public ListaEnlazada<T> copiar() {
        ListaEnlazada<T> copia = new ListaEnlazada<>();
        Nodo actual = _primero;
        while (actual != null) {
            copia.agregarAtras(actual.valor);
            actual = actual._siguiente;
        }
        copia._ultimo = _ultimo;
        return copia;
    }

    

    public void modificarPosicion(int indice, T elem) {
        int contador = 0;
        Nodo actual = _primero;
        while (actual != null) {
            if (contador == indice) {
                actual.valor = elem;
                break;
            }
            contador++;
            actual = actual._siguiente;
        }
    }
    //terminar
    public ListaEnlazada(ListaEnlazada<T> lista) {
        Nodo original = lista._primero;
        while(original != null) {
            agregarAtras(original.valor);
            original = original._siguiente;
        }
    }
    //terminar
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Nodo actual = _primero;
        while(actual != null){
            sb.append(actual.valor);
            if(actual._siguiente != null){
                sb.append(", ");

            }
            actual = actual._siguiente;
        }
        sb.append("]");
        return sb.toString();
    }
   

    public Iterador<T> iterador() {
        return new ListaIterador();
    }

    private class ListaIterador implements Iterador<T> {
        private Nodo actual;
        

        ListaIterador() {
            actual = _primero;
        }

        public boolean haySiguiente() {
            return actual != null;
        }

        public boolean hayAnterior() {
            if(actual == null){
                return _ultimo != null;
            }

            return actual != null && actual != _primero;
        }

        public T siguiente() {
           
            T valor = actual.valor;
            actual = actual._siguiente;
            return valor;
        }

        public T anterior() {
            
            if (actual == null) {
                actual = _ultimo;
            } else {
                actual = actual._anterior;
            }
            return actual.valor;
        }
    }
}