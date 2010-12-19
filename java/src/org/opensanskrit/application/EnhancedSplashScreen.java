package org.opensanskrit.application;

import java.awt.Color;
import java.awt.Image;
import java.awt.SplashScreen;
import java.awt.geom.Rectangle2D;

public class EnhancedSplashScreen extends
		org.opensanskrit.application.SplashScreen {

	private SplashScreen splash;

	private EnhancedSplashScreen(int steps, Image splashImage) {
		super(steps, splashImage);
	}

	public static org.opensanskrit.application.SplashScreen getInstance(
			int steps, Image splashImage) {
		if (splashscreen == null) {
			splashscreen = new EnhancedSplashScreen(steps, splashImage);
		}
		return splashscreen;
	}

	@Override
	public void start() {
		this.splash = SplashScreen.getSplashScreen();
		if (splash == null) {
			// System.out.println("SplashScreen.getSplashScreen() returned null");
			return;
		}

		graphics2D = splash.createGraphics();
		if (graphics2D == null) {
			// System.out.println("g is null");
			return;
		}

		graphics2D.setColor(Color.BLACK);
		graphics2D.draw(new Rectangle2D.Double(0, 0, 299, 299));

		splash.update();
	}

	@Override
	public void updateStartupState(String message) {
		super.updateStartupState(message);
		splash.update();
	}

	@Override
	public void close() {
	}
}