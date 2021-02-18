package com.cgg.twdinspection.common.screenshot;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.View;

import com.cgg.twdinspection.common.application.TWDApplication;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@TargetApi(Build.VERSION_CODES.KITKAT)
public class PDFUtil {

    public static final double PDF_PAGE_WIDTH = 8.3 * 72;
    public static final double PDF_PAGE_HEIGHT = 11.7 * 72;
    private static PDFUtil sInstance;
    private Exception mException;
    private File savedPDFFile;

    private PDFUtil(Context context) {
    }

    public static PDFUtil getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PDFUtil(context);
        }
        return sInstance;
    }

    //Generates PDF for the given content views to the file path specified.
    public final void generatePDF(final List<View> contentViews, final String filePath,
                                  final PDFUtilListener mListener) {
        int currentApiVersion = Build.VERSION.SDK_INT;
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {

            TWDApplication.getExecutorService().execute(() -> {

                try {
                    // Create PDF Document.
                    PdfDocument pdfDocument = new PdfDocument();
                    // Write content to PDFDocument.
                    writePDFDocument(pdfDocument, contentViews);
                    // Save document to file.
                    savedPDFFile = savePDFDocumentToStorage(pdfDocument, filePath);
                } catch (Exception exception) {
                    mException = exception;
                }
                //Background work here
                TWDApplication.getHandler().post(() -> {
                    try {
                        if (savedPDFFile != null) {
                            //Send Success callback.
                            mListener.pdfGenerationSuccess(savedPDFFile);
                        } else {
                            //Send Error callback.
                            mListener.pdfGenerationFailure(mException);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //UI Thread work here
                });
            });
        } else {
            mListener.pdfGenerationFailure(
                    new APINotSupportedException("Generate PDF is not available for your android version."));
        }

    }

    //Listener used to send PDF Generation callback.
    public interface PDFUtilListener {
        // Called on the success of PDF Generation.
        void pdfGenerationSuccess(File savedPDFFile);

        // Called when PDF Generation failed.
        void pdfGenerationFailure(final Exception exception);
    }

    //Writes given PDFDocument using content views.
    private void writePDFDocument(final PdfDocument pdfDocument, final List<View> mContentViews) {

        for (int i = 0; i < mContentViews.size(); i++) {
            View contentView = mContentViews.get(i);

            if (contentView.getWidth() > 0 && contentView.getHeight() > 0) {
                PdfDocument.PageInfo pageInfo;
                // crate a page description
                pageInfo = new PdfDocument.PageInfo.Builder(595, 842, i + 1).create();
                // start a page
                PdfDocument.Page page = pdfDocument.startPage(pageInfo);
                // draw view on the page
                Canvas pageCanvas = page.getCanvas();
                pageCanvas.scale(1f, 1f);
                int pageWidth = pageCanvas.getWidth();
                int pageHeight = pageCanvas.getHeight();
                int measureWidth = View.MeasureSpec.makeMeasureSpec(pageWidth, View.MeasureSpec.EXACTLY);
                int measuredHeight = View.MeasureSpec.makeMeasureSpec(pageHeight, View.MeasureSpec.EXACTLY);
                contentView.measure(measureWidth, measuredHeight);
                contentView.layout(0, 0, pageWidth, pageHeight);
                //595, 842
                contentView.draw(pageCanvas);
                // finish the page
                pdfDocument.finishPage(page);
            }

        }
    }

    // Save PDFDocument to the File in the storage.
    private File savePDFDocumentToStorage(final PdfDocument pdfDocument, final String mFilePath) throws IOException {
        FileOutputStream fos = null;
        // Create file.
//            File file = null;
//            file = new File(context.getExternalFilesDir(null) + "/CTW/" + folderName + "/");
//            if (!file.exists()) {
//                file.mkdirs();
//            }

        File pdfFile = null;
        pdfFile = new File(mFilePath);

//            if (mFilePath == null || mFilePath.isEmpty()) {
//                pdfFile = File.createTempFile(Long.toString(new Date().getTime()), "pdf");
//            } else {
//                pdfFile = new File(mFilePath);
//            }

        //Create parent directories
        File parentFile = pdfFile.getParentFile();
        if (parentFile != null && !parentFile.exists() && !parentFile.mkdirs()) {
            throw new IllegalStateException("Couldn't create directory: " + parentFile);
        }
        boolean fileExists = pdfFile.exists();
        // If File already Exists. delete it.
        if (fileExists) {
            fileExists = !pdfFile.delete();
        }
        try {
            if (!fileExists) {
                // Create New File.
                fileExists = pdfFile.createNewFile();
            }

            if (fileExists) {
                // Write PDFDocument to the file.
                fos = new FileOutputStream(pdfFile);
                pdfDocument.writeTo(fos);

                //Close output stream
                fos.close();

                // close the document
                pdfDocument.close();
            }
            return pdfFile;
        } catch (IOException exception) {
            mException = exception;
            exception.printStackTrace();
            if (fos != null) {
                fos.close();
            }
            throw exception;
        }
    }

    // APINotSupportedException will be thrown If the device doesn't support PDF methods.
    private static class APINotSupportedException extends Exception {
        // mErrorMessage.
        private final String mErrorMessage;

        public APINotSupportedException(final String errorMessage) {
            this.mErrorMessage = errorMessage;
        }

        @Override
        public @NotNull String toString() {
            return "APINotSupportedException{" + "mErrorMessage='" + mErrorMessage + '\'' + '}';
        }
    }

    //Convert PDF to bitmap, only works on devices above LOLLIPOP
    public static ArrayList<Bitmap> pdfToBitmap(File pdfFile) throws IllegalStateException {
        if (pdfFile == null || !pdfFile.exists()) {
            throw new IllegalStateException("");
        }
        //throw new MyOPDException("PDF preview image cannot be generated in this device");
        //Toast.makeText( "PDF preview image cannot be generated in this device", Toast.LENGTH_SHORT).show();
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            return null;
        }

        ArrayList<Bitmap> bitmaps = new ArrayList<>();

        try {
            PdfRenderer renderer = new PdfRenderer(ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY));

            Bitmap bitmap;
            final int pageCount = renderer.getPageCount();
            for (int i = 0; i < pageCount; i++) {
                PdfRenderer.Page page = renderer.openPage(i);


                int width = page.getWidth();
                int height = page.getHeight();

                /* FOR HIGHER QUALITY IMAGES, USE:
                int width = context.getResources().getDisplayMetrics().densityDpi / 72 * page.getWidth();
                int height = context.getResources().getDisplayMetrics().densityDpi / 72 * page.getHeight();
                */

                bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

                bitmaps.add(bitmap);

                // close the page
                page.close();

            }

            // close the renderer
            renderer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return bitmaps;

    }

    public static String getPath(Context context, Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        cursor.close();
        return s;
    }

    public static String createPdfFile(Context context, String fileName, String folder) {
        try {
            ContentResolver resolver = context.getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + File.separator + "CTW/" + folder);
            final Uri contentUri = MediaStore.Files.getContentUri("external");
            Uri uri = resolver.insert(contentUri, contentValues);
            resolver.openOutputStream(uri);
            return PDFUtil.getPath(context, uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}