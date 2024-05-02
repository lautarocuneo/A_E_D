package aed;

import java.util.Scanner;
import java.io.PrintStream;

class Archivos {
    float[] leerVector(Scanner entrada, int largo) {

        //creo un arreglo de floats
        float[] arreglo = new float[largo];

        //ingreso tantos elementos al arreglo como largo le haya pasado por parametro.

        
        for (int i = 0; i < largo; i++) {
            arreglo[i] = entrada.nextFloat();
        }

        return arreglo;
    }

    float[][] leerMatriz(Scanner entrada, int filas, int columnas) {

        float[][] matriz = new float[filas][columnas];

        
        for (int i = 0; i < filas; i++) {

            for (int j = 0; j < columnas; j++) {
                matriz[i][j] = entrada.nextFloat();
            }
           
        }

        return matriz;
    }


    //la idea es que luego de cada iteracion hay 1 espacio menos de cada lado.
    //y para los asteriscos va saltando de impar en impar.
    void imprimirPiramide(PrintStream salida, int alto) {
        for (int i = 1; i <= alto; i++) {
            int espacios = alto - i; // cantidad de espacios antes de los asteriscos
            int asteriscos = 2 * i - 1; // cantidad de asteriscos en la fila.

            // imprimo espacios de la izquierda
            for (int j = 0; j < espacios; j++) {
                salida.print(" ");
            }

            // imprimo asteriscos
            for (int j = 0; j < asteriscos; j++) {
                salida.print("*");
            }

            // imprimo espacios de la derecha
            for (int j = 0; j < espacios; j++) {
                salida.print(" ");
            }

            // cambiod e linea
            salida.println();
        }
    }
    
}
