package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import puppy.code.pantallas.PantallaBase;
import puppy.code.recursos.GestorRecursos;

public class GameScreen extends PantallaBase {
    private final Tarro tarro;
    private final Lluvia lluvia;
    private boolean destruida;

    public GameScreen(GameLluviaMenu game) {
        super(game);
        GestorRecursos recursos = GestorRecursos.getInstancia();
        tarro = new Tarro(recursos.getTexturaTarro(), recursos.getSonidoHerido());
        lluvia = new Lluvia(recursos.getTexturaGotaBuena(), recursos.getTexturaGotaMala(),
                recursos.getTexturaGotaDorada(), recursos.getSonidoGota(), recursos.getMusicaLluvia());
        lluvia.crear();
    }

    @Override
    protected void actualizar(float delta) {
        if (!tarro.estaHerido()) {
            tarro.actualizarMovimiento();
            if (!lluvia.actualizarMovimiento(tarro, Math.min(delta, 0.1f))) {
                getGame().setHigherScore(Math.max(getGame().getHigherScore(), tarro.getPuntos()));
                getGame().setScreen(new GameOverScreen(getGame()));
                dispose();
            }
        }
    }

    @Override
    protected void dibujar(SpriteBatch batch, BitmapFont font) {
        font.draw(batch, "Gotas totales: " + tarro.getPuntos(), 5, 475);
        font.draw(batch, "Vidas: " + tarro.getVidas(), 670, 475);
        font.draw(batch, "HighScore: " + getGame().getHigherScore(), 350, 475);
        font.draw(batch, "Nivel: " + lluvia.getNombreNivel() + " | Escape: pausa", 5, 450);
        tarro.dibujar(batch);
        lluvia.actualizarDibujoLluvia(batch);
    }

    @Override
    protected void procesarEntrada() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            pause();
        }
    }

    @Override
    public void show() { lluvia.continuar(); }

    @Override
    public void hide() { lluvia.pausar(); }

    @Override
    public void pause() {
        if (!destruida && getGame().getScreen() == this) {
            getGame().setScreen(new PausaScreen(getGame(), this));
        }
    }

    @Override
    public void dispose() {
        if (!destruida) {
            lluvia.destruir();
            destruida = true;
        }
    }
}
