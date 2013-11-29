package tabla_de_simbolos;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * 
 * @author DavidCQ
 */
public class Robot {
    int x;
    int y;
    int limiteX = 24;
    int limiteY = 8;
    
    private List<String> listaCoordenadasDadas;
    private List<String> coordenadasTratadas;
    private List<int[]> coordenadasConvertidas;
    private List<Direccion> listaDeMovimientos;
    
    public enum Direccion{
        arriba("Arriba"),
        abajo("Abajo"),
        derecha("Derecha"),
        izquierda("Izquierda");
        
        private String texto;
        
        Direccion(String nombre){
            this.texto = nombre;
        }
        
        public String getTexto(){
            return this.texto;
        }
        
        @Override
        public String toString(){
            return this.getTexto();
        }
    };

    public Robot() {
        this.x = 1;
        this.y = 8;
    }
    
    public void inicializar(){
        this.x = 1;
        this.y = 8;
    }

    public List<int[]> getCoordenadasConvertidas() {
        return coordenadasConvertidas;
    }

    public List<Direccion> getListaDeMovimientos() {
        return listaDeMovimientos;
    }

    public void setListaCoordenadasDadas(List<String> listaCoordenadasDadas) {
        this.listaCoordenadasDadas = listaCoordenadasDadas;        
        coordenadasTratadas = new ArrayList<>(listaCoordenadasDadas.size());
        coordenadasConvertidas = new ArrayList<>(listaCoordenadasDadas.size());
    }
    
    /**
     * Extrae las coordenadas X y Y del texto, regresa falso y fallo con alguna, verdadero si no hubo problema.
     */
    public boolean convertirCoordenadas(){
        String[] aux;
        String x,y;
        for (String cadena : listaCoordenadasDadas){
            try{
                aux = cadena.split(",");
                x = aux[0];
                y = aux[1];
                aux = x.split("\\(");
                x = aux[1];
                aux = y.split("\\)");
                y = aux[0];
                
                coordenadasConvertidas.add(new int[] {Integer.parseInt(x),Integer.parseInt(y)});
                coordenadasTratadas.add(x+","+y);
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Problema con el formato de las coordenadas, debe ser: posicion n(x,y)");
                return false;
            }
        }
        coordenadasConvertidas.remove(0);
        coordenadasTratadas.remove(0);
        return true;
    }
    
    /**
     * Valida las coordenadas, retorna 0 si son validas, retorna el 
     * numero de coordenada invalida en caso de ser invalida.
     * @return 
     */
    public int validarMovimientos(){
        int tmpX = this.x, tmpY = this.y;
        int c = 0;
        
        try {
            for (int[] coord : coordenadasConvertidas){
                //Aumenta el contador de posicion
                ++c;
                
                //Si las posiciones son invalidas
                if (coord[0] > limiteX || coord[1] > limiteY){
                    throw new Exception("Posicion fuera de rango");
                }
                
                if (coord[0] == this.x && coord[1] == this.y){
                    throw new Exception("Posicion repetida");
                }
                
                //Si el mov es en X
                if (coord[0] != tmpX){
                    //Si tmb cambio y
                    if (coord[1] != tmpY){
                        throw new Exception("Avanzo en ambas coordenadas");
                    }
                    //Si la diferencia es mayor que 1
                    if (Math.abs(coord[0] - tmpX) > 1){
                        throw new Exception("Avanzo mas de 1 posicion");
                    }
                }
                //Si el mov fue en Y
                else{
                    //Si la diferencia es mayor que 1
                    if (Math.abs(coord[1] - tmpY) > 1){
                        throw new Exception("Avanzo mas de 1 posicion");
                    }
                }
                tmpX = coord[0];
                tmpY = coord[1];
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
            return c;
        }
        return 0;
    }
    
    public void interpretar(){
        listaDeMovimientos = new ArrayList<>();
        for (int[] coord : coordenadasConvertidas){
            //Mov en x
            if (coord[0] != this.x){
                //Mov a la derecha
                if (coord[0] > this.x){
                    listaDeMovimientos.add(Direccion.derecha);
                }else{
                    listaDeMovimientos.add(Direccion.izquierda);
                }
            }else{
                //Mov a la derecha
                if (coord[1] > this.y){
                    listaDeMovimientos.add(Direccion.abajo);
                }else{
                    listaDeMovimientos.add(Direccion.arriba);
                }
            }
            this.x = coord[0];
            this.y = coord[1];
        }
    }
    
    public void mostrarMovimientos(){
        interpretar();
        int c = 2;
        for (Direccion dir : listaDeMovimientos){
            System.out.println(c++ +": "+dir.getTexto());
        }
    }
}