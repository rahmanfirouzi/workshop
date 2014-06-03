package controller;

import com.dhtmlx.planner.DHXEvent;

/**
 * The class Event extends DHXEvent. every event in javaplanner should be an
 * object of the type DHXEvent. this class extends DHXEvent.
 *
 * @author Rahman Firouzi
 */
public class Event extends DHXEvent {
    
    // the color of the event
    public final String SUGGESTED_WORK_TIME = "#DEB887";
    public final String WORKSHOP_TIME = "#32CD32";
    public final String SUGGESTED_STUDY_TIME = "#6495ED";
    public final String PERSON_SUGGESTED_WORK_TIME = "#6495ED";

    public String color;

    public String textColor;

    public Boolean readonly = false;

    public Boolean getReadonly() {
        return readonly;
    }

    public void setReadonly(Boolean readonly) {
        this.readonly = readonly;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
