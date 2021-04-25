package com.github.jrsofty.imagedata.api.video;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import com.drew.imaging.ImageProcessingException;
import com.drew.imaging.mp4.Mp4MetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.mp4.Mp4Directory;
import com.github.jrsofty.imagedata.api.ImageDateExtractor;
import com.github.jrsofty.imagedata.api.MetaDataExtractionException;

public class Mp4MetaData implements ImageDateExtractor {

    private File imageFile;
    private Metadata metadata;
    private Mp4Directory mp4Directory;

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
        return this.mp4Directory.hasTagName(Mp4Directory.TAG_CREATION_TIME);
    }

    @Override
    public Optional<Date> getDefaultDate() {
        Date result = null;
        if (this.mp4Directory.hasTagName(Mp4Directory.TAG_CREATION_TIME)) {
            result = this.mp4Directory.getDate(Mp4Directory.TAG_CREATION_TIME);
        }
        return Optional.ofNullable(result);
    }

    private void readMetaDataInformationFromImage() throws MetaDataExtractionException {
        try {
            this.metadata = Mp4MetadataReader.readMetadata(this.imageFile);
        } catch (ImageProcessingException | IOException e) {
            throw new MetaDataExtractionException("Failed to extract metadata from file " + this.imageFile.getAbsolutePath(), e);
        }
        this.mp4Directory = this.metadata.getFirstDirectoryOfType(Mp4Directory.class);
    }

}
