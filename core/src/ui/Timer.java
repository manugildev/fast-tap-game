package ui;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import configuration.Configuration;
import gameworld.GameWorld;
import tweens.Value;
import tweens.ValueAccessor;

/**
 * Created by ManuGil on 13/03/15.
 */
public class Timer {

    Tween timerTween;
    Value time = new Value();
    TweenManager manager;
    private TweenCallback cbFinish;
    private TweenCallback cbRestart;
    private GameWorld world;

    public Timer(final GameWorld world) {
        this.world = world;
        Tween.registerAccessor(Value.class, new ValueAccessor());
        manager = new TweenManager();
        cbFinish = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                world.getTimeBanner().flash(0.2f,0f);
                world.finishGame();
            }
        };


    }

    public void update(float delta) {
        manager.update(delta);
    }

    public void finish() {
        if (timerTween != null) {
            timerTween.pause();
        }
    }

    public void start(float from, float to, float duration, float delay) {
        finish();
        time.setValue(from);
        timerTween = Tween.to(time, -1, duration).target(to).setCallback(cbFinish)
                .setCallbackTriggers(
                        TweenCallback.COMPLETE).ease(TweenEquations.easeNone)
                .delay(delay).start(manager);
    }

    public float getTime() {
        return time.getValue();
    }

    public String getTimeFormatted() {
        String text = String.format("%.01f", time.getValue());
        return text;
    }

    public void restart() {
        finish();
        timerTween = Tween.to(time, -1, 0.3f).target(Configuration.START_TIMER)
                .setCallback(cbRestart)
                .setCallbackTriggers(TweenCallback.COMPLETE).ease(TweenEquations.easeNone)
                .start(manager);
    }

    public void addTime(float seconds) {
        if (world.isRunning()) {
            finish();
            timerTween = Tween.to(time, -1, 0.2f).target(time.getValue() + seconds)
                    .setCallback(cbRestartMethod(time.getValue() + seconds))
                    .setCallbackTriggers(TweenCallback.COMPLETE).ease(TweenEquations.easeNone)
                    .start(manager);
        }
    }

    private TweenCallback cbRestartMethod(final float v) {
        cbRestart = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                start(v, 0, v, 0.1f);
            }
        };
        return cbRestart;
    }
}
