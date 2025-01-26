package com.buuz135.modderspiggybank;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.Person;
import net.fabricmc.loader.impl.FabricLoaderImpl;

import java.util.ArrayList;
import java.util.HashMap;

public class FabricModdersPiggyBankMod implements ModInitializer {
    
    @Override
    public void onInitialize() {
        var authorList = new HashMap<String, AuthorInformation>();

        for (ModContainer mod : FabricLoaderImpl.InitHelper.get().getAllMods()) {
            var authors =  mod.getMetadata().getAuthors();
            for (Person author : authors) {
                if (author.getName().isEmpty()) continue;
                authorList.computeIfAbsent(author.getName().trim(), string ->  new AuthorInformation(string, new ArrayList<>())).modNames().add(mod.getMetadata().getName());
            }
        }
        CommonClass.init(authorList);
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (Constants.ALLOWED_SCREEN_CLASSES.contains(screen.getClass())) {
                Screens.getButtons(screen).add(CommonClass.getRandomWidget());
            }
        });
    }
}
