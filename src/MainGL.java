import java.awt.Dimension;

import javax.swing.*;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;

public class MainGL extends GLCanvas implements GLEventListener
{
    private float angle1 = 0.0f;
    private float angle2 = 0.0f;
    private float angle3 = 0.0f;

    private float moyenCubeZ = -2.0f; // Valeur initiale



    /**
     *
     */
    public MainGL(){ this.addGLEventListener(this); }

    /**
     *
     */
    public void init(GLAutoDrawable drawable)
    {



    }

    /**
     *
     */
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        final GLU glu = new GLU();
        float aspect = (float)width / height;
// Set the view port (display area)
        gl.glViewport(0, 0, width, height);
// Setup perspective projection,
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0, aspect, 0.1, 100.0);
// Enable the model-view transform
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
// PRENDRE EN COMPTE LA PROFONDEUR
        gl.glEnable(GL2.GL_DEPTH_TEST);
    }

    /**
     *
     */
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        // Déplacement en arrière pour voir les cubes
        gl.glTranslatef(0.0f, 0.0f, -10.0f);
        gl.glRotatef(angle1, 0.0f, 1.0f, 0.0f); // Appliquer la rotation autour de l'axe Y


        // Dessiner le grand cube
        Cube largeCube = new Cube(1.0f);

        gl.glPushMatrix(); // Sauvegarder l'état de la matrice
        gl.glColor3f(0.6f, 0.0f, 0.2f);

        largeCube.draw(gl);
        gl.glPopMatrix(); // Récupérer l'état de la matrice

        // Cube moyen
        gl.glPushMatrix();

        gl.glTranslatef(0.0f, 0.0f, 1.0f); // Utiliser la position Z
        gl.glRotatef(angle2, 5.0f, 0.0f, 5.0f); // Appliquer la rotation autour de l'axe Y


        Cube moyenCub = new Cube(0.5f);
        gl.glPushMatrix();
        gl.glColor3f(0.0f, 0.0f, 0.5f);
        moyenCub.draw(gl);
        gl.glPopMatrix();

        // Petit cube
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.0f,-2.0f);
        gl.glRotatef(angle3, 0.0f,1.0f,0.0f);

        Cube petitCube = new Cube(0.1f);

        gl.glColor3f(0.8f, 0.8f, 0.8f);
        petitCube.draw(gl);
        gl.glPopMatrix();

        // Crazy

        gl.glRotatef(angle1, 0.0f, 1.0f, 0.0f);
        gl.glTranslatef(2.0f, 0.0f, 0.0f);

        Cube crazyCube = new Cube(0.4f);
        gl.glColor3f(0.8f, 0.52f, 0.25f); // Couleur marron clair pour le crazy cube
        crazyCube.draw(gl);
        gl.glPopMatrix(); // Récupérer l'état de la matrice





        angle1 += 3.0f;
        angle2 += 6.0f;
        angle3 += 20.0f;



/*
        gl.glColor3f(0.0f, 0.0f, 1.0f);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex3f(-0.5f, -0.5f, -4.0f);
        gl.glVertex3f(0.5f, -0.5f, -4.0f);
        gl.glVertex3f(0.5f, 0.5f, -4.0f);
        gl.glVertex3f(-0.5f, 0.5f, -4.0f);
        gl.glEnd();

        gl.glColor3f(1.0f, 0.0f, 0.0f);
        gl.glBegin(GL.GL_TRIANGLES);
        gl.glVertex3f(-1f, -1f, -5.0f);
        gl.glColor3f(0.0f, 0, 1.0f);
        gl.glVertex3f(1f, -1f, -5.0f);
        gl.glColor3f(0.0f, 1.0f, 0.0f);
        gl.glVertex3f(0f, 1f, -5.0f);
        gl.glEnd();*/

    }

    /**
     *
     */
    public void dispose(GLAutoDrawable drawable) { }

    public static void main(String[] args)
    {

        GLCanvas canvas = new MainGL();
        canvas.setPreferredSize(new Dimension(900, 900));
        canvas.setAutoSwapBufferMode(true);  // Active le double tampon

        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(canvas);
        frame.setTitle("Java - OpenGL");
        frame.pack();
        Timer timer = new Timer(16, e -> canvas.repaint());
        timer.start();

        frame.setVisible(true);

    }
}