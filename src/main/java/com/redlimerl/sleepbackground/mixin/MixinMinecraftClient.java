package com.redlimerl.sleepbackground.mixin;

import com.redlimerl.sleepbackground.SleepBackground;
import com.redlimerl.sleepbackground.config.ConfigValues;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient {

    @Shadow @Nullable public ClientWorld world;

    @Inject(method = "render", at = @At("HEAD"))
    public void onRender(CallbackInfo ci) {
        SleepBackground.LATEST_LOCK_FRAME = !SleepBackground.shouldRenderInBackground();
    }

    @Inject(method = "tick", at = @At("RETURN"))
    public void onTick(CallbackInfo ci) {
        SleepBackground.CLIENT_WORLD_TICK_COUNT = this.world == null ? 0 :
                Math.min(SleepBackground.CLIENT_WORLD_TICK_COUNT + 1, ConfigValues.WORLD_INITIAL_FRAME_RATE.getMaxTicks());

        SleepBackground.checkLock();
    }


    @Inject(method = "drawProfilerResults", at = @At("HEAD"), cancellable = true, expect = 0, require = 0)
    public void onDraw(CallbackInfo ci) {
        if (SleepBackground.LATEST_LOCK_FRAME) ci.cancel();
    }
}
