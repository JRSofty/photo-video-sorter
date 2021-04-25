package com.github.jrsofty.imagedata.api.video;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import com.drew.imaging.ImageProcessingException;
import com.drew.imaging.quicktime.QuickTimeMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.mov.QuickTimeDirectory;
import com.github.jrsofty.imagedata.api.ImageDateExtractor;
import com.github.jrsofty.imagedata.api.MetaDataExtractionException;

public class MovMetaData implements ImageDateExtractor {

    private File imageFile;
    private Metadata metadata;
    private QuickTimeDirectory qtDirectory;

    @Override
    public void setFile(final File file) throws MetaDataExtractionException {
        this.imageFile = file;
        this.readMetaDataInformationFromImage();
    }

    @Override
    public boolean hasOriginalDate() {
        return false;
    }

    @Override
    public Optional<Date> getOriginalDate() {
        return Optional.empty();
    }

    @Override
    public boolean hasDigitalizedDate() {
        return false;
    }

    @Override
    public Optional<Date> getDigitalizedDate() {
        return Optional.empty();
    }

    @Override
    public boolean hasDefaultDate() {
        return this.qtDirectory.containsTag(QuickTimeDirectory.TAG_CREATION_TIME);
    }

    @Override
    public Optional<Date> getDefaultDate() {
        Date result = null;
        if (this.qtDirectory.containsTag(QuickTimeDirectory.TAG_CREATION_TIME)) {
            result = this.qtDirectory.getDate(QuickTimeDirectory.TAG_CREATION_TIME);
        }
        return Optional.ofNullable(result);
    }

    private void readMetaDataInformationFromImage() throws MetaDataExtractionException {
        try {
            this.metadata = QuickTimeMetadataReader.readMetadata(this.imageFile);
        } catch (ImageProcessingException | IOException e) {
            throw new MetaDataExtractionException("Failed to extract metadata from file " + this.imageFile.getAbsolutePath(), e);
        }
        this.qtDirectory = this.metadata.getFirstDirectoryOfType(QuickTimeDirectory.class);
        if (this.qtDirectory == null) {
            throw new MetaDataExtractionException("The quicktime reader cannot find the appropriate metadata directory");
        }
    }

}
