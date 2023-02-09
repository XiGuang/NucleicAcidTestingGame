package com.xiguang.hello;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;

public class HelloWorldApp extends GameApplication {

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setTitle("11");
        gameSettings.setVersion("0.1");
        gameSettings.setHeight(520);
        gameSettings.setWidth(480);
    }


    public static void main(String[] args) {
        launch(args);
    }

}
