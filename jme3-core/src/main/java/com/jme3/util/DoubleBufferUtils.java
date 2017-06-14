package com.jme3.util;

import java.nio.ByteOrder;
import java.nio.DoubleBuffer;

/**
 * Created by user on 6/14/2017.
 */
public class DoubleBufferUtils extends AbstractBaseUtils {

    /**
     * Create a new DoubleBuffer of the specified size.
     *
     * @param size
     *            required number of double to store.
     * @return the new DoubleBuffer
     */
    public static DoubleBuffer createDoubleBuffer(int size) {
        DoubleBuffer buf = AbstractBaseUtils.getAllocator().allocate(8 * size).order(ByteOrder.nativeOrder()).asDoubleBuffer();
        buf.clear();
        onBufferAllocated(buf);
        return buf;
    }

    /**
     * Create a new DoubleBuffer of an appropriate size to hold the specified
     * number of doubles only if the given buffer if not already the right size.
     *
     * @param buf
     *            the buffer to first check and rewind
     * @param size
     *            number of doubles that need to be held by the newly created
     *            buffer
     * @return the requested new DoubleBuffer
     */
    public static DoubleBuffer createDoubleBuffer(DoubleBuffer buf, int size) {
        if (buf != null && buf.limit() == size) {
            buf.rewind();
            return buf;
        }

        buf = createDoubleBuffer(size);
        return buf;
    }

    /**
     * Creates a new DoubleBuffer with the same contents as the given
     * DoubleBuffer. The new DoubleBuffer is separate from the old one and
     * changes are not reflected across. If you want to reflect changes,
     * consider using Buffer.duplicate().
     *
     * @param buf
     *            the DoubleBuffer to copy
     * @return the copy
     */
    public static DoubleBuffer clone(DoubleBuffer buf) {
        if (buf == null) {
            return null;
        }
        buf.rewind();

        DoubleBuffer copy;
        if (isDirect(buf)) {
            copy = createDoubleBuffer(buf.limit());
        } else {
            copy = DoubleBuffer.allocate(buf.limit());
        }
        copy.put(buf);

        return copy;
    }
}
