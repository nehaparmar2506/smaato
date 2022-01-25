package com.smaato.listener.util;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Component
public class FileUtil {

    @Value("${logging.file.path}")
    private String directory;

    @Value("${logging.file.name}")
    private String logFile;

    public void writeToLogFile(String textToAppend) throws IOException {
        FileUtils.touch(new File(directory , logFile));
        Path path = Paths.get(directory + File.separator + logFile);
        Files.write(path, System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
        Files.write(path, textToAppend.getBytes(), StandardOpenOption.APPEND);
    }
}
