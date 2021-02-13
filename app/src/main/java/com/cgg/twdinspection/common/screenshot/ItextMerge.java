package com.cgg.twdinspection.common.screenshot;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

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
    private String filePath, filePath1, filePath2;
    private Context context;
    private File destFile = null;
    private PDFMergeListener listener;


    public ItextMerge(Context context, String filePath, String filePath1, String filePath2, PDFMergeListener listener) {

        this.filePath = filePath;
        this.filePath1 = filePath1;
        this.filePath2 = filePath2;
        this.context = context;
        this.listener = listener;

        new MergePDFAsync(listener).execute();
    }

    public interface PDFMergeListener {

        void pdfMergeSuccess();

        void pdfMergeFailure(final Exception exception);
    }

    @SuppressLint("StaticFieldLeak")
    private class MergePDFAsync extends AsyncTask<Void, Void, Boolean> {

        private Exception mException;
        boolean flag = false;
        private PDFMergeListener listener;

        MergePDFAsync(PDFMergeListener listener) {
            this.listener = listener;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                cretateDestFile();
                flag = mergeFiles();
            } catch (Exception exception) {
                mException = exception;
                return flag;
            }
            return flag;
        }

        @Override
        protected void onPostExecute(Boolean flag) {
            super.onPostExecute(flag);
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
        }

//        private File savePDFDocumentToStorage(final PdfDocument pdfDocument) throws
//                IOException {
//            FileOutputStream fos = null;
//            // Create file.
//            File pdfFile = null;
//            if (mFilePath == null || mFilePath.isEmpty()) {
//                pdfFile = File.createTempFile(Long.toString(new Date().getTime()), "pdf");
//            } else {
//                pdfFile = new File(mFilePath);
//            }
//
//            //Create parent directories
//            File parentFile = pdfFile.getParentFile();
//            if (parentFile != null && !parentFile.exists() && !parentFile.mkdirs()) {
//                throw new IllegalStateException("Couldn't create directory: " + parentFile);
//            }
//            boolean fileExists = pdfFile.exists();
//            // If File already Exists. delete it.
//            if (fileExists) {
//                fileExists = !pdfFile.delete();
//            }
//            try {
//                if (!fileExists) {
//                    // Create New File.
//                    fileExists = pdfFile.createNewFile();
//                }
//
//                if (fileExists) {
//                    // Write PDFDocument to the file.
//                    fos = new FileOutputStream(pdfFile);
//                    pdfDocument.writeTo(fos);
//
//                    //Close output stream
//                    fos.close();
//
//                    // close the document
//                    pdfDocument.close();
//                }
//                return pdfFile;
//            } catch (IOException exception) {
//                mException = exception;
//                exception.printStackTrace();
//                if (fos != null) {
//                    fos.close();
//                }
//                throw exception;
//            }
//        }
    }

    private void cretateDestFile() {

//        String directory_path = context.getExternalFilesDir(null)
//                + "/" + "CTW/Schools/";
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