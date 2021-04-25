package com.github.jrsofty.imagedata.api.image;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.github.jrsofty.imagedata.api.ImageDateExtractor;
import com.github.jrsofty.imagedata.api.MetaDataExtractionException;

public class JpegMetaData implements ImageDateExtractor {

    private File imageFile;
    private Metadata metadata;
    private ExifSubIFDDirectory ifd;
    private ExifIFD0Directory ifd0;

    @Override
    public void setFile(final File file) throws MetaDataExtractionException {
        this.imageFile = file;
        this.readMetaDataInformationFromImage();
    }

    @Override
    public boolean hasOriginalDate() {
        boolean result = false;
        if (this.ifd.containsTag(ExifDirectoryBase.TAG_DATETIME_ORIGINAL)) {
            result = true;
        } else if (this.ifd0.containsTag(ExifDirectoryBase.TAG_DATETIME_ORIGINAL)) {
            result = true;
        }
        return result;
    }

    @Override
    public Optional<Date> getOriginalDate() {
        Date result = null;
        if (this.ifd.containsTag(ExifDirectoryBase.TAG_DATETIME_ORIGINAL)) {
            result = this.ifd.getDate(ExifDirectoryBase.TAG_DATETIME_ORIGINAL);
        } else if (this.ifd0.containsTag(ExifDirectoryBase.TAG_DATETIME_ORIGINAL)) {
            result = this.ifd0.getDate(ExifDirectoryBase.TAG_DATETIME_ORIGINAL);
        }
        return Optional.ofNullable(result);
    }

    @Override
    public boolean hasDigitalizedDate() {
        boolean result = false;
        if (this.ifd.containsTag(ExifDirectoryBase.TAG_DATETIME_DIGITIZED)) {
            result = true;
        } else if (this.ifd0.containsTag(ExifDirectoryBase.TAG_DATETIME_DIGITIZED)) {
            result = true;
        }
        return result;
    }

    @Override
    public Optional<Date> getDigitalizedDate() {
        Date result = null;
        if (this.ifd.containsTag(ExifDirectoryBase.TAG_DATETIME_DIGITIZED)) {
            result = this.ifd.getDate(ExifDirectoryBase.TAG_DATETIME_DIGITIZED);
        } else if (this.ifd0.containsTag(ExifDirectoryBase.TAG_DATETIME_DIGITIZED)) {
            result = this.ifd0.getDate(ExifDirectoryBase.TAG_DATETIME_DIGITIZED);
        }

        return Optional.ofNullable(result);
    }

    @Override
    public boolean hasDefaultDate() {
        boolean result = false;
        if (this.ifd.containsTag(ExifDirectoryBase.TAG_DATETIME)) {
            result = true;
        } else if (this.ifd0.containsTag(ExifDirectoryBase.TAG_DATETIME)) {
            result = true;
        }
        return result;
    }

    @Override
    public Optional<Date> getDefaultDate() {
        Date result = null;
        if (this.ifd.containsTag(ExifDirectoryBase.TAG_DATETIME)) {
            result = this.ifd.getDate(ExifDirectoryBase.TAG_DATETIME);
        } else if (this.ifd0.containsTag(ExifDirectoryBase.TAG_DATETIME)) {
            result = this.ifd0.getDate(ExifDirectoryBase.TAG_DATETIME);
        }

        return Optional.ofNullable(result);
    }

    private void readMetaDataInformationFromImage() throws MetaDataExtractionException {
        try {
            this.metadata = JpegMetadataReader.readMetadata(this.imageFile);
        } catch (JpegProcessingException | IOException e) {
            throw new MetaDataExtractionException("Failed to extract metadata from file " + this.imageFile.getAbsolutePath(), e);
        }
        this.ifd0 = this.metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
        this.ifd = this.metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
    }

}
