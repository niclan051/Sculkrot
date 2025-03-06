package com.example.sculkrot.utils;

import net.minecraft.world.phys.Vec3;

import java.util.function.Consumer;

public class RayUtils {
    public static void performOnRay(Vec3 start, Vec3 end, double step, Consumer<Vec3> onIteration) {
        Vec3 diff = end.subtract(start);
        Vec3 diffNormalized = diff.normalize();
        for (double i = 0; i <= diff.length(); i += step) {
            Vec3 currentPos = start.add(diffNormalized.multiply(i, i, i));
            onIteration.accept(currentPos);
        }
    }
}
