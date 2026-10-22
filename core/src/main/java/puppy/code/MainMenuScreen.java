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
        float escalaX = font.getData().scaleX;
        float escalaY = font.getData().scaleY;
        try {
            font.getData().setScale(2);
            font.draw(batch, "Bienvenido a Recolecta Gotas!", 70, 290);
            font.getData().setScale(1);
            font.draw(batch, "Haz clic para comenzar. Flechas: mover. Escape: pausa.", 100, 190);
        } finally {
            font.getData().setScale(escalaX, escalaY);
        }
    }

    @Override
    protected void procesarEntrada() {
        if (Gdx.input.justTouched()) {
            getGame().setScreen(new GameScreen(getGame()));
            dispose();
        }
    }
}
