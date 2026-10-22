package puppy.code;

import com.badlogic.gdx.Gdx;
import puppy.code.pantallas.PantallaBase;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen extends PantallaBase {




	private Tarro tarro;
	private Lluvia lluvia;

	   
	//boolean activo = true;

	public GameScreen(final GameLluviaMenu game) {
        super(game);
		  // load the images for the droplet and the bucket, 64x64 pixels each 	     
		  Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
		  tarro = new Tarro(new Texture(Gdx.files.internal("bucket.png")),hurtSound);
         
	      // load the drop sound effect and the rain background "music" 
         Texture gota = new Texture(Gdx.files.internal("drop.png"));
         Texture gotaMala = new Texture(Gdx.files.internal("dropBad.png"));
         
         Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        
	     Music rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
         lluvia = new Lluvia(gota, gotaMala, dropSound, rainMusic);
	      
	      // creacion del tarro
	      tarro.crear();
	      
	      // creacion de la lluvia
	      lluvia.crear();
	}

    @Override
    protected void actualizar(float delta) {
        if (!tarro.estaHerido()) {
            tarro.actualizarMovimiento();
            if (!lluvia.actualizarMovimiento(tarro)) {
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
        tarro.dibujar(batch);
        lluvia.actualizarDibujoLluvia(batch);
    }

    @Override
    protected void procesarEntrada() {
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            pause();
        }
    }

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	  // continuar con sonido de lluvia
	  lluvia.continuar();
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {
		lluvia.pausar();
		getGame().setScreen(new PausaScreen(getGame(), this)); 
	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
      tarro.destruir();
      lluvia.destruir();

	}

}
