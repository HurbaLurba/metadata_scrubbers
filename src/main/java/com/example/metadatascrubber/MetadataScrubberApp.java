package com.example.metadatascrubber;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.util.concurrent.Callable;

@Command(name = "metadata-scrubber", mixinStandardHelpOptions = true, version = "1.0",
         description = "Scrubs metadata from images and videos to remove origin evidence.")
public class MetadataScrubberApp implements Callable<Integer> {

    @Parameters(index = "0", description = "Input file or directory to process.")
    private File input;

    @Option(names = {"-o", "--output"}, description = "Output directory for scrubbed files.")
    private File outputDir;

    @Option(names = {"-r", "--recursive"}, description = "Process directories recursively.")
    private boolean recursive = false;

    @Override
    public Integer call() throws Exception {
        if (!input.exists()) {
            System.err.println("Input file or directory does not exist: " + input);
            return 1;
        }

        MetadataScrubber scrubber = new MetadataScrubber();
        scrubber.process(input, outputDir, recursive);
        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new MetadataScrubberApp()).execute(args);
        System.exit(exitCode);
    }
}
