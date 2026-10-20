package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import puppy.code.pantallas.PantallaBase;

public class MainMenuScreen extends PantallaBase {

    public MainMenuScreen(GameLluviaMenu game) {
        super(game);
    }

    @Override
    protected void dibujar(SpriteBatch batch, BitmapFont font) {
        font.getData().setScale(2, 2);
        font.draw(batch, "Bienvenido a Recolecta Gotas!!! ",
                100, getCamera().viewportHeight / 2 + 50);
        font.draw(batch, "Toca en cualquier lugar para comenzar!",
                100, getCamera().viewportHeight / 2 - 50);
    }

    @Override
    protected void procesarEntrada() {
        if (Gdx.input.isTouched()) {
            getGame().setScreen(new GameScreen(getGame()));
            dispose();
        }
    }
}
