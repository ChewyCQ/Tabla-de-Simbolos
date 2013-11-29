package tabla_de_simbolos;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * 
 * @author DavidCQ
 */
public class Dibujo {
    private Image imagen;
    private int x;
    private int y;

    public Dibujo(String urlImagen, int x, int y) {
        try{
            //this.imagen = new ImageIcon(getClass().getResource(urlImagen)).getImage();
            imagen = ImageIO.read(new File(urlImagen));

        }catch(IOException e){
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
        }
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void movimientoEnX(int x) {
        if ( this.x + x < 1 )
            this.x = 1;
        else
            if ( this.x + x > 23 )
                this.x = 23;
            else
                this.x += x;
    }

    public int getY() {
        return y;
    }

    public void movimientoEnY(int y) {
        if ( this.y + y < 1 )
            this.y = 1;
        else
            if ( this.y + y > 8 )
                this.y = 8;
            else
                this.y += y;
    }
    
    //Funciones
    public void dibujar(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        //BufferedImage img = ImageIO.read("robot.png");
//        int w = img.getWidth(null);
//        int h = img.getHeight(null);

        float[] scales = { 1f, 1f, 1f, 0.5f };
        float[] offsets = new float[4];
        RescaleOp rop = new RescaleOp(scales, offsets, null);
//        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        //g2.drawImage(imagen,rop,0,0);
        g2.drawImage(imagen, x, y, null);//Tamano real
    }
}