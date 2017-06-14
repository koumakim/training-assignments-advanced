package com.jme3.util;

import com.jme3.math.*;

import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by user on 6/14/2017.
 */
public class FloatBufferUtils extends AbstractBaseUtils {
    //// -- GENERAL FLOAT ROUTINES -- ////
    /**
     * Create a new FloatBuffer of the specified size.
     *
     * @param size
     *            required number of floats to store.
     * @return the new FloatBuffer
     */
    public static FloatBuffer createFloatBuffer(int size) {
        FloatBuffer buf = AbstractBaseUtils.getAllocator().allocate(4 * size).order(ByteOrder.nativeOrder()).asFloatBuffer();
        buf.clear();
        onBufferAllocated(buf);
        return buf;
    }

    /**
     * Copies floats from one position in the buffer to another.
     *
     * @param buf
     *            the buffer to copy from/to
     * @param fromPos
     *            the starting point to copy from
     * @param toPos
     *            the starting point to copy to
     * @param length
     *            the number of floats to copy
     */
    public static void copyInternal(FloatBuffer buf, int fromPos, int toPos, int length) {
        float[] data = new float[length];
        buf.position(fromPos);
        buf.get(data);
        buf.position(toPos);
        buf.put(data);
    }

    /**
     * Creates a new FloatBuffer with the same contents as the given
     * FloatBuffer. The new FloatBuffer is separate from the old one and changes
     * are not reflected across. If you want to reflect changes, consider using
     * Buffer.duplicate().
     *
     * @param buf
     *            the FloatBuffer to copy
     * @return the copy
     */
    public static FloatBuffer clone(FloatBuffer buf) {
        if (buf == null) {
            return null;
        }
        buf.rewind();

        FloatBuffer copy;
        if (isDirect(buf)) {
            copy = createFloatBuffer(buf.limit());
        } else {
            copy = FloatBuffer.allocate(buf.limit());
        }
        copy.put(buf);

        return copy;
    }

    /**
     * Copies a Vector2f from one position in the buffer to another. The index
     * values are in terms of vector number (eg, vector number 0 is positions
     * 0-1 in the FloatBuffer.)
     *
     * @param buf
     *            the buffer to copy from/to
     * @param fromPos
     *            the index of the vector to copy
     * @param toPos
     *            the index to copy the vector to
     */
    public static void copyInternalVector2(FloatBuffer buf, int fromPos, int toPos) {
        copyInternal(buf, fromPos * 2, toPos * 2, 2);
    }

    /**
     * Copies a Vector3f from one position in the buffer to another. The index
     * values are in terms of vector number (eg, vector number 0 is positions
     * 0-2 in the FloatBuffer.)
     *
     * @param buf
     *            the buffer to copy from/to
     * @param fromPos
     *            the index of the vector to copy
     * @param toPos
     *            the index to copy the vector to
     */
    public static void copyInternalVector3(FloatBuffer buf, int fromPos, int toPos) {
        copyInternal(buf, fromPos * 3, toPos * 3, 3);
    }

    /**
     * Generate a new FloatBuffer using the given array of Vector3f objects. The
     * FloatBuffer will be 3 * data.length long and contain the vector data as
     * data[0].x, data[0].y, data[0].z, data[1].x... etc.
     *
     * @param data
     *            array of Vector3f objects to place into a new FloatBuffer
     */
    public static FloatBuffer createFloatBuffer(Vector3f... data) {
        if (data == null) {
            return null;
        }
        FloatBuffer buff = createFloatBuffer(3 * data.length);
        for (Vector3f element : data) {
            if (element != null) {
                buff.put(element.x).put(element.y).put(element.z);
            } else {
                buff.put(0).put(0).put(0);
            }
        }
        buff.flip();
        return buff;
    }

    /**
     * Generate a new FloatBuffer using the given array of Quaternion objects.
     * The FloatBuffer will be 4 * data.length long and contain the vector data.
     *
     * @param data
     *            array of Quaternion objects to place into a new FloatBuffer
     */
    public static FloatBuffer createFloatBuffer(Quaternion... data) {
        if (data == null) {
            return null;
        }
        FloatBuffer buff = createFloatBuffer(4 * data.length);
        for (Quaternion element : data) {
            if (element != null) {
                buff.put(element.getX()).put(element.getY()).put(element.getZ()).put(element.getW());
            } else {
                buff.put(0).put(0).put(0).put(0);
            }
        }
        buff.flip();
        return buff;
    }

    /**
     * Generate a new FloatBuffer using the given array of Vector4 objects. The
     * FloatBuffer will be 4 * data.length long and contain the vector data.
     *
     * @param data
     *            array of Vector4 objects to place into a new FloatBuffer
     */
    public static FloatBuffer createFloatBuffer(Vector4f... data) {
        if (data == null) {
            return null;
        }
        FloatBuffer buff = createFloatBuffer(4 * data.length);
        for (int x = 0; x < data.length; x++) {
            if (data[x] != null) {
                buff.put(data[x].getX()).put(data[x].getY()).put(data[x].getZ()).put(data[x].getW());
            } else {
                buff.put(0).put(0).put(0).put(0);
            }
        }
        buff.flip();
        return buff;
    }

    /**
     * Generate a new FloatBuffer using the given array of ColorRGBA objects.
     * The FloatBuffer will be 4 * data.length long and contain the color data.
     *
     * @param data
     *            array of ColorRGBA objects to place into a new FloatBuffer
     */
    public static FloatBuffer createFloatBuffer(ColorRGBA... data) {
        if (data == null) {
            return null;
        }
        FloatBuffer buff = createFloatBuffer(4 * data.length);
        for (int x = 0; x < data.length; x++) {
            if (data[x] != null) {
                buff.put(data[x].getRed()).put(data[x].getGreen()).put(data[x].getBlue()).put(data[x].getAlpha());
            } else {
                buff.put(0).put(0).put(0).put(0);
            }
        }
        buff.flip();
        return buff;
    }

    /**
     * Generate a new FloatBuffer using the given array of float primitives.
     *
     * @param data
     *            array of float primitives to place into a new FloatBuffer
     */
    public static FloatBuffer createFloatBuffer(float... data) {
        if (data == null) {
            return null;
        }
        FloatBuffer buff = createFloatBuffer(data.length);
        buff.clear();
        buff.put(data);
        buff.flip();
        return buff;
    }

    /**
     * Create a new FloatBuffer of an appropriate size to hold the specified
     * number of Vector3f object data.
     *
     * @param vertices
     *            number of vertices that need to be held by the newly created
     *            buffer
     * @return the requested new FloatBuffer
     */
    public static FloatBuffer createVector3Buffer(int vertices) {
        FloatBuffer vBuff = createFloatBuffer(3 * vertices);
        return vBuff;
    }

    /**
     * Create a new FloatBuffer of an appropriate size to hold the specified
     * number of Vector3f object data only if the given buffer if not already
     * the right size.
     *
     * @param buf
     *            the buffer to first check and rewind
     * @param vertices
     *            number of vertices that need to be held by the newly created
     *            buffer
     * @return the requested new FloatBuffer
     */
    public static FloatBuffer createVector3Buffer(FloatBuffer buf, int vertices) {
        if (buf != null && buf.limit() == 3 * vertices) {
            buf.rewind();
            return buf;
        }

        return createFloatBuffer(3 * vertices);
    }

    // // -- VECTOR2F METHODS -- ////
    /**
     * Generate a new FloatBuffer using the given array of Vector2f objects. The
     * FloatBuffer will be 2 * data.length long and contain the vector data as
     * data[0].x, data[0].y, data[1].x... etc.
     *
     * @param data
     *            array of Vector2f objects to place into a new FloatBuffer
     */
    public static FloatBuffer createFloatBuffer(Vector2f... data) {
        if (data == null) {
            return null;
        }
        FloatBuffer buff = createFloatBuffer(2 * data.length);
        for (Vector2f element : data) {
            if (element != null) {
                buff.put(element.x).put(element.y);
            } else {
                buff.put(0).put(0);
            }
        }
        buff.flip();
        return buff;
    }

    /**
     * Create a new FloatBuffer of an appropriate size to hold the specified
     * number of Vector2f object data.
     *
     * @param vertices
     *            number of vertices that need to be held by the newly created
     *            buffer
     * @return the requested new FloatBuffer
     */
    public static FloatBuffer createVector2Buffer(int vertices) {
        FloatBuffer vBuff = createFloatBuffer(2 * vertices);
        return vBuff;
    }

    /**
     * Create a new FloatBuffer of an appropriate size to hold the specified
     * number of Vector2f object data only if the given buffer if not already
     * the right size.
     *
     * @param buf
     *            the buffer to first check and rewind
     * @param vertices
     *            number of vertices that need to be held by the newly created
     *            buffer
     * @return the requested new FloatBuffer
     */
    public static FloatBuffer createVector2Buffer(FloatBuffer buf, int vertices) {
        if (buf != null && buf.limit() == 2 * vertices) {
            buf.rewind();
            return buf;
        }

        return createFloatBuffer(2 * vertices);
    }


    /**
     * Ensures there is at least the <code>required</code> number of entries
     * left after the current position of the buffer. If the buffer is too small
     * a larger one is created and the old one copied to the new buffer.
     *
     * @param buffer
     *            buffer that should be checked/copied (may be null)
     * @param required
     *            minimum number of elements that should be remaining in the
     *            returned buffer
     * @return a buffer large enough to receive at least the
     *         <code>required</code> number of entries, same position as the
     *         input buffer, not null
     */
    public static FloatBuffer ensureLargeEnough(FloatBuffer buffer, int required) {
        if (buffer != null) {
            buffer.limit(buffer.capacity());
        }
        if (buffer == null || (buffer.remaining() < required)) {
            int position = (buffer != null ? buffer.position() : 0);
            FloatBuffer newVerts = createFloatBuffer(position + required);
            if (buffer != null) {
                buffer.flip();
                newVerts.put(buffer);
                newVerts.position(position);
            }
            buffer = newVerts;
        }
        return buffer;
    }
}
