package tabla_de_simbolos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author DavidCQ
 */
public class PanelDibujo extends javax.swing.JPanel{
    private Dibujo dibujo;
    private Timer timer;

    public PanelDibujo(Dimension d) {
        this.setSize(d);
        this.setPreferredSize(d);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //this.setBackground(new Color(0,255,0));
        this.setBackground(Color.LIGHT_GRAY);
        
        //dibujo = new Dibujo("C:\\Users\\DavidCQ\\Documents\\NetBeansProjects\\Tabla_de_Simbolos\\robot.png", 1, 8);
        dibujo = new Dibujo("C:\\robot.png", 1, 8);
        
        timer = new Timer(16, new ActionListener (){
            public void actionPerformed(ActionEvent e) {
                //dibujo.move();
                repaint();
            }
        });
    }
    
    //Funciones
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujo.dibujar(g);
    }
}
