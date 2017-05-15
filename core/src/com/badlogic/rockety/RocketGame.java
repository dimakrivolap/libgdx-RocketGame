
package com.badlogic.rockety;

import com.badlogic.rockety.screens.MainMenu;
import com.badlogic.gdx.Game;

public class RocketGame extends Game {
	@Override
	public void create () {
		setScreen(new MainMenu(this));
	}
}
