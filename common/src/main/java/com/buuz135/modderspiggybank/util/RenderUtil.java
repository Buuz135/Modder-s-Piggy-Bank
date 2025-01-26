package com.buuz135.modderspiggybank.util;

import net.minecraft.client.gui.GuiGraphics;

public class RenderUtil {

    public static void renderFrameGradient(GuiGraphics guiGraphics, int x, int y, int width, int height, int z, int topColor, int bottomColor) {
        renderVerticalLineGradient(guiGraphics, x, y, height - 2, z, topColor, bottomColor);
        renderVerticalLineGradient(guiGraphics, x + width - 1, y, height - 2, z, topColor, bottomColor);
        renderHorizontalLine(guiGraphics, x, y - 1, width, z, topColor);
        renderHorizontalLine(guiGraphics, x, y - 1 + height - 1, width, z, bottomColor);
    }

    public static void renderVerticalLine(GuiGraphics guiGraphics, int x, int y, int length, int z, int color) {
        guiGraphics.fill(x, y, x + 1, y + length, z, color);
    }

    public static void renderVerticalLineGradient(GuiGraphics guiGraphics, int x, int y, int length, int z, int topColor, int bottomColor) {
        guiGraphics.fillGradient(x, y, x + 1, y + length, z, topColor, bottomColor);
    }

    public static void renderHorizontalLineGradient(GuiGraphics guiGraphics, int x, int y, int length, int z, int topColor, int bottomColor) {
        guiGraphics.fillGradient(x, y, x + length, y + 4, z, topColor, bottomColor);
    }

    public static void renderHorizontalLine(GuiGraphics guiGraphics, int x, int y, int length, int z, int color) {
        guiGraphics.fill(x, y, x + length, y + 1, z, color);
    }

    public static void renderRectangle(GuiGraphics guiGraphics, int x, int y, int width, int height, int z, int color) {
        guiGraphics.fill(x, y, x + width, y + height, z, color);
    }
}
