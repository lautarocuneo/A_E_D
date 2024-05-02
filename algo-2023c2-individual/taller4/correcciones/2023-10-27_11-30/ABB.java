package aed;

import java.util.*;

// Todos los tipos de datos "Comparables" tienen el método compareTo()
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2
public class ABB<T extends Comparable<T>> implements Conjunto<T> {
    Nodo _raiz;
    int _cardinal;

    private class Nodo {
        T valor;
        Nodo izq;
        Nodo der;
        Nodo padre;

        Nodo(T v) {
            valor = v;
            izq = null;
            der = null;
            padre = null;
            }

    }

    public ABB() {
        _raiz = null;
        _cardinal = 0;
    }

    public int cardinal() {
        return _cardinal;
    }

    public T minimo(){
        Nodo actual = _raiz;
        while(actual.izq != null){
            actual = actual.izq;
        }
        return actual.valor;
    }

    public T maximo(){
        Nodo actual = _raiz;
        while(actual.der != null){
            actual = actual.der;
        }
        return actual.valor;
    }
    //iterativo.
    public void insertar(T elem){
        //me voy guardando el ultimo nodo mayor que el que voy a insertar.
        Nodo actual = _raiz; 
        Nodo ultimoMayor = null;
        Nodo ultimoMenor = null;

        
        if(actual == null){
            _raiz = new Nodo(elem);
            _cardinal ++;
        }

        else{
            ultimoMayor = actual;
            while(actual != null){
            // si el actual es el que quiero enchufar no hago nada
            if(elem.compareTo(actual.valor) == 0){
                _cardinal--;
                break;
            }
            //si el actual es mayor al que quiero enchufar, me lo guardo en ultimoMayor y me muevo a la izquierda.
            else if (elem.compareTo(actual.valor) < 0){
                ultimoMayor = actual;
                actual = actual.izq;
                if(actual == null){
                    Nodo nuevo = new Nodo(elem);
                    nuevo.padre = ultimoMayor;
                    ultimoMayor.izq = nuevo;
                    
                }
            }
            //si el actual es menor al que quiero enchufar me muevo a la derecha
            else if(elem.compareTo(actual.valor) > 0){
                ultimoMenor = actual;
                actual = actual.der;
                if(actual == null){
                    Nodo nuevo = new Nodo(elem);
                    nuevo.padre = ultimoMenor;
                    ultimoMenor.der = nuevo;
                    
                }
                } 
            }
            _cardinal++;
        }
    }
    //lo hago iterativo.
    public boolean pertenece(T elem){
        Nodo actual = _raiz;
        if(actual == null){
            return false;
        }

        else{

            while(actual != null){
            if(elem.compareTo(actual.valor) == 0){
                return true;
            }
            else if (elem.compareTo(actual.valor) < 0){
                actual = actual.izq;
            }
            else if(elem.compareTo(actual.valor) > 0){
                actual = actual.der;
            } 
        }

        return false;

      }

    }
    //terminar a partir de aca.
   
    
    public void eliminar(T elem) {
        Nodo actual = _raiz;
    
        while (actual != null) {
            
    
            if (elem.compareTo(actual.valor) == 0) {
                //YA ENCONTRE EL ELEMENTO AHORA LO PUEDO BORRAR.
    
                if (actual.izq == null && actual.der == null) {
                    // CASO 1 NO TIENE HIJOS
                    if (actual.padre == null) {
                        _raiz = null;
                    } else if (actual.padre.izq == actual) {
                        actual.padre.izq = null;
                    } else {
                        actual.padre.der = null;
                    }
                    }

                // CASO 2 EL HIJO ES IZQUIERDO
                else if (actual.izq != null && actual.der == null) {
                    if (actual.padre == null) {
                        _raiz = actual.izq;
                        _raiz.padre = null; 
                    } else if (actual.padre.izq == actual) {
                        actual.padre.izq = actual.izq;
                        actual.izq.padre = actual.padre; 
                    } else {
                        actual.padre.der = actual.izq;
                        actual.izq.padre = actual.padre; 
                    }
                }

                // CASO 2 EL HIJO ES DERECHO.
                else if (actual.izq == null && actual.der != null) {
                    if (actual.padre == null) {
                        _raiz = actual.der;
                        _raiz.padre = null; 
                    } else if (actual.padre.izq == actual) {
                        actual.padre.izq = actual.der;
                        actual.der.padre = actual.padre; 
                    } else {
                        actual.padre.der = actual.der;
                        actual.der.padre = actual.padre; 
                    }
                }
                 else {
                    // CASO 3 TIENE DOS HIJOS.
                    Nodo sucesor = actual.der;
    
                    //busco al sucesor
                    while (sucesor.izq != null) {
    
                        sucesor = sucesor.izq;
                    }
                    
                //reemplazo el valor del sucesor directamente en el actual, sin reorganizar todos los nodos.
                actual.valor = sucesor.valor;

                // Despues elimino al sucesor y conecto los nodos con los que corresponden.
                if (sucesor.padre.izq == sucesor) {
                    sucesor.padre.izq = sucesor.der;
                    if (sucesor.der != null) {
                        sucesor.der.padre = sucesor.padre;
                    }
                } else {
                    sucesor.padre.der = sucesor.der;
                    if (sucesor.der != null) {
                        sucesor.der.padre = sucesor.padre;
                    }
                }
                    
                }
    
                _cardinal--;
                break;
            } else if (elem.compareTo(actual.valor) < 0) {
                actual = actual.izq;
            } else {
                actual = actual.der;
            }
        }
    }
        

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            toStringRecursivo(_raiz, sb);
            sb.append("}");
            return sb.toString();
        }
        
        private void toStringRecursivo(Nodo nodo, StringBuilder sb) {
            if (nodo == null) {
                return;
            }
        
            if (nodo.izq != null) {
                toStringRecursivo(nodo.izq, sb);
                sb.append(",");
            }
        
            sb.append(nodo.valor);
        
            if (nodo.der != null) {
                sb.append(",");
                toStringRecursivo(nodo.der, sb);
            }
        }

        private class ABB_Iterador implements Iterador<T> {
            private Nodo _actual;
        
            public ABB_Iterador() {
                _actual = _raiz;
                // Buscar el nodo más a la izquierda.
                while (_actual.izq != null) {
                    _actual = _actual.izq;
                }
            }
        
            public boolean haySiguiente() {
                return _actual != null;
            }
        
            public T siguiente() {
                if (_actual == null) {
                    throw new NoSuchElementException("No hay más elementos en el árbol");
                }
        
                
                T valor = _actual.valor;
        
                
                if (_actual.der != null) {
                    _actual = _actual.der;
                    while (_actual.izq != null) {
                        _actual = _actual.izq;
                    }
                } else {
                    
                    while (_actual.padre != null && _actual.padre.der == _actual) {
                        _actual = _actual.padre;
                    }
                    _actual = _actual.padre;
                }
        
                return valor;
            }
        }
        
        public Iterador<T> iterador() {
            return new ABB_Iterador();
        }
}
