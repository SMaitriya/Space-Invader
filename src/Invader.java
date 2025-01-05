import com.jogamp.opengl.GL2;

public class Invader {
    private float x; // Position X de l'envahisseur
    private float y; // Position Y de l'envahisseur
    private static final float SIZE = 0.06f;

    // Constructeur pour initialiser la position de l'envahisseur
    public Invader(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // Méthode pour déplacer l'envahisseur selon un déplacement horizontal (dx) et vertical (dy)
    public void move(float dx, float dy) {
        x += dx; // Ajouter dx à la position X
        y += dy; // Ajouter dy à la position Y
    }

    // Méthode pour dessiner l'envahisseur à l'écran
    public void draw(GL2 gl) {
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(x - SIZE, y - SIZE);
        gl.glVertex2f(x + SIZE, y - SIZE);
        gl.glVertex2f(x + SIZE, y + SIZE);
        gl.glVertex2f(x - SIZE, y + SIZE);
        gl.glEnd();
    }

    // Méthode pour obtenir la position X de l'envahisseur
    public float getX() {
        return x;
    }

    // Méthode pour obtenir la position Y de l'envahisseur
    public float getY() {
        return y;
    }

    // Méthode pour obtenir la taille de l'envahisseur
    public float getSize() {
        return SIZE; // Retourner la taille constante de l'envahisseur
    }
}
