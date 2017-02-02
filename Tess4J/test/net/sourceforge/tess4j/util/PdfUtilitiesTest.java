/*
 * Copyright 2014 Quan Nguyen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sourceforge.tess4j.util;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PdfUtilitiesTest {

    private static final Logger logger = LoggerFactory.getLogger(new LoggHelper().toString());
    private final String testResourcesDataPath = "test/resources/test-data";

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of convertPdf2Tiff method, of class PdfUtilities.
     */
    @Test
    public void testConvertPdf2Tiff() throws Exception {
        logger.info("convertPdf2Tiff");
        File inputPdfFile = new File(testResourcesDataPath, "eurotext.pdf");
        File result = PdfUtilities.convertPdf2Tiff(inputPdfFile);
        result.deleteOnExit();
        assertTrue(result.exists());
    }

    /**
     * Test of convertPdf2Png method, of class PdfUtilities.
     */
    @Test
    public void testConvertPdf2Png() {
        logger.info("convertPdf2Png");
        File inputPdfFile = new File(testResourcesDataPath, "eurotext.pdf");
        File[] results = PdfUtilities.convertPdf2Png(inputPdfFile);
        for (File result : results) {
            result.deleteOnExit();
        }
        assertTrue(results.length > 0);
    }

    /**
     * Test of splitPdf method, of class PdfUtilities.
     */
    @Test
    public void testSplitPdf() {
        logger.info("splitPdf");
        File inputPdfFile = new File(testResourcesDataPath, "multipage-pdf.pdf");
        File outputPdfFile = new File("test/test-results/multipage-pdf_splitted.pdf");
        int startPage = 2;
        int endPage = 3;
        int expResult = 2;
        PdfUtilities.splitPdf(inputPdfFile, outputPdfFile, startPage, endPage);
        int pageCount = PdfUtilities.getPdfPageCount(outputPdfFile);
        assertEquals(expResult, pageCount);
    }

    /**
     * Test of getPdfPageCount method, of class PdfUtilities.
     */
    @Test
    public void testGetPdfPageCount() {
        logger.info("getPdfPageCount");
        File inputPdfFile = new File(testResourcesDataPath, "multipage-pdf.pdf");
        int expResult = 5;
        int result = PdfUtilities.getPdfPageCount(inputPdfFile);
        assertEquals(expResult, result);
    }

    /**
     * Test of mergePdf method, of class PdfUtilities.
     */
    @Test
    public void testMergePdf() {
        logger.info("mergePdf");
        File pdfPartOne = new File(testResourcesDataPath, "eurotext.pdf");
        File pdfPartTwo = new File(testResourcesDataPath, "multipage-pdf.pdf");
        int expResult = 6;
        File outputPdfFile = new File("test/test-results", "multipage-pdf_merged.pdf");
        File[] inputPdfFiles = {pdfPartOne, pdfPartTwo};
        PdfUtilities.mergePdf(inputPdfFiles, outputPdfFile);
        assertEquals(expResult, PdfUtilities.getPdfPageCount(outputPdfFile));
    }

}