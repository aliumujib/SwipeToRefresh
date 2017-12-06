package com.aliumujib.swipetorefresh;

/**
 * Created by oliviergoutay on 1/23/15.
 */
public enum RefreshDirection {

    TOP(0),
    BOTTOM(1),
    BOTH(2);

    private int mValue;

    RefreshDirection(int value) {
        this.mValue = value;
    }

    public static RefreshDirection getFromInt(int value) {
        for (RefreshDirection direction : RefreshDirection.values()) {
            if (direction.mValue == value) {
                return direction;
            }
        }
        return BOTH;
    }

}
