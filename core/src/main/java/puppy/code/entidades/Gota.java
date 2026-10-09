/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puppy.code.entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import puppy.code.Tarro;
import puppy.code.movimiento.MovimientoStrategy;

/**
 *
 * @author justin
 */
public abstract class Gota implements Dibujable {
    private final Rectangle area;
    private final Texture textura;
    private MovimientoStrategy movimiento;
    
    protected Gota(float x, float y, Texture newTextura, MovimientoStrategy newMovimiento) {
        this.area = new Rectangle(x, y, 64, 64);
        this.textura = newTextura;
        this.movimiento = newMovimiento;
    }
    
    public void actualizar(float delta) {
        movimiento.mover(area, delta);
    }
    
    @Override
    public void dibujar(SpriteBatch batch) {
        batch.draw(textura, area.x, area.y);
    }
    
    public boolean chocaCon(Rectangle otra) {
        return area.overlaps(otra);
    }
    
    public boolean llegoAlSuelo() {
        return area.y + area.height <= 0;
    }
    
    public void setMovimiento(MovimientoStrategy movimiento) {
        this.movimiento = movimiento;
    }
    
    public abstract void aplicarEfecto(Tarro tarro);
}
