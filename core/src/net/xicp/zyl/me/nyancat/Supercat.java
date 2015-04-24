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
		atlas = Assets.theme_commonAtlas;
		altlas2_anim = Assets.skin_catheroAtlas;
		Sprite sprite1 = Assets.cat_scat_01Sprite;
		Sprite sprite2 = Assets.cat_scat_02Sprite;
		Sprite sprite3 = Assets.cat_scat_03Sprite;
		Sprite sprite4 = Assets.cat_scat_04Sprite;
		anim_supercat = Assets.supercatAnimation;
		supercat = Assets.powerup_supercatSprite;
		width  = supercat.getWidth();
		height = supercat.getHeight();
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		// TODO Auto-generated method stub
		batch.draw(supercat,position.x,position.y);
	}

}
