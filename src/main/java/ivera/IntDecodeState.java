package ivera;

import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Random;

@State(Scope.Benchmark)
public class IntDecodeState {

    private FileChannel channel;
    ByteBuffer input;

    final int count  = 512;
    int[] tmpInts;
    int[] outputInts;

    @Setup(Level.Trial)
    public void setupTrial() throws IOException {
        Path path = Files.createTempFile("IntDecodeState", ".bench");
        try (FileChannel channel = FileChannel.open(path, StandardOpenOption.APPEND, StandardOpenOption.WRITE)) {
            byte[] data = new byte[count * Integer.BYTES];
            new Random(0).nextBytes(data);
            channel.write(ByteBuffer.wrap(data));
        }
        channel = FileChannel.open(path, StandardOpenOption.READ);
        input = channel.map(FileChannel.MapMode.READ_ONLY, 0, count * Integer.BYTES);
        input.order(ByteOrder.LITTLE_ENDIAN);

        outputInts = new int[count];
        tmpInts = new int[count];
    }

    @Setup(Level.Invocation)
    public void setupInvocation() {
        // Reset the position of the buffer
        input.position(0);
    }

    @TearDown(Level.Trial)
    public void tearDownTrial() throws IOException {
        input = null;
        if (channel != null) {
            channel.close();
        }
    }

}
