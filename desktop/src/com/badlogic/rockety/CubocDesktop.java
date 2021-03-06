
package com.badlogic.rockety;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class CubocDesktop {
	public static void main (String[] argv) {
		new LwjglApplication(new RocketGame(), "RocketGame", 480, 320);

		// After creating the Application instance we can set the log level to
		// show the output of calls to Gdx.app.debug
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
	}
}
