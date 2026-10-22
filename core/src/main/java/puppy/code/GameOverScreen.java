package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import puppy.code.pantallas.PantallaBase;

/**
 * Pantalla de fin de partida (Game Over) que extiende PantallaBase aplicando Template Method (GM-8)
 * y encapsulamiento de constantes (GM-5).
 *
 * @author Benja
 */
public class GameOverScreen extends PantallaBase {

    private static final float TEXTO_X = 100f;
    private static final float TITULO_Y = 200f;
    private static final float RECORD_Y = 150f;
    private static final float INSTRUCCION_Y = 100f;

    public GameOverScreen(GameLluviaMenu game) {
        super(game);
    }

    @Override
    protected void dibujar(SpriteBatch batch, BitmapFont font) {
        font.draw(batch, "GAME OVER", TEXTO_X, TITULO_Y);
        font.draw(batch, "Record: " + getGame().getHigherScore(), TEXTO_X, RECORD_Y);
        font.draw(batch, "Clic o Enter para reiniciar", TEXTO_X, INSTRUCCION_Y);
    }

    @Override
    protected void procesarEntrada() {
        if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            getGame().setScreen(new GameScreen(getGame()));
        }
    }
}