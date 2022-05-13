package com.redlimerl.sleepbackground.config;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;

import java.util.HashSet;

public class ConfigValues {

    public static final HashSet<ConfigValue> ALL_CONFIGS = Sets.newHashSet();

    public static final FrameLimitConfigValue BACKGROUND_FRAME_RATE =
            new FrameLimitConfigValue("background", 1, "It works when instance is in the background after joined the world.");

    public static final FrameLimitConfigValue LOADING_SCREEN_FRAME_RATE =
            new FrameLimitConfigValue("loading_screen", 30, "It works when instance is in the world loading screen. maximum (fps_limit) is 15.") {
                @Override
                protected void loadToInit(JsonObject configObject) {
                    super.loadToInit(configObject);
                    Integer fps = this.getFrameLimit();
                    if (fps != null && fps < 15) {
                        throw new IllegalArgumentException("loading_screen fps limit is must be 15 or over");
                    }
                }
            };

    public static final RenderTimesConfigValue WORLD_PREVIEW_RENDER_TIMES =
            new RenderTimesConfigValue("world_preview", 5, "config for world preview, every time (loading_screen) is rendered (render_times) times, will be render a preview. ex) if (loading_screen.fps_limit) is 30 and this value is 2, preview fps will be 15(30 ÷ 2).");

    public static final FrameTickConfigValue WORLD_INITIAL_FRAME_RATE =
            new FrameTickConfigValue("world_setup", 10, 30, "same with (background) config but for (max_ticks) ticks after the joined the world.");


    static {
        ALL_CONFIGS.add(BACKGROUND_FRAME_RATE);
        ALL_CONFIGS.add(LOADING_SCREEN_FRAME_RATE);
        ALL_CONFIGS.add(WORLD_PREVIEW_RENDER_TIMES);
        ALL_CONFIGS.add(WORLD_INITIAL_FRAME_RATE);
    }
}