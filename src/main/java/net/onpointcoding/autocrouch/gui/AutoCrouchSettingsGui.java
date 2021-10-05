package net.onpointcoding.autocrouch.gui;

import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.onpointcoding.autocrouch.Utils;
import net.onpointcoding.autocrouch.client.AutoCrouchClient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Environment(EnvType.CLIENT)
public class AutoCrouchSettingsGui extends LightweightGuiDescription {
    public AutoCrouchSettingsGui() {
        WPlainPanel root = new WPlainPanel();
        setRootPanel(root);
        root.setSize(200, 200);
        root.setInsets(Insets.ROOT_PANEL);
        root.setBackgroundPainter(BackgroundPainter.VANILLA);
        root.setHost(this);

        AutoCrouchClient autoCrouch = AutoCrouchClient.getInstance();

        WLabel label = new WLabel(new TranslatableText("autocrouch.config.title"));
        root.add(label, 0, 0, root.getWidth(), 18);

        WTabPanel tabs = new WTabPanel();
        root.add(tabs, 0, 16, root.getWidth(), root.getHeight() - 20);

        WPlainPanel panelA = new WPlainPanel();
        WPlainPanel panelB = new WPlainPanel();
        panelA.setSize(tabs.getWidth(), tabs.getHeight() - 30);
        panelB.setSize(tabs.getWidth(), tabs.getHeight() - 30);

        tabs.add(panelA, tab -> tab.title(new TranslatableText("autocrouch.config.tab.general.title")));
        tabs.add(panelB, tab -> tab.title(new TranslatableText("autocrouch.config.tab.screens.title")));

        WPlainPanel panelBDisabled = new WPlainPanel();
        WPlainPanel panelBInner = new WPlainPanel();
        WPlainPanel panelBOuter = new WPlainPanel();

        WLabel lbl = new WLabel(new TranslatableText("autocrouch.config.tab.screens.disabled"));
        panelBDisabled.add(lbl, 4, 8, panelB.getWidth() - 8, 18);

        WScrollPanel panelBScroller = new WScrollPanel(panelBInner);
        panelBScroller.setScrollingHorizontally(TriState.FALSE);
        panelBScroller.setScrollingVertically(TriState.DEFAULT);

        panelBOuter.setSize(panelB.getWidth() - 16, panelB.getHeight() - 16);
        panelBOuter.add(panelBScroller, 4, 4, panelBOuter.getWidth(), panelBOuter.getHeight());

        if (autoCrouch.config.EnableGUIs) {
            panelB.add(panelBOuter, 4, 4, panelB.getWidth() - 8, panelB.getHeight() - 8);
            panelB.remove(panelBDisabled);
        } else {
            panelB.add(panelBDisabled, 4, 4, panelB.getWidth() - 8, panelB.getHeight() - 8);
            panelB.remove(panelBOuter);
        }

        generateScreenClassList(autoCrouch, panelBInner);

        WToggleButton toggleButtonA = new WToggleButton(new TranslatableText("autocrouch.config.tab.general.enable_guis"));
        toggleButtonA.setToggle(autoCrouch.config.EnableGUIs);
        toggleButtonA.setOnToggle(on -> {
            autoCrouch.config.EnableGUIs = on;
            autoCrouch.saveConfig();
            if (on) {
                panelB.add(panelBOuter, 4, 4, panelB.getWidth() - 8, panelB.getHeight() - 8);
                panelB.remove(panelBDisabled);
            } else {
                panelB.add(panelBDisabled, 4, 4, panelB.getWidth() - 8, panelB.getHeight() - 8);
                panelB.remove(panelBOuter);
            }
        });

        WToggleButton toggleButtonB = new WToggleButton(new TranslatableText("autocrouch.config.tab.general.enable_chat"));
        toggleButtonB.setToggle(autoCrouch.config.EnableChat);
        toggleButtonB.setOnToggle(on -> {
            autoCrouch.config.EnableChat = on;
            autoCrouch.saveConfig();
        });

        panelA.add(toggleButtonA, 8, 8, panelA.getWidth() - 16, 18);
        panelA.add(toggleButtonB, 8, 26, panelA.getWidth() - 16, 18);

        root.validate(this);
    }

    private void generateScreenClassList(AutoCrouchClient autoCrouch, WPlainPanel panel) {
        List<Class<? extends Screen>> a = new ArrayList<>(Utils.getClassesExtendingBaseClass(AutoCrouchClient.InGameScreenPackage, Screen.class));
        a.removeIf(aClass -> aClass.getSimpleName().startsWith("Abstract") || aClass.getSimpleName().equals("HandledScreen"));
        a.sort(Comparator.comparing(Class::getSimpleName));
        for (int i = 0; i < a.size(); i++)
            panel.add(generateScreenClassItem(autoCrouch, a.get(i)), 4, 4 + i * 18, panel.getWidth() - 8, 18);
        panel.add(new WLabel(new LiteralText("")), 4, 4 + a.size() * 18, panel.getWidth() - 8, 4);
    }

    private WToggleButton generateScreenClassItem(AutoCrouchClient autoCrouch, Class<? extends Screen> name) {
        WToggleButton toggleButton = new WToggleButton(new LiteralText(name.getSimpleName()));
        toggleButton.setToggle(autoCrouch.config.EnableScreensSet.contains(name.getName()));
        toggleButton.setOnToggle(on -> {
            if (on) autoCrouch.config.EnableScreensSet.add(name.getName());
            else autoCrouch.config.EnableScreensSet.remove(name.getName());
            autoCrouch.saveConfig();
        });
        return toggleButton;
    }
}
