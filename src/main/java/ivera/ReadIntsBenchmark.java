package ivera;

import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 25, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class ReadIntsBenchmark {

    @Benchmark
    public void readIntsLegacy(IntDecodeState state, Blackhole bh) {
        for (int i = 0; i < state.count; i++) {
            state.outputInts[i] = state.input.getInt();
        }
        bh.consume(state.outputInts);
    }

    @Benchmark
    public void readIntsLegacyVisitor(IntDecodeState state, Blackhole bh) {
        for (int i = 0; i < state.count; i++) {
            bh.consume(state.input.getInt());
        }
    }

    @Benchmark
    public void readIntsForUtil(IntDecodeState state, Blackhole bh) {
        state.input.asIntBuffer().get(state.outputInts, 0, state.count);
        bh.consume(state.outputInts);
    }

    @Benchmark
    public void readIntsForUtilVisitor(IntDecodeState state, Blackhole bh) {
        state.input.asIntBuffer().get(state.outputInts, 0, state.count);
        for (int i = 0; i < state.count; i++) {
            bh.consume(state.outputInts[i]);
        }
    }

    public static void main(String[] args) throws Exception {
        Main.main(args);
    }
}
