package net.xicp.zyl.me.nyancat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

public class Supercat extends AbstractObject{
	TextureAtlas atlas;
	TextureAtlas altlas2_anim;
	private Sprite supercat;
	Animation anim_supercat;
	boolean isAnimationEnd;
	public Sprite getSupercat() {
		return supercat;
	}

	public void setSupercat(Sprite supercat) {
		this.supercat = supercat;
	}

	private float width, height;
	
	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public Supercat() {
		atlas = new TextureAtlas(Gdx.files.internal("images/theme_common.xml.xmf"));
		altlas2_anim = new TextureAtlas(Gdx.files.internal("images/skin_cathero.xml.xmf"));
		Sprite sprite1 = altlas2_anim.createSprite("cat_scat_01");
		Sprite sprite2 = altlas2_anim.createSprite("cat_scat_02");
		Sprite sprite3 = altlas2_anim.createSprite("cat_scat_03");
		Sprite sprite4 = altlas2_anim.createSprite("cat_scat_04");
		anim_supercat = new Animation(0.05f, sprite1,sprite2,sprite3,sprite4);
		anim_supercat.setPlayMode(PlayMode.LOOP);
		supercat = atlas.createSprite("powerup_supercat");
		width  = supercat.getWidth();
		height = supercat.getHeight();
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		// TODO Auto-generated method stub
		batch.draw(supercat,position.x,position.y);
	}

}
