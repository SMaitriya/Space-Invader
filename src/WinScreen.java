import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.awt.TextRenderer;

public class WinScreen implements GLEventListener {
    private TextRenderer textRenderer;

    @Override
    public void init(GLAutoDrawable drawable) {
        textRenderer = new TextRenderer(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 36));
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        // le message de victoire
        textRenderer.beginRendering(800, 600);
        textRenderer.draw("You Win!", 350, 300);
        textRenderer.endRendering();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) { }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }
}
