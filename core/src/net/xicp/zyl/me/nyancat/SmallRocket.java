package net.xicp.zyl.me.nyancat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public class SmallRocket extends AbstractObject {
	private Sprite texture;
	private float width;
	private float height;
	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public SmallRocket() {
		// TODO Auto-generated constructor stub
		texture = Assets.powerup_rocketeerSprite;
		width = texture.getWidth();
		height = texture.getHeight();
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		// TODO Auto-generated method stub
		batch.draw(texture, position.x, position.y);
	}

}
