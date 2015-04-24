package net.xicp.zyl.me.nyancat;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public class Cat extends AbstractObject{
	private Sprite head;
	private Sprite body;
	private Sprite foot;
	private int width = 94;
	private int height = 48;
	public static Vector2 JUMP_IMPLUS = new Vector2(0, 1000);
	Animation anim_scarf;
	Animation anim_foot;
	Animation anim_rocket;
	Animation anim_supercat;
	Rocket rocket;
	Supercat supercat;
	float scarfStateTime = 0;
	float footStateTime = 0;
	float rocketStateTime = 0;
	float supercatStateTime = 0;
	boolean hasRocket;
	boolean hasSupercat;
	boolean playFootAnimation;
	boolean isDead;
	boolean canThroughThings;
	
	public Cat() {
		// TODO Auto-generated constructor stub
		catAtlas = new TextureAtlas("images/tp.xml.xmf");
		body = catAtlas.createSprite("alap_test");
		head = catAtlas.createSprite("alap_fej");
		foot = catAtlas.createSprite("alap_lab1");
		anim_scarf = new Animation(0.05f, catAtlas.createSprite("snyan_kopeny1"),catAtlas.createSprite("snyan_kopeny2"),
				catAtlas.createSprite("snyan_kopeny3"),catAtlas.createSprite("snyan_kopeny4"));
		anim_scarf.setPlayMode(Animation.PlayMode.LOOP);
		anim_foot = new Animation(0.05f, catAtlas.createSprite("alap_lab1"),catAtlas.createSprite("alap_lab2"),
				catAtlas.createSprite("alap_lab3"),catAtlas.createSprite("alap_lab4"),catAtlas.createSprite("alap_lab5"),catAtlas.createSprite("alap_lab6"));
		anim_foot.setPlayMode(Animation.PlayMode.LOOP);
		rocket = new Rocket();
		supercat = new Supercat();
		anim_rocket = rocket.anim_rocket;
		anim_supercat = supercat.anim_supercat;
		setPosition();
	}

	private void setPosition() {
		if(position.x > NyancatGame.scr_width)
		{
			position.x = -width;
		}
		if(position.x < -width)
		{
			position.x = NyancatGame.scr_width;
		}
		body.setPosition(position.x, position.y);
		head.setPosition(body.getX() + body.getWidth() / 2, body.getY());
		foot.setPosition(body.getX(), body.getY());
		if(velocity.y <= 0)
		{
			if(hasRocket)
			{
				rocket.isBroken = true;
				rocket.position.set(position);
				rocket.velocity.set(-200,500);
				hasRocket = false;
				rocket.isAnimationEnd = true;
			}
		}
		
		if(velocity.y <= 1000)
		{
			if(hasSupercat)
			{
				supercat.isAnimationEnd = true;
				hasSupercat = false;
			}
		}
	}
	
	public void draw(SpriteBatch batch)
	{
		setPosition();
		if(hasRocket)
		{
			head.setPosition(body.getX() + body.getWidth() / 2 - 4, body.getY()+anim_rocket.getKeyFrame(rocketStateTime).getRegionHeight() + 6);
			head.draw(batch);
			batch.draw(anim_rocket.getKeyFrame(rocketStateTime),body.getX(),body.getY(),anim_rocket.getKeyFrame(rocketStateTime).getRegionWidth()/2,anim_rocket.getKeyFrame(rocketStateTime).getRegionHeight()/2,anim_rocket.getKeyFrame(rocketStateTime).getRegionWidth(),anim_rocket.getKeyFrame(rocketStateTime).getRegionHeight(),1,1,90);
		}else if(hasSupercat)
		{
			batch.draw(anim_supercat.getKeyFrame(supercatStateTime),body.getX(),body.getY(),anim_supercat.getKeyFrame(supercatStateTime).getRegionWidth()/2,anim_supercat.getKeyFrame(supercatStateTime).getRegionHeight()/2,anim_supercat.getKeyFrame(supercatStateTime).getRegionWidth(),anim_supercat.getKeyFrame(supercatStateTime).getRegionHeight(),1,1,90);
		}else
		{
			batch.draw(anim_foot.getKeyFrame(footStateTime),body.getX(),body.getY());
			body.draw(batch);
			batch.draw(anim_scarf.getKeyFrame(scarfStateTime), body.getX(), body.getY());
			head.draw(batch);
		}
	}
	
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
	
	public void jump()
	{
		playFootAnimation = true;
		this.velocity.set(Cat.JUMP_IMPLUS);
	}
	
	public void hitSpring()
	{
		this.velocity.set(new Vector2(0,2000));
	}
	
	public void hitRocket()
	{
		hasRocket = true;
		rocket.isAnimationEnd = false;
		this.velocity.set(new Vector2(0,6000));
	}
	
	public void hitSupercat()
	{
		hasSupercat = true;
		supercat.isAnimationEnd = false;
		this.velocity.set(new Vector2(0,4000));
	}
}
