package com.buuz135.modderspiggybank;


import com.buuz135.modderspiggybank.client.PiggyBankWidget;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.options.OptionsScreen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommonClass {

    public static HashMap<String, AuthorPiggyBank> PIGGY_BANKS = new HashMap<>();
    public static HashMap<String, AuthorInformation> AUTHOR_INFORMATION = new HashMap<>();
    public static List<AuthorInformation> INFORMATION = new ArrayList<>();


    public static void init(HashMap<String, AuthorInformation> authors) {
        AUTHOR_INFORMATION = authors;
        INFORMATION = AUTHOR_INFORMATION.values().stream().toList();
        loadMinified();

        Constants.ALLOWED_SCREEN_CLASSES.add(TitleScreen.class);
        Constants.ALLOWED_SCREEN_CLASSES.add(OptionsScreen.class);

        Constants.ALLOWED_LINKS.put("ko-fi", "Ko-fi");
        Constants.ALLOWED_LINKS.put("github-sponsor", "Github Sponsor");
        Constants.ALLOWED_LINKS.put("patreon", "Patreon");
        Constants.ALLOWED_LINKS.put("buymeacoffee", "Buy Me a Coffee");
        Constants.ALLOWED_LINKS.put("paypal", "Paypal");
        Constants.ALLOWED_LINKS.put("custom", "Custom");
    }

    public static AuthorPiggyBank getPiggyBankOrDefault(String authorName){
        if (PIGGY_BANKS.containsKey(authorName)) {
            return PIGGY_BANKS.get(authorName);
        }
        return new AuthorPiggyBank(authorName, 0x55FFFF, 0x55FFFF, new ArrayList<>(), new ArrayList<>());
    }

    public static PiggyBankWidget getRandomWidget() {
        //SELECT A RANDOM
        var selected = CommonClass.INFORMATION.get(Constants.RANDOM.nextInt(CommonClass.INFORMATION.size()));
        //FIND THEIR PIGGY BANK
        var piggy = CommonClass.getPiggyBankOrDefault(selected.name());
        //COLLECT ALTERNATE ATTRIBUTIONS
        List<String> alternateMods = new ArrayList<>();
        if (!piggy.alternate().isEmpty()){
            for (String alternateMod : piggy.alternate()) {
                if (CommonClass.AUTHOR_INFORMATION.containsKey(alternateMod)) {
                    alternateMods.addAll(CommonClass.AUTHOR_INFORMATION.get(alternateMod).modNames());
                }
            }
        }
        return new PiggyBankWidget( 2,3, selected, piggy, alternateMods);
    }

    private static void loadMinified(){
        new Thread(() -> {
            try {
                var urlContents = readUrl(new URL(Constants.MINIFIED_URL));
                System.out.println(urlContents);
                var parsed = new JsonParser().parse(urlContents);
                for (JsonElement jsonElement : parsed.getAsJsonArray()) {
                    var object = jsonElement.getAsJsonObject();
                    var author = object.get("author").getAsString();
                    if (!AUTHOR_INFORMATION.containsKey(author)) continue;
                    var primary_color = 0x55FFFF;
                    if (object.has("primary_color")) {
                        primary_color = Integer.decode(object.get("primary_color").getAsString());
                    }
                    var secondary_color = 0x55FFFF;
                    if (object.has("secondary_color")) {
                        secondary_color = Integer.decode(object.get("secondary_color").getAsString());
                    }
                    var alternate_attribution = new ArrayList<String>();
                    if (object.has("alternate_attribution")) {
                        for (JsonElement alternateAttribution : object.getAsJsonArray("alternate_attribution")) {
                            alternate_attribution.add(alternateAttribution.getAsString());
                        }
                    }
                    var links = new ArrayList<AuthorPiggyBank.Link>();
                    object.get("links").getAsJsonArray().forEach(link -> {
                       links.add(new AuthorPiggyBank.Link(link.getAsJsonObject().get("type").getAsString(), link.getAsJsonObject().get("url").getAsString()));
                    });

                    PIGGY_BANKS.put(author, new AuthorPiggyBank(author, primary_color, secondary_color, alternate_attribution, links));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }).start();
    }

    private static String readUrl(URL url) throws IOException {
        BufferedReader reader = null;
        try {
            HttpURLConnection httpURLConnection =  (HttpURLConnection) url.openConnection();
            httpURLConnection.setInstanceFollowRedirects(true);
            httpURLConnection.setUseCaches(false);
            reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }
}