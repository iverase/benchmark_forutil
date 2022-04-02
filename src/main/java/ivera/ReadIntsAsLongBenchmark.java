package ivera;

import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 25, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class ReadIntsAsLongBenchmark {

    @Benchmark
    public void readIntsLegacyLong1(IntDecodeState state, Blackhole bh) {
        int i;
        for (i = 0; i < state.count - 1; i += 2) {
            long l1 = state.input.getLong();
            state.outputInts[i] = (int) (l1 >>> 32);
            state.outputInts[i + 1] = (int) l1;
        }
        for (; i < state.count; i++) {
            state.outputInts[i] = state.input.getInt();
        }
        bh.consume(state.outputInts);
    }

    @Benchmark
    public void readIntsLegacyLong2(IntDecodeState state, Blackhole bh) {
        int i;
        for (i = 0; i < state.count - 3; i += 4) {
            long l1 = state.input.getLong();
            long l2 = state.input.getLong();
            state.outputInts[i] = (int) (l1 >>> 32);
            state.outputInts[i + 1] = (int) l1;
            state.outputInts[i + 2] = (int) (l2 >>> 32);
            state.outputInts[i + 3] = (int) l2;
        }
        for (; i < state.count; i++) {
            state.outputInts[i] = state.input.getInt();
        }
        bh.consume(state.outputInts);
    }

    @Benchmark
    public void readIntsLegacyLong3(IntDecodeState state, Blackhole bh) {
        int i;
        for (i = 0; i < state.count - 5; i += 6) {
            long l1 = state.input.getLong();
            long l2 = state.input.getLong();
            long l3 = state.input.getLong();
            state.outputInts[i] = (int) (l1 >>> 32);
            state.outputInts[i + 1] = (int) l1;
            state.outputInts[i + 2] = (int) (l2 >>> 32);
            state.outputInts[i + 3] = (int) l2;
            state.outputInts[i + 4] = (int) (l3 >>> 32);
            state.outputInts[i + 5] = (int) l3;
        }
        for (; i < state.count; i++) {
            state.outputInts[i] = state.input.getInt();
        }
        bh.consume(state.outputInts);
    }

    @Benchmark
    public void readIntsLegacyLong4(IntDecodeState state, Blackhole bh) {
        int i;
        for (i = 0; i < state.count - 7; i += 8) {
            long l1 = state.input.getLong();
            long l2 = state.input.getLong();
            long l3 = state.input.getLong();
            long l4 = state.input.getLong();
            state.outputInts[i] = (int) (l1 >>> 32);
            state.outputInts[i + 1] = (int) l1;
            state.outputInts[i + 2] = (int) (l2 >>> 32);
            state.outputInts[i + 3] = (int) l2;
            state.outputInts[i + 4] = (int) (l3 >>> 32);
            state.outputInts[i + 5] = (int) l3;
            state.outputInts[i + 6] = (int) (l4 >>> 32);
            state.outputInts[i + 7] = (int) l4;
        }
        for (; i < state.count; i++) {
            state.outputInts[i] = state.input.getInt();
        }
        bh.consume(state.outputInts);
    }

    @Benchmark
    public void readIntsLegacyLongVisitor1(IntDecodeState state, Blackhole bh) {
        int i;
        for (i = 0; i < state.count - 1; i += 2) {
            long l1 = state.input.getLong();
            bh.consume((int) (l1 >>> 32));
            bh.consume((int) l1);
        }
        for (; i < state.count; i++) {
            bh.consume(state.input.getInt());
        }
    }

    @Benchmark
    public void readIntsLegacyLongVisitor2(IntDecodeState state, Blackhole bh) {
        int i;
        for (i = 0; i < state.count - 3; i += 4) {
            long l1 = state.input.getLong();
            long l2 = state.input.getLong();
            bh.consume((int) (l1 >>> 32));
            bh.consume((int) l1);
            bh.consume((int) (l2 >>> 32));
            bh.consume((int) l2);
        }
        for (; i < state.count; i++) {
            bh.consume(state.input.getInt());
        }
    }

    @Benchmark
    public void readIntsLegacyLongVisitor3(IntDecodeState state, Blackhole bh) {
        int i;
        for (i = 0; i < state.count - 5; i += 6) {
            long l1 = state.input.getLong();
            long l2 = state.input.getLong();
            long l3 = state.input.getLong();
            bh.consume((int) (l1 >>> 32));
            bh.consume((int) l1);
            bh.consume((int) (l2 >>> 32));
            bh.consume((int) l2);
            bh.consume((int) (l3 >>> 32));
            bh.consume((int) l3);
        }
        for (; i < state.count; i++) {
            bh.consume(state.input.getInt());
        }
    }

    @Benchmark
    public void readIntsLegacyLongVisitor4(IntDecodeState state, Blackhole bh) {
        int i;
        for (i = 0; i < state.count - 7; i += 8) {
            long l1 = state.input.getLong();
            long l2 = state.input.getLong();
            long l3 = state.input.getLong();
            long l4 = state.input.getLong();
            bh.consume((int) (l1 >>> 32));
            bh.consume((int) l1);
            bh.consume((int) (l2 >>> 32));
            bh.consume((int) l2);
            bh.consume((int) (l3 >>> 32));
            bh.consume((int) l3);
            bh.consume((int) (l4 >>> 32));
            bh.consume((int) l4);
        }
        for (; i < state.count; i++) {
            bh.consume(state.input.getInt());
        }
    }
    

    public static void main(String[] args) throws Exception {
        Main.main(args);
    }
}
