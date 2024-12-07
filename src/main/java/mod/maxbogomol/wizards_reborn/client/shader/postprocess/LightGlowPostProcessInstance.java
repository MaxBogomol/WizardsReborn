package mod.maxbogomol.wizards_reborn.client.shader.postprocess;

import mod.maxbogomol.fluffy_fur.client.shader.postprocess.PostProcessInstance;
import org.joml.Vector3f;

import java.util.function.BiConsumer;

public class LightGlowPostProcessInstance extends PostProcessInstance {
    public Vector3f start;
    public Vector3f end;
    public Vector3f color;
    public float radius = 10;
    public float intensity = 1;
    public float fade = 1;

    public LightGlowPostProcessInstance(Vector3f start, Vector3f end, Vector3f color) {
        this.start = start;
        this.end = end;
        this.color = color;
    }

    public LightGlowPostProcessInstance setRadius(float radius) {
        this.radius = radius;
        return this;
    }

    public LightGlowPostProcessInstance setIntensity(float intensity) {
        this.intensity = intensity;
        return this;
    }

    public LightGlowPostProcessInstance setFade(float fade) {
        this.fade = fade;
        return this;
    }

    @Override
    public void writeDataToBuffer(BiConsumer<Integer, Float> writer) {
        writer.accept(0, start.x());
        writer.accept(1, start.y());
        writer.accept(2, start.z());
        writer.accept(3, end.x());
        writer.accept(4, end.y());
        writer.accept(5, end.z());
        writer.accept(6, color.x());
        writer.accept(7, color.y());
        writer.accept(8, color.z());
        writer.accept(9, radius);
        writer.accept(10, intensity);
        writer.accept(11, fade);
    }
}
