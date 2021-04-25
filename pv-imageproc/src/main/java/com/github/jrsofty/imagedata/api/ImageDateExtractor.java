package com.github.jrsofty.imagedata.api;

import java.io.File;
import java.util.Date;
import java.util.Optional;

public interface ImageDateExtractor {

    void setFile(File file) throws MetaDataExtractionException;

    boolean hasOriginalDate();

    Optional<Date> getOriginalDate();

    boolean hasDigitalizedDate();

    Optional<Date> getDigitalizedDate();

    boolean hasDefaultDate();

    Optional<Date> getDefaultDate();
}
