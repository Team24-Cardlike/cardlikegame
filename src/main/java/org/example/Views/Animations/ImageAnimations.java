package org.example.Views.Animations;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
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
}
