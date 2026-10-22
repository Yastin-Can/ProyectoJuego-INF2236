package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import puppy.code.pantallas.PantallaBase;

/**
 * Pantalla de pausa del videojuego que extiende PantallaBase aplicando Template Method (GM-8)
 * y encapsulamiento de constantes (GM-5).
 *
 * @author Benja
 */
public class PausaScreen extends PantallaBase {

    private static final float COLOR_R = 0f;
    private static final float COLOR_G = 0f;
    private static final float COLOR_B = 1f;
    private static final float COLOR_A = 1f;
    private static final float TEXTO_X = 100f;
    private static final float TITULO_Y = 150f;
    private static final float SUBTITULO_Y = 100f;

    private final GameScreen juego;

    public PausaScreen(GameLluviaMenu game, GameScreen juego) {
        super(game);
        this.juego = juego;
    }

    @Override
    protected void limpiarPantalla() {
        ScreenUtils.clear(COLOR_R, COLOR_G, COLOR_B, COLOR_A);
    }

    @Override
    protected void dibujar(SpriteBatch batch, BitmapFont font) {
        font.draw(batch, "Juego en pausa", TEXTO_X, TITULO_Y);
        font.draw(batch, "Clic o Escape para continuar", TEXTO_X, SUBTITULO_Y);
    }

    @Override
    protected void procesarEntrada() {
        if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            getGame().setScreen(this.juego);
        }
    }

    @Override
    public void dispose() {
        if (this.juego != null) {
            this.juego.dispose();
        }
    }
}