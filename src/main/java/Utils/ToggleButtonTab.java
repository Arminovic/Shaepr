package Utils;

import javafx.event.ActionEvent;
import javafx.scene.control.ToggleButton;

/**
 * used to override the normal fire() method of ToggleButton
 * the normal ToggleButton fires even when the Button is already selected, which is currently bad
 * the new ToggleButtonTab only fires when the Button isn't selected, which is nice
 */
public class ToggleButtonTab extends ToggleButton {

    //private Event onSelect = new Event();

    public ToggleButtonTab(String text) {
        super(text);
    }

    /**
     * old fire method
     */
    /*public void fire() {
        if (!isDisabled()) {
            setSelected(!isSelected());
            fireEvent(new ActionEvent());
        }
    }*/


    /**
     * new fire method
     */
    @Override
    public void fire() {
        if (!isDisabled()) {
            if (!isSelected()) {
                setSelected(true);
                fireEvent(new ActionEvent());
            }
        }
    }
}
