package org.opensanskrit.widget;

import org.opensanskrit.widget.interfaces.SplashableWindow;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;

import javax.swing.JWindow;

public class SplashScreen {

    private static SplashableWindow splashWindow = null;

    public static SplashableWindow getInstance(int steps, Image splashImage) {
        if (splashWindow == null)
            splashWindow = Java6SplashScreen.getInstance(steps, splashImage);
        return splashWindow;
    }

    /**
     * SplashScreen
     * 
     * @author luca
     */
    private static class Java5SplashScreen implements SplashableWindow {
        protected static SplashableWindow splashscreen = null;
        protected Graphics2D graphics2D;
        protected int steps;
        protected int stepCounter = 0;
        protected int width;
        protected int height;
        private JWindow splashJW;
        private final Image splashImage;

        protected Java5SplashScreen(int steps, Image splashImage) {
            this.steps = steps;
            this.splashImage = splashImage;
            this.width = splashImage.getWidth(null);
            this.height = splashImage.getHeight(null);
        }

        public static SplashableWindow getInstance(int steps, Image splashImage) {
            if (splashscreen == null) {
                splashscreen = new Java5SplashScreen(steps, splashImage);
            }
            return splashscreen;
        }

        @Override
        public void start() {
            Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (screenDimension.width - width) / 2;
            int y = (screenDimension.height - height) / 2;

            splashJW = new JWindow()  {
                /*
                 * Non è chiaro il motivo, ma sembrerebbe che occorra
                 * necessariamente disegnare la splashImage nel metodo paint.
                 * Non è chiaro perchè occorra invocare super.paint(g). Non è
                 * chiaro perchè disegnare qualcosa che attenga ad una image
                 * debba essere fatto anch'esso nel metodo paint. Approfondire
                 * le API Graphics2D!!!
                 */

                @Override
                public void paint(Graphics g) {
                    super.paint(g);
                    graphics2D.drawImage(splashImage, 0, 0, null);
                    graphics2D.setColor(Color.BLACK);
                    graphics2D.draw(new Rectangle2D.Double(0, 0, 299, 299));
                }
            };
            splashJW.setBounds(x, y, width, height);
            splashJW.setVisible(true);

            /*
             * E' fondamentale che la JWindow sia visibile per ottenere
             * l'oggetto graphics2D. In caso contrario verrà restituito null.
             */
            graphics2D = (Graphics2D) splashJW.getGraphics();
        }

        @Override
        public void updateStartupState(String message) {
            stepCounter++;

            graphics2D.setBackground(new Color(255, 255, 255, 0));
            graphics2D.clearRect(0, 301, 299, 20);
            graphics2D.setColor(new Color(157, 53, 7, 255));
            graphics2D.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
            graphics2D.drawString(message, 10, 315);
            graphics2D.setColor(new Color(243, 101, 35, 100));
            graphics2D.fillRect(1, 301, (299 / steps) * stepCounter, 18);
        }

        @Override
        public void close() {
            splashJW.dispose();
        }
    }

    private static class Java6SplashScreen extends Java5SplashScreen {

        private java.awt.SplashScreen splash;

        private Java6SplashScreen(int steps, Image splashImage) {
            super(steps, splashImage);
        }

        public static SplashableWindow getInstance(int steps, Image splashImage) {
            if (splashscreen == null)
                splashscreen = new Java6SplashScreen(steps, splashImage);
            return splashscreen;
        }

        @Override
        public void start() {
            this.splash = java.awt.SplashScreen.getSplashScreen();
            if (splash == null) {
                System.out.println("SplashScreen.getSplashScreen() returned null");
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
        public void close() {}
    }
}