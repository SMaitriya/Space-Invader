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
        // Corps du missile (rectangle allongé)
        gl.glColor3f(1.0f, 0.0f, 0.0f); // Couleur rouge pour le corps du missile
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(x - SIZE * 0.2f, y - SIZE); // Coin inférieur gauche
        gl.glVertex2f(x + SIZE * 0.2f, y - SIZE); // Coin inférieur droit
        gl.glVertex2f(x + SIZE * 0.2f, y + SIZE); // Coin supérieur droit
        gl.glVertex2f(x - SIZE * 0.2f, y + SIZE); // Coin supérieur gauche
        gl.glEnd();

        // Tête du missile (triangle)
        gl.glColor3f(1.0f, 0.5f, 0.0f); // Couleur orange pour la tête du missile
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex2f(x - SIZE * 0.3f, y + SIZE); // Coin gauche de la tête
        gl.glVertex2f(x + SIZE * 0.3f, y + SIZE); // Coin droit de la tête
        gl.glVertex2f(x, y + SIZE * 1.5f); // Pointe de la tête
        gl.glEnd();

        // Ailerons du missile (rectangles pour chaque côté)
        gl.glColor3f(0.5f, 0.5f, 0.5f); // Couleur gris pour les ailerons
        gl.glBegin(GL2.GL_QUADS);
        // Aileron gauche
        gl.glVertex2f(x - SIZE * 0.2f, y - SIZE * 0.6f);
        gl.glVertex2f(x - SIZE * 0.4f, y - SIZE * 0.8f);
        gl.glVertex2f(x - SIZE * 0.4f, y - SIZE * 1.0f);
        gl.glVertex2f(x - SIZE * 0.2f, y - SIZE * 0.8f);
        gl.glEnd();

        gl.glBegin(GL2.GL_QUADS);
        // Aileron droit
        gl.glVertex2f(x + SIZE * 0.2f, y - SIZE * 0.6f);
        gl.glVertex2f(x + SIZE * 0.4f, y - SIZE * 0.8f);
        gl.glVertex2f(x + SIZE * 0.4f, y - SIZE * 1.0f);
        gl.glVertex2f(x + SIZE * 0.2f, y - SIZE * 0.8f);
        gl.glEnd();
    }

    // Accesseurs pour la position et la taille de la balle
    public float getX() { return x; }
    public float getY() { return y; }
    public float getSize() { return SIZE; }
}