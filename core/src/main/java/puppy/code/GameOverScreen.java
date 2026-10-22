package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import puppy.code.pantallas.PantallaBase;

public class GameOverScreen extends PantallaBase {
    public GameOverScreen(GameLluviaMenu game) {
        super(game);
    }

    @Override
    protected void dibujar(SpriteBatch batch, BitmapFont font) {
        font.draw(batch, "GAME OVER", 100, 200);
        font.draw(batch, "Record: " + getGame().getHigherScore(), 100, 150);
        font.draw(batch, "Clic o Enter para reiniciar", 100, 100);
    }

    @Override
    protected void procesarEntrada() {
        if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            getGame().setScreen(new GameScreen(getGame()));
        }
    }
}
