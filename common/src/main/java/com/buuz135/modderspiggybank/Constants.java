package com.buuz135.modderspiggybank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Constants {

	public static final String MOD_ID = "modderspiggybank";
	public static final String MOD_NAME = "Modder's Piggy-Bank";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

	public static final String MINIFIED_URL = "https://raw.githubusercontent.com/Buuz135/Modders-Piggy-Bank-Repository/refs/heads/main/mod-authors.min.json";

	public static final List<Class> ALLOWED_SCREEN_CLASSES = new ArrayList<>();
	public static HashMap<String, String> ALLOWED_LINKS = new HashMap<>();

	public static final Random RANDOM = new Random();

}