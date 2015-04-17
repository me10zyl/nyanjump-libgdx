package net.xicp.zyl.me.nyancat;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractObject {
	protected TextureAtlas catAtlas;
	public Vector2 position = new Vector2();
	public Vector2 velocity = new Vector2();
	abstract public void draw(SpriteBatch batch);
}
