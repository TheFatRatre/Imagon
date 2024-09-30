package com.cyc.imagon.View;

import io.vproxy.vfx.manager.font.FontManager;
import io.vproxy.vfx.manager.font.FontProvider;
import io.vproxy.vfx.manager.font.FontSettings;
import io.vproxy.vfx.theme.impl.DarkTheme;
import io.vproxy.vfx.theme.impl.DarkThemeFontProvider;

/**
 * ClassName: FXTheme
 * Package: com.cyc.imagon.View
 * Description:
 *
 * @Author CYC
 * @Create 2024/8/17 16:30
 * @Version 1.0
 */
public class ViewTheme extends DarkTheme {
    @Override
    public FontProvider fontProvider() {
        return new ImageFontProvider();
    }

    public static class ImageFontProvider extends DarkThemeFontProvider {
        @Override
        protected void defaultFont(FontSettings settings) {
            super.defaultFont(settings);
            settings.setFamily(FontManager.FONT_NAME_JetBrainsMono);
        }
    }
}
