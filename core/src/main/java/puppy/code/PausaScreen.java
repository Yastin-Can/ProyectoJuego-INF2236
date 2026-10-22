package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import puppy.code.pantallas.PantallaBase;

public class PausaScreen extends PantallaBase {
    private final GameScreen juego;

    public PausaScreen(GameLluviaMenu game, GameScreen juego) {
        super(game);
        this.juego = juego;
    }

    @Override
    protected void limpiarPantalla() {
        ScreenUtils.clear(0, 0, 1, 1);
    }

    @Override
    protected void dibujar(SpriteBatch batch, BitmapFont font) {
        font.draw(batch, "Juego en pausa", 100, 150);
        font.draw(batch, "Clic o Escape para continuar", 100, 100);
    }

    @Override
    protected void procesarEntrada() {
        if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            getGame().setScreen(juego);
        }
    }

    @Override
    public void dispose() {
        // Al cerrar la aplicacion pausada, libera tambien la partida retenida.
        juego.dispose();
    }
}
