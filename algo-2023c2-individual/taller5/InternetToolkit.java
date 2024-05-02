package aed;
import java.util.PriorityQueue;


public class InternetToolkit {
    public InternetToolkit() {
    }

    public Fragment[] tcpReorder(Fragment[] fragments) {
        if(estaOrdenado(fragments)){
            return fragments;
        }
        
        insertionSort(fragments);
        return fragments;
    }
    
    public static void insertionSort(Fragment[] fragments) {
        int n = fragments.length;
    
        for (int i = 1; i < n; ++i) {
            Fragment particular = fragments[i];
            int j = i - 1;
    
            while (j >= 0 && fragments[j].compareTo(particular) > 0) {
                fragments[j + 1] = fragments[j];
                j = j - 1;
            }
            fragments[j + 1] = particular;
        }
    }
    
    public static boolean estaOrdenado(Fragment[] fragments) {
        int n = fragments.length;
    
        if(n == 1 || n == 0){
            return true;
        }
    
        for (int i = 1; i < n; ++i) {
            if (fragments[i].compareTo(fragments[i - 1]) < 0) {
                return false;
            }
        }
    
        return true;
    }

    public Router[] kTopRouters(Router[] routers, int k, int umbral) {
        
        //creo un heap con un comparador custom que ordene los routers por el trafico en forma descendiente.
        PriorityQueue<Router> queue = new PriorityQueue<>((a, b) -> b.compareTo(a));
    
        
        //agrego los routers a la cola si el trafico esta por encima del umbral.
        for (Router router : routers) {
            if (router.getTrafico() > umbral) {
                queue.add(router); //encolar.
            }
        }
    
        
        //creo un array para guardar los k primeros routers.
        Router[] topRouters = new Router[Math.min(k, queue.size())];
    
        
        //saco los primeros k routers de la cola y los agrego al array.
        for (int i = 0; i < topRouters.length; i++) {
            topRouters[i] = queue.poll(); //esto es tipo desencolar. Me devuelve el valor.
        }
    
        return topRouters;
    }

    //uso bucket sort y ordeno cada bucket con bubble sort.

    public static IPv4Address[] sortIPv4(String[] ipv4) {
        // Crear un array de buckets (arreglos)
        IPv4Address[][] buckets = new IPv4Address[256][ipv4.length];
        int[] bucketIndividual = new int[256];
    
        // pongo las direcciones en los buckets según el primer octeto
        for (String addressStr : ipv4) {
            IPv4Address address = new IPv4Address(addressStr);
            int firstOctet = address.getOctet(0);
            buckets[firstOctet][bucketIndividual[firstOctet]++] = address;
        }
    
        // ordeno individualmente cada bucket. 
        int index = 0;
        for (int i = 0; i < 256; i++) {
            OrdenarBucket(buckets[i], bucketIndividual[i]);
            for (int j = 0; j < bucketIndividual[i]; j++) {
                ipv4[index++] = buckets[i][j].toString();
            }
        }
    
        // convierto los elementos a Ipv4address.
        IPv4Address[] sortedIPv4Addresses = new IPv4Address[ipv4.length];
        for (int i = 0; i < ipv4.length; i++) {
            sortedIPv4Addresses[i] = new IPv4Address(ipv4[i]);
        }
    
        return sortedIPv4Addresses;
    }

    // funcion para ordenar los buckets
    private static void OrdenarBucket(IPv4Address[] bucket, int size) {
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                if (CompararIpv4(bucket[j], bucket[j + 1]) > 0) {
                    // Intercambiar elementos si están en el orden incorrecto
                    IPv4Address temp = bucket[j];
                    bucket[j] = bucket[j + 1];
                    bucket[j + 1] = temp;
                }
            }
        }
    }

    //para comparar dos direcciones IPv4
    private static int CompararIpv4(IPv4Address a, IPv4Address b) {
        for (int i = 0; i < 4; i++) {
            int octetComparison = Integer.compare(a.getOctet(i), b.getOctet(i));
            if (octetComparison != 0) {
                return octetComparison;
            }
        }
        return 0;
    }

   
}
