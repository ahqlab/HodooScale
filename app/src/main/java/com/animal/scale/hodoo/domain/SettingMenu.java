package com.animal.scale.hodoo.domain;

import lombok.Data;

@Data
public class SettingMenu {

    public SettingMenu(int icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public SettingMenu(String name) {
        this.name = name;
    }

    private int icon;

    private String name;

    private boolean badgeState;

    private int badgeCount;
}
