package com.jme3.util;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.nio.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by user on 6/14/2017.
 */
public class AbstractBaseUtils {

    private static BufferAllocator allocator = new PrimitiveAllocator();
    private static boolean used;


    protected static boolean trackDirectMemory = false;
    protected static ReferenceQueue<Buffer> removeCollected = new ReferenceQueue<Buffer>();
    protected static ConcurrentHashMap<BufferInfo, BufferInfo> trackedBuffers = new ConcurrentHashMap<BufferInfo, BufferInfo>();
    static ClearReferences cleanupthread;

    /**
     * Set it to true if you want to enable direct memory tracking for debugging
     * purpose. Default is false. To print direct memory usage use
     * BufferUtils.printCurrentDirectMemory(StringBuilder store);
     *
     * @param enabled
     */
    public static void setTrackDirectMemoryEnabled(boolean enabled) {
        trackDirectMemory = enabled;
    }

    static {
        try {
            allocator = new ReflectionAllocator();
        } catch (Throwable t) {
            t.printStackTrace();
            System.err.println("Error using ReflectionAllocator");
        }
    }

    public static BufferAllocator getAllocator() {
        return allocator;
    }

    /**
     * Warning! do only set this before JME is started!
     */
    public static void setAllocator(BufferAllocator allocator) {
        if (used) {
            throw new IllegalStateException(
                    "An Buffer was already allocated, since it is quite likely that other dispose methods will create native dangling pointers or other fun things, this is forbidden to be changed at runtime");
        }
        AbstractBaseUtils.allocator = allocator;
    }

    public static void setUsed(boolean used) {
        AbstractBaseUtils.used = used;
    }

    /*
 * FIXME when java 1.5 supprt is dropped - replace calls to this method with
 * Buffer.isDirect
 *
 * Buffer.isDirect() is only java 6. Java 5 only have this method on Buffer
 * subclasses : FloatBuffer, IntBuffer, ShortBuffer,
 * ByteBuffer,DoubleBuffer, LongBuffer. CharBuffer has been excluded as we
 * don't use it.
 *
 */
    public static boolean isDirect(Buffer buf) {
        if (buf instanceof FloatBuffer) {
            return ((FloatBuffer) buf).isDirect();
        }
        if (buf instanceof IntBuffer) {
            return ((IntBuffer) buf).isDirect();
        }
        if (buf instanceof ShortBuffer) {
            return ((ShortBuffer) buf).isDirect();
        }
        if (buf instanceof ByteBuffer) {
            return ((ByteBuffer) buf).isDirect();
        }
        if (buf instanceof DoubleBuffer) {
            return ((DoubleBuffer) buf).isDirect();
        }
        if (buf instanceof LongBuffer) {
            return ((LongBuffer) buf).isDirect();
        }
        throw new UnsupportedOperationException(" BufferUtils.isDirect was called on " + buf.getClass().getName());
    }

    public static void onBufferAllocated(Buffer buffer) {
        AbstractBaseUtils.setUsed(true);
        if (trackDirectMemory) {
            if (cleanupthread == null) {
                cleanupthread = new BufferUtils.ClearReferences();
                cleanupthread.start();
            }
            if (buffer instanceof ByteBuffer) {
                BufferInfo info = new BufferInfo(ByteBuffer.class, buffer.capacity(), buffer,
                        removeCollected);
                trackedBuffers.put(info, info);
            } else if (buffer instanceof FloatBuffer) {
                BufferInfo info = new BufferInfo(FloatBuffer.class, buffer.capacity() * 4, buffer,
                        removeCollected);
                trackedBuffers.put(info, info);
            } else if (buffer instanceof IntBuffer) {
                BufferInfo info = new BufferInfo(IntBuffer.class, buffer.capacity() * 4, buffer,
                        removeCollected);
                trackedBuffers.put(info, info);
            } else if (buffer instanceof ShortBuffer) {
                BufferInfo info = new BufferInfo(ShortBuffer.class, buffer.capacity() * 2, buffer,
                        removeCollected);
                trackedBuffers.put(info, info);
            } else if (buffer instanceof DoubleBuffer) {
                BufferInfo info = new BufferInfo(DoubleBuffer.class, buffer.capacity() * 8, buffer,
                        removeCollected);
                trackedBuffers.put(info, info);
            }

        }
    }


    protected static class BufferInfo extends PhantomReference<Buffer> {

        private Class type;
        private int size;

        public BufferInfo(Class type, int size, Buffer referent, ReferenceQueue<? super Buffer> q) {
            super(referent, q);
            this.type = type;
            this.size = size;
        }

        public Class getType() {
            return type;
        }

        public void setType(Class type) {
            this.type = type;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

    protected static class ClearReferences extends Thread {

        ClearReferences() {
            this.setDaemon(true);
        }

        @Override
        public void run() {
            try {
                while (true) {
                    Reference<? extends Buffer> toclean = removeCollected.remove();
                    trackedBuffers.remove(toclean);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates a clone of the given buffer. The clone's capacity is equal to the
     * given buffer's limit.
     *
     * @param buf
     *            The buffer to clone
     * @return The cloned buffer
     */
    public static Buffer clone(Buffer buf) {
        if (buf instanceof FloatBuffer) {
            return clone((FloatBuffer) buf);
        } else if (buf instanceof ShortBuffer) {
            return clone((ShortBuffer) buf);
        } else if (buf instanceof ByteBuffer) {
            return clone((ByteBuffer) buf);
        } else if (buf instanceof IntBuffer) {
            return clone((IntBuffer) buf);
        } else if (buf instanceof DoubleBuffer) {
            return clone((DoubleBuffer) buf);
        } else {
            throw new UnsupportedOperationException();
        }
    }
}
