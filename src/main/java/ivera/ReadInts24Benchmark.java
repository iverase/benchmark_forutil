package ivera;

import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 25, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class ReadInts24Benchmark {

    @Benchmark
    public void readInts24Legacy(IntDecodeState state, Blackhole bh) {
        int i;
        for (i = 0; i < state.count - 7; i += 8) {
            long l1 = state.input.getLong();
            long l2 = state.input.getLong();
            long l3 = state.input.getLong();
            state.outputInts[i] = (int) (l1 >>> 40);
            state.outputInts[i + 1] = (int) (l1 >>> 16) & 0xffffff;
            state.outputInts[i + 2] = (int) (((l1 & 0xffff) << 8) | (l2 >>> 56));
            state.outputInts[i + 3] = (int) (l2 >>> 32) & 0xffffff;
            state.outputInts[i + 4] = (int) (l2 >>> 8) & 0xffffff;
            state.outputInts[i + 5] = (int) (((l2 & 0xff) << 16) | (l3 >>> 48));
            state.outputInts[i + 6] = (int) (l3 >>> 24) & 0xffffff;
            state.outputInts[i + 7] = (int) l3 & 0xffffff;
        }
        for (; i < state.count; ++i) {
            state.outputInts[i] = (Short.toUnsignedInt(state.input.getShort()) << 8) | Byte.toUnsignedInt(state.input.get());
        }
        bh.consume(state.outputInts);
    }

    @Benchmark
    public void readInts24Visitor(IntDecodeState state, Blackhole bh) {
        int i;
        for (i = 0; i < state.count - 7; i += 8) {
            long l1 = state.input.getLong();
            long l2 = state.input.getLong();
            long l3 = state.input.getLong();
            bh.consume((int) (l1 >>> 40));
            bh.consume((int) (l1 >>> 16) & 0xffffff);
            bh.consume((int) (((l1 & 0xffff) << 8) | (l2 >>> 56)));
            bh.consume((int) (l2 >>> 32) & 0xffffff);
            bh.consume((int) (l2 >>> 8) & 0xffffff);
            bh.consume((int) (((l2 & 0xff) << 16) | (l3 >>> 48)));
            bh.consume((int) (l3 >>> 24) & 0xffffff);
            bh.consume((int) l3 & 0xffffff);
        }
        for (; i < state.count; ++i) {
            bh.consume((Short.toUnsignedInt(state.input.getShort()) << 8) | Byte.toUnsignedInt(state.input.get()));
        }
    }

    @Benchmark
    public void readInts24ForUtil(IntDecodeState state, Blackhole bh) {
        decode24(state);
        bh.consume(state.outputInts);
    }

    @Benchmark
    public void readInts24ForUtilVisitor(IntDecodeState state, Blackhole bh) {
        decode24(state);
        for (int i = 0; i < state.count; i++) {
            bh.consume(state.outputInts[i]);
        }
    }

    void decode24(IntDecodeState state)  {
        final int quarterLen = state.count >>> 2;
        final int quarterLen3 = quarterLen * 3;
        state.input.asIntBuffer().get(state.tmpInts, 0, quarterLen3);
        for (int i = 0; i < quarterLen3; ++i) {
            state.outputInts[i] = state.tmpInts[i] >>> 8;
        }
        for (int i = 0; i < quarterLen; i++) {
            state.outputInts[i + quarterLen3] =
                    ((state.tmpInts[i] & 0xFF) << 16)
                            | ((state.tmpInts[i + quarterLen] & 0xFF) << 8)
                            | (state.tmpInts[i + quarterLen * 2] & 0xFF);
        }
        int remainder = state.count & 0x3;
        if (remainder > 0) {
            state.input.asIntBuffer().get(state.outputInts, quarterLen << 2, remainder);
        }
    }

    public static void main(String[] args) throws Exception {
        Main.main(args);
    }
}
