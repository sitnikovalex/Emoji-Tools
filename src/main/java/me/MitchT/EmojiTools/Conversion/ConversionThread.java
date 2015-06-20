package me.MitchT.EmojiTools.Conversion;

import com.kylinworks.IPngConverter;
import me.MitchT.EmojiTools.GUI.ConversionDialog;

import java.io.File;
import java.io.IOException;

class ConversionThread extends Thread {

    private final File conversionFile;
    private final File outputDir;
    private final ConversionManager conversionManager;
    private final ConversionDialog conversionDialog;
    private boolean running = true;


    private long startTime = 0;

    private int totalFileNum = 0;
    private int currentFileNum = 0;

    public ConversionThread(File conversionFile, File outputDir, ConversionManager conversionManager, ConversionDialog conversionDialog) {
        this.conversionFile = conversionFile;
        this.outputDir = outputDir;
        this.conversionManager = conversionManager;
        this.conversionDialog = conversionDialog;
    }

    @Override
    public void run() {
        if (outputDir != null) {
            if (!outputDir.exists()) {
                outputDir.mkdir();
            }
        }

        startTime = System.currentTimeMillis();

        File[] files;

        if (conversionFile.isDirectory()) {
            files = conversionFile.listFiles();
        } else {
            files = new File[]{conversionFile};
        }

        totalFileNum = files.length;

        IPngConverter converter;

        try {
            for (File file : files) {
                if (!running) {
                    conversionDialog.dispose();
                    return;
                }

                updateProgress();

                currentFileNum++;

                if (file.getName().endsWith(".png")) {
                    System.out.println("Converting " + file.getName());
                    conversionDialog.appendToStatus("Converting " + file.getName());

                    converter = new IPngConverter(file, (outputDir != null) ? new File(outputDir, file.getName()) : file);
                    converter.convert();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        updateProgress();

        System.out.println("No more Emojis to convert! All done! :)");
        conversionManager.showMessageDialog("No more Emojis to convert! All done! :)");

        conversionDialog.dispose();
    }

    private void updateProgress() {
        conversionDialog.setProgress((int) (((double) currentFileNum / totalFileNum) * 100));
        conversionDialog.setTimeRemaining(currentFileNum, totalFileNum, System.currentTimeMillis(), startTime);
    }

    public void endConversion() {
        running = false;
    }
}
