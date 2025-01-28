package com.buuz135.modderspiggybank;

import com.buuz135.modderspiggybank.client.PiggyBankWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.TitleScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforgespi.language.IModInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EventBusSubscriber(Dist.CLIENT)
public class NeoforgeEvent {

    @SubscribeEvent
    public static void onGui(ScreenEvent.Init.Post event){;
        if (Constants.ALLOWED_SCREEN_CLASSES.contains(event.getScreen().getClass().getSimpleName()) || true) {
            event.addListener(CommonClass.getRandomWidget());
        }
    }
}
