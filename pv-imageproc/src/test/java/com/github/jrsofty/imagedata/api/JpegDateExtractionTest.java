package com.github.jrsofty.imagedata.api;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.jrsofty.imagedata.api.image.JpegMetaData;

class JpegDateExtractionTest {

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeEach
    void setUp() throws Exception {
    }

    @Test
    void testValidDefaultDateAvailable() throws MetaDataExtractionException {
        final File file = new File(this.getClass().getClassLoader().getResource("date-test-image/wrong-date.jpg").getFile());
        final JpegMetaData impl = new JpegMetaData();
        impl.setFile(file);
        final boolean result = impl.hasDefaultDate();

        Assertions.assertTrue(result);

    }

    @Test
    void testDefaultDate() throws MetaDataExtractionException, ParseException {

        final File file = new File(this.getClass().getClassLoader().getResource("date-test-image/wrong-date.jpg").getFile());
        final JpegMetaData impl = new JpegMetaData();
        impl.setFile(file);
        final Optional<Date> result = impl.getDefaultDate();

        Assertions.assertEquals("2008-07-15", this.sdf.format(result.get()));

    }

    @Test
    void testDigitizedDateNotAvailable() throws MetaDataExtractionException {
        final File file = new File(this.getClass().getClassLoader().getResource("date-test-image/wrong-date.jpg").getFile());
        final JpegMetaData impl = new JpegMetaData();
        impl.setFile(file);
        final boolean result = impl.hasDigitalizedDate();
        Assertions.assertFalse(result);
    }

}
