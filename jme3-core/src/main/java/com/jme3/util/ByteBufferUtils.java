package com.jme3.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by user on 6/14/2017.
 */
public class ByteBufferUtils extends AbstractBaseUtils {

    /**
     * Create a new ByteBuffer of the specified size.
     *
     * @param size
     *            required number of ints to store.
     * @return the new IntBuffer
     */
    public static ByteBuffer createByteBuffer(int size) {
        ByteBuffer buf = AbstractBaseUtils.getAllocator().allocate(size).order(ByteOrder.nativeOrder());
        buf.clear();
        onBufferAllocated(buf);
        return buf;
    }

    /**
     * Create a new ByteBuffer of an appropriate size to hold the specified
     * number of ints only if the given buffer if not already the right size.
     *
     * @param buf
     *            the buffer to first check and rewind
     * @param size
     *            number of bytes that need to be held by the newly created
     *            buffer
     * @return the requested new IntBuffer
     */
    public static ByteBuffer createByteBuffer(ByteBuffer buf, int size) {
        if (buf != null && buf.limit() == size) {
            buf.rewind();
            return buf;
        }

        buf = createByteBuffer(size);
        return buf;
    }

    public static ByteBuffer createByteBuffer(byte... data) {
        ByteBuffer bb = createByteBuffer(data.length);
        bb.put(data);
        bb.flip();
        return bb;
    }

    public static ByteBuffer createByteBuffer(String data) {
        try {
            byte[] bytes = data.getBytes("UTF-8");
            ByteBuffer bb = createByteBuffer(bytes.length);
            bb.put(bytes);
            bb.flip();
            return bb;
        } catch (UnsupportedEncodingException ex) {
            throw new UnsupportedOperationException(ex);
        }
    }

    /**
     * Creates a new ByteBuffer with the same contents as the given ByteBuffer.
     * The new ByteBuffer is seperate from the old one and changes are not
     * reflected across. If you want to reflect changes, consider using
     * Buffer.duplicate().
     *
     * @param buf
     *            the ByteBuffer to copy
     * @return the copy
     */
    public static ByteBuffer clone(ByteBuffer buf) {
        if (buf == null) {
            return null;
        }
        buf.rewind();

        ByteBuffer copy;
        if (isDirect(buf)) {
            copy = createByteBuffer(buf.limit());
        } else {
            copy = ByteBuffer.allocate(buf.limit());
        }
        copy.put(buf);

        return copy;
    }

    public static ByteBuffer ensureLargeEnough(ByteBuffer buffer, int required) {
        if (buffer != null) {
            buffer.limit(buffer.capacity());
        }
        if (buffer == null || (buffer.remaining() < required)) {
            int position = (buffer != null ? buffer.position() : 0);
            ByteBuffer newVerts = createByteBuffer(position + required);
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
