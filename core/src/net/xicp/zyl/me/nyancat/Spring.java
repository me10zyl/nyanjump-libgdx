package net.xicp.zyl.me.nyancat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Spring extends AbstractObject {
	private Texture texture;
	private int width;
	private int height;
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Spring() {
		// TODO Auto-generated constructor stub
		texture = Assets.springTexture;
		width = texture.getWidth();
		height = texture.getHeight();
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		// TODO Auto-generated method stub
		batch.draw(texture, position.x, position.y);
	}

}
