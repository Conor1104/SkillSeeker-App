package com.zybooks.skillseekerapp;

import android.graphics.Path;

public class StarPath extends Path {
    public StarPath(float x, float y, float innerRadius, float outerRadius, int numPoints) {
        super();
        double section = 2.0 * Math.PI / numPoints;

        moveTo(
                (float)(x + outerRadius * Math.cos(0)),
                (float)(y + outerRadius * Math.sin(0))
        );

        for (int i = 1; i < numPoints * 2; i++) {
            double angle = section * i / 2.0;
            float radius = (i % 2 == 0) ? outerRadius : innerRadius;
            lineTo(
                    (float)(x + radius * Math.cos(angle)),
                    (float)(y + radius * Math.sin(angle))
            );
        }

        close();
    }
}
