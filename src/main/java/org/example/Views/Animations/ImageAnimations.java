package org.example.Views.Animations;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ImageAnimations{

    public static void moveToSelected(Image card, float x, float y) {
        card.addAction(Actions.sequence(
                Actions.moveTo(x, y, 0.5f, Interpolation.swingOut),
                Actions.color(Color.GOLD, 0.25f)
        ));
    }

    public static void moveToHand(Image card, float x, float y) {
        card.addAction(Actions.sequence(
                Actions.moveTo(x, y, 0.5f, Interpolation.swingIn),
                Actions.color(Color.WHITE, 0.25f)
        ));
    }

    public static void opponentAnimation(Image opponent, float startX, float startY, float y){
        opponent.addAction(Actions.sequence(
                Actions.moveTo(startX, y, 0.9f, Interpolation.pow2OutInverse),
                Actions.color(Color.WHITE, 0.25f),
                Actions.moveTo(startX, startY, 0.5f, Interpolation.pow2Out),
                Actions.color(Color.WHITE, 0.25f)
        ));
    }

    public static Action screenShake(float intensity, float duration) {
        return Actions.sequence(
                Actions.repeat((int)(duration / 0.05f),
                        Actions.sequence(
                                Actions.moveBy(-intensity, 0, 0.025f),
                                Actions.moveBy(intensity, 0, 0.025f))),
                Actions.moveTo(0, 0)
        );
    }
}
