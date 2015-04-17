package net.xicp.zyl.me.nyancat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Rocket extends AbstractObject {
	private TextureAtlas catAtlas;
	private TextureRegion rocket_no_fire;
	private float width;
	private float height;
	Animation anim_rocket;
	boolean isBroken = false;
	
	public boolean isBroken() {
		return isBroken;
	}

	public void setBroken(boolean isBroken) {
		this.isBroken = isBroken;
	}

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

	public Rocket() {
		// TODO Auto-generated constructor stub
		catAtlas = new TextureAtlas("images/tp.xml.xmf");
		rocket_no_fire = new TextureRegion(new Texture("images/rocket_no_fire.png"));
		width = rocket_no_fire.getRegionWidth();
		height = rocket_no_fire.getRegionHeight();
		catAtlas = new TextureAtlas("images/tp.xml.xmf");
		final Sprite rocket1 = catAtlas.createSprite("rocketeer_rocket1");
		final Sprite rocket2 = catAtlas.createSprite("rocketeer_rocket2");
		final Sprite rocket3 = catAtlas.createSprite("rocketeer_rocket3");
		final Sprite rocket4 = catAtlas.createSprite("rocketeer_rocket4");
		rocket1.setRotation(90f);
		rocket1.rotate90(true);
		rocket2.rotate90(true);
		rocket3.rotate90(true);
		rocket4.rotate90(true);
		anim_rocket = new Animation(0.01f, rocket1,rocket2,rocket3,rocket4);
		anim_rocket.setPlayMode(Animation.PlayMode.LOOP);
	}
	
	public void update(Vector2 gravity, float deltaTime)
	{
		velocity.add(gravity);
		position.mulAdd(velocity,deltaTime);
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		// TODO Auto-generated method stub
		batch.draw(rocket_no_fire, position.x, position.y, width/2, height/2, width, height, 1, 1, 90);
	}

}