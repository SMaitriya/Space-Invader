import com.jogamp.opengl.GL2;

public class Invader {
    private float x; // Position X de l'envahisseur
    private float y; // Position Y de l'envahisseur
    private static final float SIZE = 0.07f;

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
        // Corps principal
        gl.glColor3f(0.0f, 1.0f, 0.0f);
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex2f(x - SIZE, y - SIZE * 0.5f);
        gl.glVertex2f(x + SIZE, y - SIZE * 0.5f);
        gl.glVertex2f(x + SIZE, y + SIZE * 0.5f);
        gl.glVertex2f(x - SIZE, y + SIZE * 0.5f);
        gl.glEnd();

        // Tentacules inférieures (triangles)
        gl.glBegin(GL2.GL_TRIANGLES);
        // Tentacule gauche
        gl.glVertex2f(x - SIZE * 0.8f, y - SIZE * 0.5f);
        gl.glVertex2f(x - SIZE * 0.5f, y - SIZE);
        gl.glVertex2f(x - SIZE * 0.2f, y - SIZE * 0.5f);
        // Tentacule droite
        gl.glVertex2f(x + SIZE * 0.8f, y - SIZE * 0.5f);
        gl.glVertex2f(x + SIZE * 0.5f, y - SIZE);
        gl.glVertex2f(x + SIZE * 0.2f, y - SIZE * 0.5f);
        gl.glEnd();

        // Yeux (cercles rouges menaçants)
        gl.glColor3f(1.0f, 0.0f, 0.0f); // Rouge pour les yeux
        // Œil gauche
        gl.glBegin(GL2.GL_POLYGON);
        for (int i = 0; i < 360; i += 10) {
            double angle = Math.toRadians(i);
            float dx = (float) (Math.cos(angle) * SIZE * 0.2f);
            float dy = (float) (Math.sin(angle) * SIZE * 0.2f);
            gl.glVertex2f(x - SIZE * 0.5f + dx, y + SIZE * 0.2f + dy);
        }
        gl.glEnd();
        // Œil droit
        gl.glBegin(GL2.GL_POLYGON);
        for (int i = 0; i < 360; i += 10) {
            double angle = Math.toRadians(i);
            float dx = (float) (Math.cos(angle) * SIZE * 0.2f);
            float dy = (float) (Math.sin(angle) * SIZE * 0.2f);
            gl.glVertex2f(x + SIZE * 0.5f + dx, y + SIZE * 0.2f + dy);
        }
        gl.glEnd();

        // Antennes (triangles supérieurs)
        gl.glColor3f(0.0f, 1.0f, 0.0f);
        gl.glBegin(GL2.GL_TRIANGLES);
        // Antenne gauche
        gl.glVertex2f(x - SIZE * 0.8f, y + SIZE * 0.5f);
        gl.glVertex2f(x - SIZE * 0.6f, y + SIZE);
        gl.glVertex2f(x - SIZE * 0.4f, y + SIZE * 0.5f);
        // Antenne droite
        gl.glVertex2f(x + SIZE * 0.8f, y + SIZE * 0.5f);
        gl.glVertex2f(x + SIZE * 0.6f, y + SIZE);
        gl.glVertex2f(x + SIZE * 0.4f, y + SIZE * 0.5f);
        gl.glEnd();

        // Bouche (forme en zigzag)
        gl.glColor3f(0.0f, 0.8f, 0.0f); // Vert légèrement plus foncé
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex2f(x - SIZE * 0.6f, y - SIZE * 0.2f);
        gl.glVertex2f(x - SIZE * 0.3f, y);
        gl.glVertex2f(x, y - SIZE * 0.2f);
        gl.glVertex2f(x + SIZE * 0.3f, y);
        gl.glVertex2f(x + SIZE * 0.6f, y - SIZE * 0.2f);
        gl.glVertex2f(x + SIZE * 0.6f, y - SIZE * 0.3f);
        gl.glVertex2f(x - SIZE * 0.6f, y - SIZE * 0.3f);
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