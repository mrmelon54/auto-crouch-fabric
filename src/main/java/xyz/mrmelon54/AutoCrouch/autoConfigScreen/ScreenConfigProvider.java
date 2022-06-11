package xyz.mrmelon54.AutoCrouch.autoConfigScreen;

import me.shedaniel.autoconfig.gui.registry.api.GuiProvider;
import me.shedaniel.autoconfig.gui.registry.api.GuiRegistryAccess;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.minecraft.text.Text;
import xyz.mrmelon54.AutoCrouch.config.ScreenConfig;
import xyz.mrmelon54.AutoCrouch.utils.Deobfuscator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ScreenConfigProvider implements GuiProvider {
    @Override
    public List<AbstractConfigListEntry> get(String s, Field field, Object o, Object o1, GuiRegistryAccess guiRegistryAccess) {
        List<AbstractConfigListEntry> list = new ArrayList<>();
        if (o instanceof ScreenConfig screenConfig) {
            Iterator<String> iterator = screenConfig.EnabledScreens.keySet().stream().sorted().iterator();
            SubCategoryBuilder abstractConfigListEntries = ConfigEntryBuilder.create().startSubCategory(Text.literal("In Game Screens")).setExpanded(true);
            while (iterator.hasNext()) {
                final String a = iterator.next();
                Boolean b = screenConfig.EnabledScreens.get(a);
                String a1 = Deobfuscator.deobfuscateClass(a);
                Text c = Text.literal(a1.replace("net.minecraft.client.gui.screen.ingame.", ""));
                abstractConfigListEntries.add(ConfigEntryBuilder.create().startBooleanToggle(c, b).setDefaultValue(true).setSaveConsumer(value -> screenConfig.EnabledScreens.put(a1, value)).build());
            }
            list.add(abstractConfigListEntries.build());
        }
        return list;
    }
}
