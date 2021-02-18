package com.cgg.twdinspection.common.screenshot;

import com.cgg.twdinspection.common.application.TWDApplication;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class ItextMerge {
    private final String filePath;
    private final String filePath1;
    private final String filePath2;
    private File destFile = null;
    private boolean flag = false;
    private Exception mException;

    public ItextMerge(String filePath, String filePath1, String filePath2, PDFMergeListener listener) {
        this.filePath = filePath;
        this.filePath1 = filePath1;
        this.filePath2 = filePath2;

        TWDApplication.getExecutorService().execute(() -> {
            try {
                cretateDestFile();
                flag = mergeFiles();
            } catch (Exception exception) {
                mException = exception;
            }
            //Background work here
            TWDApplication.getHandler().post(() -> {
                if (flag) {
                    //Send Success callback.
                    File file1 = new File(filePath1);
                    file1.delete();

                    File file2 = new File(filePath2);
                    file2.delete();

                    listener.pdfMergeSuccess();
                } else {
                    //Send Error callback.
                    listener.pdfMergeFailure(mException);
                }
                //UI Thread work here
            });
        });
    }

    public interface PDFMergeListener {
        void pdfMergeSuccess();

        void pdfMergeFailure(final Exception exception);
    }

    private void cretateDestFile() {
        String path;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            path = filePath;
        } else {
            path = filePath + ".pdf";
        }

        if (filePath.isEmpty()) {
            try {
                destFile = File.createTempFile(filePath, "pdf");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            destFile = new File(path);
        }

        assert destFile != null;
        File parentFile = destFile.getParentFile();
        if (parentFile != null && !parentFile.exists() && !parentFile.mkdirs()) {
            throw new IllegalStateException("Couldn't create directory: " + parentFile);
        }
        boolean fileExists = destFile.exists();
        if (fileExists) {
            fileExists = !destFile.delete();
        }
        try {
            if (!fileExists) {
                fileExists = destFile.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean mergeFiles() {
        boolean flag = false;
        List<InputStream> list = new ArrayList<InputStream>();
        try {
            // Source pdfs
            list.add(new FileInputStream(new File(filePath1)));
            list.add(new FileInputStream(new File(filePath2)));

            // Resulting pdf
            OutputStream out = new FileOutputStream(destFile);

            flag = doMerge(list, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    private boolean doMerge(List<InputStream> list, OutputStream outputStream)
            throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();
        PdfContentByte cb = writer.getDirectContent();

        for (InputStream in : list) {
            PdfReader reader = new PdfReader(in);
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                document.newPage();
                //import the page from source pdf
                PdfImportedPage page = writer.getImportedPage(reader, i);
                //add the page to the destination pdf
                cb.addTemplate(page, 0, 0);
            }
        }
        outputStream.flush();
        document.close();
        outputStream.close();
        return true;
    }
}