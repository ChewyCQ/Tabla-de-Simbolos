package tabla_de_simbolos;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
    
    int orientacionX;
    int orientacionY;
    int contadorMovimientos;
    String movimiento;
    List<String []> listaMovimientos;
    List<String> codigo;
    
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
        x = 1;
        y = 8;
        orientacionX = 0;
        orientacionY = 1;//Hacia arriba
        contadorMovimientos = 1;
        listaMovimientos = new ArrayList<>();
        codigo = new ArrayList<>();
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
    
    private String orientacion(){
        if (orientacionY == 1)
            return "^";
        if (orientacionY == -1)
            return "V";
        if (orientacionX == 1)
            return ">";
        if (orientacionX == -1)
            return "<";
        else
            return "Error";
    }
    
    public void movimiento(String coordenada){
        int yAux = 0;
        int xAux = 0;
        
        //Obtener los valores de x & y
        try {
            xAux = Integer.parseInt(coordenada.split(",")[0]);
            yAux = Integer.parseInt(coordenada.split(",")[1]);
        }catch(Exception e){
            System.out.println("Malas coordenadas. El formato es x,y");
            return;
        }
        
        //Si el movimiento es en x
        if (Math.abs(xAux - x) == 1){
            //Si se mueve tmb en y
            if (Math.abs(yAux - y) != 0){
                System.out.println("Malas coordenadas");
                return;
            }else{
                if (xAux > x){
                    System.out.println("Movimiento en x+");
                    movimiento = "Movimiento en x+";
                }
                else{
                    System.out.println("Movimiento en x-");
                    movimiento = "Movimiento en x-";
                }
                //Recorre la posicion
                giro(xAux - x, 0);
                x += xAux - x;
                contadorMovimientos++;
            }
        }else{
            //Si el movimiento es en y
            if (Math.abs(yAux - y) == 1){
                //Si se mueve tmb en x
                if (Math.abs(xAux - x) != 0){
                    System.out.println("Malas coordenadas");
                    return;
                }else{
                    if (yAux < y){
                        System.out.println("Movimiento en y+");
                        movimiento = "Movimiento en y+";
                    }
                    else{
                        System.out.println("Movimiento en y-");
                        movimiento = "Movimiento en y-";
                    }
                    //Recorre la posicion
                    giro(0,-1*(yAux - y));
                    y += yAux - y;
                    contadorMovimientos++;
                }
            }
        }
    }
    
    public void giro(int movX, int movY){
        //Movimiento en x
        if (movX != 0){
            //Anterior movimiento sobre x
            if (orientacionX != 0){
                if (movX == orientacionX){
                    System.out.println("Avanza");
                    listaMovimientos.add(new String[]{"Avanza", orientacion()});
                    codigo.add("Avanza();");
                    
                }else{
                    System.out.println("Gira 180 y avanza");
                    //Se invierte orientacionX
                    if (orientacionX == 1){
                        orientacionX = -1;
                        listaMovimientos.add(new String[]{"Gira 180 y avanza.", orientacion()});
                    }
                    else{
                        orientacionX = 1;
                        listaMovimientos.add(new String[]{"Gira 180 y avanza.", orientacion()});
                        codigo.add("Gira(180)");
                        codigo.add("Avanza()");
                    }
                }
            }
            //Anterior movimiento sobre y
            else{
                if (movX == orientacionY){
                    System.out.println("Gira -90 y avanza");
                    orientacionX = orientacionY;
                    orientacionY = 0;
                    listaMovimientos.add(new String[]{"Gira -90 y avanza.", orientacion()});
                    codigo.add("Gira(-90)");
                    codigo.add("Avanza");
                }
                else{
                    System.out.println("Gira +90 y avanza");
                    if (orientacionY == 1){
                        orientacionX = -1;
                        orientacionY = 0;
                        listaMovimientos.add(new String[]{"Gira +90 y avanza.", orientacion()});
                        codigo.add("Gira(90)");
                        codigo.add("Avanza");
                    }
                    else{
                        orientacionX = 1;
                        orientacionY = 0;
                        listaMovimientos.add(new String[]{"Gira +90 y avanza.", orientacion()});
                        codigo.add("Gira(90)");
                        codigo.add("Avanza");
                    }
                }
            }
        }
        //Movimiento en y
        else if (movY != 0){
            //Anterior movimiento sobre y
            if (orientacionY != 0){
                if (movY == orientacionY){
                    System.out.println("Avanza");
                    listaMovimientos.add(new String[]{"Avanza", orientacion()});
                    codigo.add("Avanza()");
                }else{
                    System.out.println("Gira 180 y avanza");
                    if (orientacionY == 1){
                        orientacionY = -1;
                    }
                    else{
                        orientacionY = 1;
                    }
                    listaMovimientos.add(new String[]{"Gira 180 y avanza.", orientacion()});
                    codigo.add("Gira(180)");
                    codigo.add("Avanza");
                }
            }
            //Anterior movimiento sobre x
            else{
                if (movY == orientacionX){
                    System.out.println("Gira +90 y avanza");
                    orientacionX = orientacionY;
                    orientacionY = 1;
                    listaMovimientos.add(new String[]{"Gira +90 y avanza.", orientacion()});
                    codigo.add("Gira(90)");
                    codigo.add("Avanza");
                }
                else{
                    System.out.println("Gira -90 y avanza");
                    if (orientacionX == 1){
                        orientacionY = -1;
                        orientacionX = 0;
                    }else{
                        orientacionY = 1;
                        orientacionX = 0;
                    }
                    listaMovimientos.add(new String[]{"Gira -90 y avanza.", orientacion()});
                    codigo.add("Gira(-90)");
                    codigo.add("Avanza");
                }
            }
        }
    }
    
    public void verCoordenadas(){
        System.out.println("Orientacion: "+ direccion());
        System.out.print("Coordenadas actuales (y,x) = ");
        System.out.print(y);
        System.out.println("," + x);
    }
    
    public String direccion(){
        if (orientacionX == 1)
            return ">";
        if (orientacionX == -1)
            return "<";
        if (orientacionY == 1)
            return "^";
        if (orientacionY == -1)
            return "V";
        if (orientacionY == 0 && orientacionX == 0)
            return "Los 2 valen 0";
        if (orientacionY != 0 && orientacionX != 0)
            return "Los 2 != 0";
        return "Algo raro paso";
    }
    
    public static void main(String[] args){
        Robot robot = new Robot();
        Scanner scan = new Scanner(System.in);
        String coordenadas = "";
        
        while (!coordenadas.equals("0")){
            robot.verCoordenadas();
            System.out.println("Movimiento #" + robot.contadorMovimientos);
            System.out.print("Coordenadas nuevas (y,x): ");
            coordenadas = scan.nextLine();
            robot.movimiento(coordenadas);
            System.out.println();
        }
    }
}