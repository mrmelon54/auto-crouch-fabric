package net.onpointcoding.autocrouch.config;

import blue.endless.jankson.annotation.SerializedName;

import java.util.HashSet;
import java.util.Set;

public class AutoCrouchConfig {
    @SerializedName("enable_guis")
    public Boolean EnableGUIs;
    @SerializedName("enable_tab")
    public Boolean EnableTab;
    @SerializedName("enable_chat")
    public Boolean EnableChat;
    @SerializedName("enable_screens_set")
    public Set<String> EnableScreensSet = new HashSet<>();
}
