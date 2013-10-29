package tabla_de_simbolos;

import java.util.Scanner;

/**
 * 
 * @author DavidCQ
 */
public class Robot {
    int x;
    int y;
    int orientacionX;
    int orientacionY;
    int contadorMovimientos;

    public Robot() {
        x = 1;
        y = 1;
        orientacionX = 0;
        orientacionY = 1;
        contadorMovimientos = 1;
    }
    
    void movimiento(String coordenada){
        int yAux = 0;
        int xAux = 0;
        
        //Obtener los valores de x & y
        try {
            yAux = Integer.parseInt(coordenada.split(",")[0]);
            xAux = Integer.parseInt(coordenada.split(",")[1]);
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
                }
                else{
                    System.out.println("Movimiento en x-");
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
                    if (yAux > y){
                        System.out.println("Movimiento en y+");
                    }
                    else{
                        System.out.println("Movimiento en y-");
                    }
                    //Recorre la posicion
                    giro(0,yAux - y);
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
                    System.out.println("Avanza en la misma direccion.");
                }else{
                    System.out.println("Gira 180 y avanza");
                    //Se invierte orientacionX
                    if (orientacionX == 1)
                        orientacionX = -1;
                    else
                        orientacionX = 1;
                }
            }
            //Anterior movimiento sobre y
            else{
                if (movX == orientacionY){
                    System.out.println("Gira -90 y avanza");
                    orientacionX = orientacionY;
                    orientacionY = 0;
                }
                else{
                    System.out.println("Gira +90 y avanza");
                    if (orientacionY == 1){
                        orientacionX = -1;
                        orientacionY = 0;
                    }
                    else{
                        orientacionX = 1;
                        orientacionY = 0;
                    }
                }
            }
        }
        //Movimiento en y
        else if (movY != 0){
            //Anterior movimiento sobre y
            if (orientacionY != 0){
                if (movY == orientacionY){
                    System.out.println("Avanza en la misma direccion.");
                }else{
                    System.out.println("Gira 180 y avanza");
                    if (orientacionY == 1)
                        orientacionY = -1;
                    else
                        orientacionY = 1;
                }
            }
            //Anterior movimiento sobre x
            else{
                if (movY == orientacionX){
                    System.out.println("Gira +90 y avanza");
                    orientacionX = orientacionY;
                    orientacionY = 0;
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
