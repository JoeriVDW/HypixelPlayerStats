package com.quac.bedstats.core.structure;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;

import java.io.File;

public class Config extends Vigilant {
    @Property(
            type = PropertyType.TEXT, name = "Api Key",
            protectedText = true,
            description = "Api Key used for certain functions",
            category = "! General",
            placeholder = "Not found"
    )
    public static String apiKey = "";

    public Config(File file) {
        super(file);
        initialize();
    }
}
