package com.buuz135.modderspiggybank;


import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforgespi.language.IModInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Mod(Constants.MOD_ID)
public class NeoForgeModdersPiggyBankMod {

    public NeoForgeModdersPiggyBankMod(IEventBus eventBus) {
        var authorList = new HashMap<String, AuthorInformation>();
        for (IModInfo mod : ModList.get().getMods()) {
            var dirty = mod.getConfig().getConfigElement("authors").orElse("");
            var authors = new ArrayList<String>();
            if (dirty instanceof String dirtyString) {
                authors.addAll(List.of(dirtyString.split(",")));
            } else {
                authors = (ArrayList<String>) dirty;
            }
            for (String author : authors) {
                if (author.isEmpty()) continue;
                authorList.computeIfAbsent(author.trim(), string -> new AuthorInformation(string, new ArrayList<>())).modNames().add(mod.getDisplayName());
            }
        }
        CommonClass.init(authorList);
    }


}