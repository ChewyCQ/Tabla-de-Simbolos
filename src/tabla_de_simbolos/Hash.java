package tabla_de_simbolos;

/**
 *
 * @author DavidCQ
 */
public class Hash {
    
    /**
     * La suma del codigo ASCII del primer y ultimo caracter mod 11
     * @param string    Cadena 
     * @return Entero (int) que representa la posicion a almacenar
     */
    public static int algoritmo1 (String string){
        return Math.round(((float) string.charAt(0) + (float) string.charAt(string.length()-1))% 11);
    }
}
