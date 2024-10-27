import com.jogamp.opengl.GL2;

public class Cube {
    private float size; // Taille du cube

    public Cube(float size) {
        this.size = size;
    }

    public void draw(GL2 gl) {
        // Définition des sommets du cube
        float halfSize = size / 2;

        // Dessiner les 6 faces du cube
        gl.glBegin(GL2.GL_QUADS);

        // Face avant
        gl.glVertex3f(-halfSize, -halfSize, halfSize);
        gl.glVertex3f(halfSize, -halfSize, halfSize);
        gl.glVertex3f(halfSize, halfSize, halfSize);
        gl.glVertex3f(-halfSize, halfSize, halfSize);

        // Face arrière
        gl.glVertex3f(-halfSize, -halfSize, -halfSize);
        gl.glVertex3f(-halfSize, halfSize, -halfSize);
        gl.glVertex3f(halfSize, halfSize, -halfSize);
        gl.glVertex3f(halfSize, -halfSize, -halfSize);

        // Face gauche
        gl.glVertex3f(-halfSize, -halfSize, -halfSize);
        gl.glVertex3f(-halfSize, -halfSize, halfSize);
        gl.glVertex3f(-halfSize, halfSize, halfSize);
        gl.glVertex3f(-halfSize, halfSize, -halfSize);

        // Face droite
        gl.glVertex3f(halfSize, -halfSize, -halfSize);
        gl.glVertex3f(halfSize, halfSize, -halfSize);
        gl.glVertex3f(halfSize, halfSize, halfSize);
        gl.glVertex3f(halfSize, -halfSize, halfSize);

        // Face haut
        gl.glVertex3f(-halfSize, halfSize, -halfSize);
        gl.glVertex3f(-halfSize, halfSize, halfSize);
        gl.glVertex3f(halfSize, halfSize, halfSize);
        gl.glVertex3f(halfSize, halfSize, -halfSize);

        // Face bas
        gl.glVertex3f(-halfSize, -halfSize, -halfSize);
        gl.glVertex3f(halfSize, -halfSize, -halfSize);
        gl.glVertex3f(halfSize, -halfSize, halfSize);
        gl.glVertex3f(-halfSize, -halfSize, halfSize);

        gl.glEnd(); // Fin du dessin
    }
}
