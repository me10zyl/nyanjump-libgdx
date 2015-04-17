package net.xicp.zyl.me.nyancat;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;

public class Platform extends AbstractObject{
	private TextureAtlas catAtlas;
	public static final int GAP_Y = 414;
	private float move_speed = 5;
	private boolean directionX;
	private Spring spring;
	private SmallRocket rocket;
	private boolean isRemoved;
	public boolean isRemoved() {
		return isRemoved;
	}

	public void setRemoved(boolean isRemoved) {
		this.isRemoved = isRemoved;
	}

	public SmallRocket getRocket() {
		return rocket;
	}

	public void setRocket(SmallRocket rocket) {
		this.rocket = rocket;
	}

	boolean hasSpring;
	boolean hasRocket;
	
	public Spring getSpring() {
		return spring;
	}

	public void setSpring(Spring spring) {
		this.spring = spring;
	}

	
	public float getMove_speed() {
		return move_speed;
	}

	public void setMove_speed(float move_speed) {
		this.move_speed = move_speed;
	}

	private Sprite texture;
	String[] types = {
			"platf_cake_120",
			"platf_cake_240",
			"platf_cake_60",
			"platf_disco_120",
			"platf_disco_240",
			"platf_disco_60",
			"platf_gold_120",
			"platf_gold_240",
			"platf_gold_60",
			"platf_gum_120",
			"platf_gum_240",
			"platf_gum_60",
			"platf_piano_120",
			"platf_piano_240",
			"platf_piano_60",
			"platf_pite_120",
			"platf_pite_240",
			"platf_pite_60",
			"platf_sausage_120",
			"platf_sausage_240",
			"platf_sausage_60",

	};

	public static enum TYPE {
		platf_cake_120,
		platf_cake_240,
		platf_cake_60,
		platf_disco_120,
		platf_disco_240,
		platf_disco_60,
		platf_gold_120,
		platf_gold_240,
		platf_gold_60,
		platf_gum_120,
		platf_gum_240,
		platf_gum_60,
		platf_piano_120,
		platf_piano_240,
		platf_piano_60,
		platf_pite_120,
		platf_pite_240,
		platf_pite_60,
		platf_sausage_120,
		platf_sausage_240,
		platf_sausage_60
	}
	private String type = "platf_disco_120";
	private Level level;
	
	public void moveXCircle()
	{
		if(position.x > NyancatGame.scr_width)
		{
			position.x = -getWidth();
		}
		if(position.x < -getWidth())
		{
			position.x = NyancatGame.scr_width;
		}
		if(directionX)
			velocity.x = getMove_speed();
		else
			velocity.x = -getMove_speed();
	}
	
	public void moveX()
	{
		if(velocity.x == 0)
		{
			if(directionX)
				velocity.x = getMove_speed();
			else
				velocity.x = -getMove_speed();
		}
		if(position.x + getWidth() >= NyancatGame.scr_width)
		{
			velocity.x = -getMove_speed();
		}
		if(position.x <= 0)
		{
			velocity.x = getMove_speed();
		}
	}
	
	
	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		directionX = MathUtils.randomBoolean();
		this.level = level;
	}

	public static enum Level{
		NORMAL, DYNAMIC_X, DYNAMIC_X_CIRCLE, DYNAMIC_Y, BROKEN
	}
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		texture = catAtlas.createSprite(type);
	}
	
	public float getWidth()
	{
		return texture.getWidth();
	}
	
	public float getHeight()
	{
		return texture.getHeight();
	}

	public Platform() {
		// TODO Auto-generated constructor stub
		catAtlas = new TextureAtlas("images/theme_common.xml.xmf");
		texture = catAtlas.createSprite(type);
		spring = new Spring();
		rocket = new SmallRocket();
		init();
	}

	public void init() {
		position.set(0, 0);
		velocity.set(0, 0);
		isRemoved = false;
		hasRocket = false;
		hasSpring = false;
		if(MathUtils.randomBoolean(0.15f))
		{
			hasSpring = true;
			hasRocket = false;
		}
		if(MathUtils.randomBoolean(0.07f))
		{
			hasRocket = true;
			hasSpring = false;
		}
	}
	
	public Platform(TYPE type) {
		// TODO Auto-generated constructor stub
		this();
		texture = catAtlas.createSprite(types[type.ordinal()]);
	}
	
	public void draw(SpriteBatch batch)
	{
		if(hasSpring)
		{
			spring.position.x = this.position.x + getWidth() / 2 - spring.getWidth() / 2;
			spring.position.y = this.position.y + getHeight() - spring.getHeight()/2;
			spring.draw(batch);
		}
		if(hasRocket)
		{
			rocket.position.x = this.position.x + getWidth() / 2 - rocket.getWidth() / 2;
			rocket.position.y = this.position.y + getHeight() - rocket.getHeight() / 2 + 6;
			rocket.draw(batch);
		}
		batch.draw(texture, position.x, position.y);
	}
}
