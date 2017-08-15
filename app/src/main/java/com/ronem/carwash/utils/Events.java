package com.ronem.carwash.utils;

/**
 * Created by ram on 7/10/17.
 */

public class Events {

    public static class ReloadEvent {
        private boolean reload;

        public ReloadEvent(boolean reload) {
            this.reload = reload;
        }

        public boolean isReload() {
            return reload;
        }
    }
}
