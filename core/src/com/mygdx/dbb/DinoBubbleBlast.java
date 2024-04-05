package com.mygdx.dbb;

import com.badlogic.gdx.Game;

public class DinoBubbleBlast extends Game {
	@Override
	public void create() {
		this.setScreen(new StartScreen());
		//start on StartScreen Class
	}
	@Override
	public void render() {
		super.render();
	}
}
