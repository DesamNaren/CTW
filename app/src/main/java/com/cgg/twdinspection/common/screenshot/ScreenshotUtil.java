package com.cgg.twdinspection.common.screenshot;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.LruCache;
import android.view.View;
import android.view.View.MeasureSpec;

import androidx.recyclerview.widget.RecyclerView;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
import com.cgg.twdinspection.common.utils.Utils;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ScreenshotUtil {
    private static ScreenshotUtil mInstance;

    private ScreenshotUtil() {
    }

    public static ScreenshotUtil getInstance() {
        if (mInstance == null) {
            synchronized (ScreenshotUtil.class) {
                if (mInstance == null) {
                    mInstance = new ScreenshotUtil();
                }
            }
        }
        return mInstance;
    }

    /**
     * Measures and takes a screenshot of the provided {@link View}.
     *
     * @param view The view of which the screenshot is taken
     * @return A {@link Bitmap} for the taken screenshot.
     */
    public Bitmap takeScreenshotForView(View view) {
        view.measure(MeasureSpec.makeMeasureSpec(view.getWidth(), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(view.getHeight(), MeasureSpec.UNSPECIFIED));
//        view.layout((int) view.getX(), (int) view.getY(), (int) view.getX() + view.getMeasuredWidth(), (int) view.getY() + view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return bitmap;
    }

    public Bitmap takeScreenshotForScreen(Activity activity) {
        return takeScreenshotForView(activity.getWindow().getDecorView().getRootView());
    }

    private static File generatePDF(RecyclerView view, final Activity activity, String fromDate, String toDate) {
        File file = null;
        try {
            RecyclerView.Adapter adapter = view.getAdapter();
            Bitmap bigBitmap = null;
            if (adapter != null) {
                int size = adapter.getItemCount();
                int height = 0, width = 0;
                Paint paint = new Paint();
                int iHeight = 0;
                final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

                // Use 1/8th of the available memory for this memory cache.
                final int cacheSize = maxMemory / 8;

                LruCache<String, Bitmap> bitmaCache = new LruCache<>(cacheSize);
                for (int i = 0; i < size; i++) {
                    RecyclerView.ViewHolder holder = adapter.createViewHolder(view, adapter.getItemViewType(i));
                    adapter.onBindViewHolder(holder, i);
                    holder.itemView.setVisibility(View.VISIBLE);
                    holder.itemView.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.UNSPECIFIED),
                            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                    holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(), holder.itemView.getMeasuredHeight());
                    holder.itemView.setDrawingCacheEnabled(true);
                    holder.itemView.buildDrawingCache();
                    Bitmap drawingCache = holder.itemView.getDrawingCache();
                    if (drawingCache != null) {

                        bitmaCache.put(String.valueOf(i), drawingCache);
                    }

                    height += holder.itemView.getMeasuredHeight();
                    width += holder.itemView.getMeasuredWidth();
                }

                bigBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Canvas bigCanvas = new Canvas(bigBitmap);
                bigCanvas.drawColor(Color.WHITE);

                Document document = new Document(PageSize.A4);
                File pdfFile = new File(Environment.getExternalStorageDirectory() + "/Download/" + "CTW/");
                if (!pdfFile.exists()) {
                    pdfFile.mkdirs();
                }

                file = new File(pdfFile.getPath(), fromDate + "_" + toDate + "_" +
                        Utils.getCurrentDateTime() + ".pdf");
                try {
                    PdfWriter.getInstance(document, new FileOutputStream(file));
                } catch (DocumentException | FileNotFoundException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < size; i++) {

                    try {
                        Bitmap bmp = bitmaCache.get(String.valueOf(i));
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        Image image = Image.getInstance(stream.toByteArray());
                        float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                                - document.rightMargin() - 0) / image.getWidth()) * 100; // 0 means you have no indentation. If you have any, change it.
                        image.scalePercent(scaler);
                        image.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
                        if (!document.isOpen()) {
                            document.open();
                        }
                        document.add(image);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                if (document.isOpen()) {
                    document.close();
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;

    }

    static File pdfFile;

    @SuppressLint("StaticFieldLeak")
    public static class AsyncBTCheckTask extends AsyncTask<Void, Void, File> {
        Activity _activity;
        String fromDate, toDate;
        private final RecyclerView recyclerView;

        AsyncBTCheckTask(RecyclerView recyclerView, Activity _activity, String fromDate, String toDate) {
            this.recyclerView = recyclerView;
            this._activity = _activity;
            this.fromDate = fromDate;
            this.toDate = toDate;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            showProgress();
        }

        protected File doInBackground(Void... x) {
            return generatePDF(recyclerView, _activity, fromDate, toDate);
        }

        @Override
        protected void onPostExecute(File file) {
            super.onPostExecute(file);
//            dismissDialog();
            if (file != null) {
                Utils.customPDFAlert(_activity,
                        _activity.getString(R.string.app_name),
                        "PDF File Generated Successfully. \n File saved at " + pdfFile + "\n Do you want open it?"
                        , file);
            } else {
                Utils.customErrorAlert(_activity,
                        _activity.getString(R.string.app_name),
                        "Failed to generate pdf, Please try again");
            }
        }
    }

    private CustomProgressDialog progressDialog;

    public static void CallPDFGenerator(RecyclerView view, final Activity activity, String fromDate, String toDate) {
//        progressDialog = new CustomProgressDialog(activity);
        new AsyncBTCheckTask(view, activity, fromDate, toDate).execute();
    }

}