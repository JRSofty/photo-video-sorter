package com.github.jrsofty.imagedata.api;

import java.io.File;

public class MetaDataExtractionException extends Exception {

    private static final long serialVersionUID = 7780812309983172218L;

    public MetaDataExtractionException(final File file) {
        super(String.format("Could not extract the requested information from: %s", file.getAbsolutePath()));
    }

    public MetaDataExtractionException(final File file, final Throwable t) {
        super(String.format("Could not extract the requested information from: %s", file.getAbsolutePath()), t);
    }

    public MetaDataExtractionException(final String message) {
        super(message);
    }

    public MetaDataExtractionException(final String message, final Throwable t) {
        super(message, t);
    }
}
