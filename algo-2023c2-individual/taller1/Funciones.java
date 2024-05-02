package aed;

import java.util.Scanner;

class Funciones {
    int cuadrado(int x) {
        // COMPLETAR
        return (x*x);
    }
    
    double distancia(double x, double y) {
        // COMPLETAR
        return Math.sqrt(x*x + y*y);
    }

    boolean esPar(int n) {

        return (n % 2 == 0);
        
    }

    boolean esBisiesto(int n) {
        // COMPLETAR
        return ((n % 100 != 0 && n % 4 == 0) || (n % 400 == 0));
    }

    int factorialIterativo(int n) {
    int i = n; 
    int res = 1;
    while (i > 0) {
        res = i * res;
        i--;
    }
    return res;
}

    int factorialRecursivo(int n) {
        
       if (n >= 1)
            return n * factorialRecursivo(n - 1);
        else
            return 1;
    }

    boolean esPrimo(int n) {
        int i = n;
        int cuentaPrimos = 0;
        while(i >= 1){
            
            if(n % i == 0){
                cuentaPrimos++;
            }
            i--;
        }
        return (cuentaPrimos == 2);
    }

    int sumatoria(int[] numeros) {
    int rango = numeros.length - 1;
    int suma = 0;
    while (rango >= 0) {
        suma += numeros[rango];
        rango--;
    }
    return suma;
}

    int busqueda(int[] numeros, int buscado) {
        for(int i = 0; i < numeros.length; i++){
            if(numeros[i] == buscado){
                return i; 
            }
        }
        return -1; 
    }

    boolean tienePrimo(int[] numeros) {
        // COMPLETAR
       for(int i = 0; i < numeros.length; i++){
            if(esPrimo(numeros[i])){
                return true; 
            }
            
        }
        return false;
    }

    boolean todosPares(int[] numeros) {
        
        for(int i = 0; i < numeros.length; i++){
            if(!esPar(numeros[i])){
                return false; 
            }
            
        }

        return true;
    }

   boolean esPrefijo(String s1, String s2) {
    if (s1.length() > s2.length()) {
        return false;
    }
    
    for(int i = 0; i < s1.length(); i++){
        
        if(s1.charAt(i) != s2.charAt(i)){
            return false; 
        }
    }
    return true;
}

    boolean esSufijo(String s1, String s2) {
        if (s1.length() > s2.length()) {
        return false;
    }
    
    for (int i = 0; i < s1.length(); i++) {
        if (s1.charAt(i) != s2.charAt(s2.length() - s1.length() + i)) {
            return false;
        }
    }
    return true;
    }

}
