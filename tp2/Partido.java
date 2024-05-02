package aed;

public class Partido {
    String nombre;

    public Partido(String nombreX) {
        nombre = nombreX; //O(|string|) siendo que el string es como un "array" de chars.
    }
}