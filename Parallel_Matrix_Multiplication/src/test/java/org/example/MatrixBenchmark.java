package org.example;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime) // Measure average execution time
@OutputTimeUnit(TimeUnit.MILLISECONDS) // Output in milliseconds
@State(Scope.Benchmark) // Share state across benchmark methods
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS) // Warm-up iterations
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS) // Measurement iterations
@Fork(3) // Number of forks
public class MatrixBenchmark {

    @Param({"256", "512", "1024"}) // Matrix sizes to benchmark
    public int n;

    private double[][] matrixA;
    private double[][] matrixB;

    private ClassicMatrixMultiplication classicMultiplier;
    private ParallelMatrixMultiplication parallelMultiplier;
    private VectorizedMatrixMultiplication vectorizedMultiplier;

    @Setup(Level.Trial)
    public void setup() {
        Random random = new Random();

        // Initialize matrices
        matrixA = new double[n][n];
        matrixB = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrixA[i][j] = random.nextDouble();
                matrixB[i][j] = random.nextDouble();
            }
        }

        // Initialize multipliers
        classicMultiplier = new ClassicMatrixMultiplication();
        parallelMultiplier = new ParallelMatrixMultiplication();
        vectorizedMultiplier = new VectorizedMatrixMultiplication();
    }

    @Benchmark
    public double[][] testClassicMultiply() {
        return classicMultiplier.multiply(matrixA, matrixB);
    }

    @Benchmark
    public double[][] testParallelStreamsMultiply() {
        return parallelMultiplier.parallelStreamsMultiply(matrixA, matrixB);
    }

    @Benchmark
    public double[][] testParallelThreadsMultiply() {
        return ParallelMatrixMultiplication.parallelThreadsMultiply(matrixA, matrixB);
    }

    @Benchmark
    public double[][] testVectorizedMultiply() {
        return vectorizedMultiplier.vectorizedMatrixMultiply(matrixA, matrixB);
    }

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(MatrixBenchmark.class.getSimpleName())
                .forks(3) // Number of separate runs
                .warmupIterations(5) // Warm-up phase
                .measurementIterations(10) // Measurement phase
                .measurementTime(TimeValue.seconds(1)) // Duration of each measurement iteration
                .warmupTime(TimeValue.seconds(1)) // Duration of each warm-up iteration
                .output("benchmark_results.log") // Log output file
                .result("benchmark_results.json") // JSON result file
                .resultFormat(org.openjdk.jmh.results.format.ResultFormatType.JSON)
                .build();

        new Runner(opt).run();
    }
}
