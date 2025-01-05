import com.jogamp.opengl.GL2;

import com.jogamp.opengl.GL2;

public class Bullet {
    private float x, y;
    private static final float SIZE = 0.02f;
    private static final float SPEED = 0.02f;

    // Constructeur qui initialise la position de la balle
    public Bullet(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // Met à jour la position de la balle, et retourne false si elle doit être supprimée
    public boolean update() {
        y += SPEED; // Déplace la balle vers le haut
        return y <= 1.0f; // Si la balle dépasse la limite de l'écran, elle est supprimée
    }

    // Dessine la balle à l'écran avec OpenGL
    public void draw(GL2 gl) {
        gl.glColor3f(1.0f, 1.0f, 0.0f);
        gl.glBegin(GL2.GL_QUADS); // Début de la forme carrée pour la balle
        gl.glVertex2f(x - SIZE, y - SIZE);
        gl.glVertex2f(x + SIZE, y - SIZE);
        gl.glVertex2f(x + SIZE, y + SIZE);
        gl.glVertex2f(x - SIZE, y + SIZE);
        gl.glEnd();
    }

    // Accesseurs pour la position et la taille de la balle
    public float getX() { return x; }
    public float getY() { return y; }
    public float getSize() { return SIZE; }
}
