package game.player.attack;

import game.enemy.Enemy;
import game.sprite.Hittable;
import game.sprite.ImageMask;
import java.util.List;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public abstract class Attack {
    
    protected List<Enemy> enemies;
    
    protected Animation anim;
    
    protected int x = 0;
    protected int y = 0;
    
    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }
    
    public abstract ImageMask getMask(int x, int y);
    public abstract void init();
    public abstract void attack(int direction, boolean sound);
    
    protected int swingEndRest;
    protected int attackRest;
    
    protected boolean attacking;
    protected int attackTimer;
    protected int attackDelay;
    
    protected int currentAttackId = 0;
    protected int attackId = 0;
    
    protected int targetDirection = 0;
    
    public boolean canAttack() {
        return !attacking && attackDelay<1;
    }
    
    public void update(int delta, int x, int y) {
        if (attackTimer<500)
            attackTimer+=delta;
        attackDelay-=delta;
        if (attackTimer > swingEndRest)
            attacking = false;
    }
    
    public void resolveAttackHit(Hittable other, int x, int y) {
        if (!attacking)
            return;
        if(other.getCollisionMask().intersects(getMask(x,y)))
            other.resolveHit(x,y,currentAttackId);
    }
    
    public void render(int x, int y) {
        if (attacking)
            anim.draw(x-64,y-64,192,192);
    }
    
    protected int getAttackId() {
        attackId = attackId>Integer.MAX_VALUE-1?0:attackId+1;
        return attackId;
    }
    
    public void renderDebugInfo(int camX, int camY, Graphics g) {
        g.drawString(attacking?"Attacking":"Not attacking",camX,camY);
        g.drawString(String.valueOf(targetDirection),camX,14+camY);
    }
    
    public void renderMask(int x, int y, Graphics g) {
        if (attacking) {
            g.setColor(Color.red);
            getMask(x,y).render(g);
        }
    }
}