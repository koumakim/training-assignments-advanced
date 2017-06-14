package com.jme3.util;

import java.nio.ByteOrder;
import java.nio.IntBuffer;

/**
 * Created by user on 6/14/2017.
 */
public class IntBufferUtils extends AbstractBaseUtils{
    //// -- GENERAL INT ROUTINES -- ////
    /**
     * Create a new IntBuffer of the specified size.
     *
     * @param size
     *            required number of ints to store.
     * @return the new IntBuffer
     */
    public static IntBuffer createIntBuffer(int size) {
        IntBuffer buf = AbstractBaseUtils.getAllocator().allocate(4 * size).order(ByteOrder.nativeOrder()).asIntBuffer();
        buf.clear();
        onBufferAllocated(buf);
        return buf;
    }

    /**
     * Create a new IntBuffer of an appropriate size to hold the specified
     * number of ints only if the given buffer if not already the right size.
     *
     * @param buf
     *            the buffer to first check and rewind
     * @param size
     *            number of ints that need to be held by the newly created
     *            buffer
     * @return the requested new IntBuffer
     */
    public static IntBuffer createIntBuffer(IntBuffer buf, int size) {
        if (buf != null && buf.limit() == size) {
            buf.rewind();
            return buf;
        }

        buf = createIntBuffer(size);
        return buf;
    }

    /**
     * Creates a new IntBuffer with the same contents as the given IntBuffer.
     * The new IntBuffer is separate from the old one and changes are not
     * reflected across. If you want to reflect changes, consider using
     * Buffer.duplicate().
     *
     * @param buf
     *            the IntBuffer to copy
     * @return the copy
     */
    public static IntBuffer clone(IntBuffer buf) {
        if (buf == null) {
            return null;
        }
        buf.rewind();

        IntBuffer copy;
        if (isDirect(buf)) {
            copy = createIntBuffer(buf.limit());
        } else {
            copy = IntBuffer.allocate(buf.limit());
        }
        copy.put(buf);

        return copy;
    }
}
