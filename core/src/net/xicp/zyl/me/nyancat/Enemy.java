package net.xicp.zyl.me.nyancat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends AbstractObject{
	private TextureAtlas catAtlas;
	Animation fly_cat;
	float stateTime;
	boolean start_directionX;//random start x direction ,right is true
	float old_velocity_x;//
	boolean directionChanged;
	private int move_speed = 200;
	private int width;
	private int height;
	private int flipCount;
	private int old_frame_index = -1;
	boolean isDie = false;
	boolean isFlip[];
	public enum Direction{
		LEFT,RIGHT
	}
	Direction direction;
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

	public int getMove_speed() {
		return move_speed;
	}

	public void setMove_speed(int move_speed) {
		this.move_speed = move_speed;
	}

	public enum TYPE {
		partycat
	}
	public Enemy(TYPE type) {
		if(type == TYPE.partycat)
		{
			catAtlas = new TextureAtlas(Gdx.files.internal("images/skin_2012_partycat.xml.xmf"));
			Sprite sp1 = catAtlas.createSprite("cat_scat_01");
			width = sp1.getRegionWidth();
			height = sp1.getRegionHeight();
			Sprite sp2 = catAtlas.createSprite("cat_scat_02");
			Sprite sp3 = catAtlas.createSprite("cat_scat_03");
			Sprite sp4 = catAtlas.createSprite("cat_scat_04");
			fly_cat = new Animation(0.15f, sp1,sp2,sp3,sp4);
			fly_cat.setPlayMode(PlayMode.LOOP);
			isFlip = new boolean[fly_cat.getKeyFrames().length];
		}
		init();
	}
	
	public void init()
	{
		isDie = false;
		start_directionX = true;
		flipCount = 0;
		stateTime = 0;
		old_velocity_x = 0;
		velocity.set(0,0);
		position.set(0, 0);
		old_frame_index = -1;
		directionChanged = false;
		direction = Direction.RIGHT;
	}
	
	public void update(Vector2 gravity, float deltaTime)
	{
		stateTime += deltaTime;
		if(velocity.x == 0)
		{
			if(start_directionX)
				velocity.x = getMove_speed();
			else
				velocity.x = -getMove_speed();
		}
		if(position.x + getWidth() >= NyancatGame.scr_width)
		{
			velocity.x = -getMove_speed();
			direction = Direction.LEFT;
		}
		if(position.x <= 0)
		{
			velocity.x = getMove_speed();
			direction = Direction.RIGHT;
		}
		if(isDie)
		{
			velocity.add(gravity);
		}
		position.mulAdd(velocity, deltaTime);
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		// TODO Auto-generated method stub
		final TextureRegion keyFrame = fly_cat.getKeyFrame(stateTime);
		final int keyFrameIndex = fly_cat.getKeyFrameIndex(stateTime);
		if(directionChanged && !isFlip[keyFrameIndex])
		{
			keyFrame.flip(true, false);
			isFlip[keyFrameIndex] = true;
			flipCount++;
			if(flipCount >= fly_cat.getKeyFrames().length)
			{
				flipCount = 0;
				directionChanged = false;
				old_frame_index = -1;
				for(int i = 0;i < fly_cat.getKeyFrames().length;i++)
				{
					isFlip[i] = false;
				}
			}
		}
		if(velocity.x > 0 && old_velocity_x < 0)
		{
			directionChanged = true;
		}
		else if(velocity.x < 0 && old_velocity_x > 0)
		{
			directionChanged = true;
		}
		old_velocity_x = velocity.x;
		old_frame_index = keyFrameIndex;
		batch.draw(keyFrame,position.x,position.y);
	}
	
}
