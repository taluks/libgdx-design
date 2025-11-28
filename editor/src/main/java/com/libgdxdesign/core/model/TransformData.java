package com.libgdxdesign.core.model;

import com.badlogic.gdx.math.Vector2;

import java.util.List;

public record TransformData(List<ActorWrapper> actors,
                            Vector2 newPosition,
                            Vector2 newSize,
                            Vector2 originalPosition,
                            Vector2 originalSize) {
}
