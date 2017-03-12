package com.madtriangle.fasttap.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import buttons.ButtonsGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Color Buttons";
        config.width= (int) (1080 / 3);
        config.height = (int) (1720 / 3);
        new LwjglApplication(new ButtonsGame(new ActionResolverDesktop()), config);
    }
}
