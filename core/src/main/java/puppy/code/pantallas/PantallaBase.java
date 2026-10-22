package puppy.code.pantallas;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import puppy.code.GameLluviaMenu;

/**
 * Procedimiento comun de las pantallas (Template Method).
 * El juego conserva la propiedad del batch y de la fuente compartidos.
 */
public abstract class PantallaBase implements Screen {

    private final GameLluviaMenu game;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final OrthographicCamera camera;

    protected PantallaBase(GameLluviaMenu game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 800, 480);
    }

    @Override
    public final void render(float delta) {
        actualizar(delta);
        // Una actualizacion puede haber cambiado de pantalla.
        if (game.getScreen() != this) {
            return;
        }

        limpiarPantalla();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        try {
            dibujar(batch, font);
        } finally {
            batch.end();
        }
        procesarEntrada();
    }

    /** Actualiza la logica antes de abrir el batch. */
    protected void actualizar(float delta) {
    }

    protected void limpiarPantalla() {
        ScreenUtils.clear(0, 0, 0.2f, 1);
    }

    /** Dibuja contenido; no debe llamar begin/end ni cambiar de pantalla. */
    protected abstract void dibujar(SpriteBatch batch, BitmapFont font);

    /** Procesa navegacion cuando el batch ya esta cerrado. */
    protected void procesarEntrada() {
    }

    protected final GameLluviaMenu getGame() {
        return game;
    }

    protected final OrthographicCamera getCamera() {
        return camera;
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
        // Mantiene las coordenadas logicas de la base original: 800 x 480.
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        // Las subclases liberan solo sus recursos propios, nunca batch/font.
    }
}
