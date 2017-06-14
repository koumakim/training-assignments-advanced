package com.jme3.scene.shape;

/**
 * Created by user on 6/14/2017.
 */
public class CylinderCreator {

    private int axisSamples;

    private int radialSamples;

    private float radius;
    private float radius2;

    private float height;
    private boolean closed;
    private boolean inverted;


    public int getAxisSamples() {
        return axisSamples;
    }

    public void setAxisSamples(int axisSamples) {
        this.axisSamples = axisSamples;
    }

    public int getRadialSamples() {
        return radialSamples;
    }

    public void setRadialSamples(int radialSamples) {
        this.radialSamples = radialSamples;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getRadius2() {
        return radius2;
    }

    public void setRadius2(float radius2) {
        this.radius2 = radius2;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public boolean isInverted() {
        return inverted;
    }

    public void setInverted(boolean inverted) {
        this.inverted = inverted;
    }
}
