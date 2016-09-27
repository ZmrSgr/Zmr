package cn.sgr.zmr.com.sgr.Utils.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtil {

	public static File operaFileData( byte[] by)
	{
	{
		
		File mFileZoomDir = null;
		if (mFileZoomDir == null) {
			mFileZoomDir = new File(MyConfig.FILEPATH + "img/");
			if (!mFileZoomDir.exists()) {
				mFileZoomDir.mkdirs();
			}
		}

//		String fileName = ;

		String filePath = mFileZoomDir+ "/temp.jpg" ;
		
	FileOutputStream fileout = null;
	String fileName = filePath ;
	File file = new File(fileName);
	if (file.exists())
	{
	file.delete();
	}
	try
	{
	fileout = new FileOutputStream(file);
	fileout.write(by, 0, by.length);

	} catch (FileNotFoundException e)
	{
	// TODO Auto-generated catch block
	e.printStackTrace();
	} catch (IOException e)
	{
	// TODO Auto-generated catch block
	e.printStackTrace();
	} finally
	{
	try
	{
	fileout.close();
	} catch (IOException e)
	{
	// TODO Auto-generated catch block
	e.printStackTrace();
	}
	}

	return file;
	}
	}

	/**
	 * 根据byte数组，生成文件
	 */
	public static void getFile(byte[] bfile) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		// private File mFileZoomDir;
		File mFileZoomDir = null;
		if (mFileZoomDir == null) {
			mFileZoomDir = new File(MyConfig.FILEPATH1 + "img/");
			if (!mFileZoomDir.exists()) {
				mFileZoomDir.mkdirs();
			}
		}

		String fileName = "/temp.jpg";

		String filePath = mFileZoomDir + fileName;

		try {
			File dir = new File(filePath);
			if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
				dir.mkdirs();
			}
			file = new File(filePath + "\\" + fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(bfile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public static String zoomImage(String path, int maxSize) {
		try {
			Bitmap bitmap;
			Options options = new Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(path, options);
			int max = Math.max(options.outWidth, options.outHeight);
			if (max > maxSize) {
				int num = max / maxSize;
				options.inJustDecodeBounds = false;
				options.inSampleSize = num;

				int degree = ImageUtil.readPictureDegree(path);
				bitmap = BitmapFactory.decodeFile(path, options);
				bitmap = ImageUtil.rotaingImageView(degree, bitmap);

				if (bitmap.getWidth() > maxSize || bitmap.getHeight() > maxSize) {
					bitmap = scaleBitmap(bitmap, maxSize);
				}
				FileOutputStream b = null;
				File file = new File(MyConfig.FILEPATH + "img/");
				if (!file.exists()) {
					file.mkdirs();
				}

				String fileName;
				if (path.startsWith(MyConfig.FILEPATH)) {
					fileName = path;
				} else {
					fileName = MyConfig.FILEPATH + "img/"
							+ MyConfig.imageName();
				}

				try {
					b = new FileOutputStream(fileName);
					bitmap.compress(Bitmap.CompressFormat.JPEG, 60, b);// 把数据写入文件
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						b.flush();
						b.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return fileName;
			} else {
				return path;
			}

		} catch (OutOfMemoryError err) {
			err.printStackTrace();
		}

		return path;
	}

	public static Bitmap scaleBitmap(Bitmap bm,int pixel){
		int srcHeight = bm.getHeight();
		int srcWidth = bm.getWidth();


		if(srcHeight>pixel || srcWidth>pixel)
		{
			float scale_y = 0;
			float scale_x = 0;
			if (srcHeight > srcWidth)
			{
				scale_y = ((float)pixel)/srcHeight;
				scale_x = scale_y;
			}
			else
			{
				scale_x = ((float)pixel)/srcWidth;
				scale_y = scale_x;
			}

			Matrix  matrix = new Matrix();
			matrix.postScale(scale_x, scale_y);
			Bitmap dstbmp = Bitmap.createBitmap(bm, 0, 0, srcWidth, srcHeight, matrix, true);
			return dstbmp;
		}
		else{
			return Bitmap.createBitmap(bm);
		}
	}

	public static String avatarImage(String path, int maxSize) {
		try {
			Bitmap bitmap;
			Options options = new Options();
			options.inJustDecodeBounds = true;
			bitmap = BitmapFactory.decodeFile(path, options);
			int max = Math.max(options.outWidth, options.outHeight);
			if (max > maxSize) {
				int num = max / maxSize;
				options.inJustDecodeBounds = false;
				options.inSampleSize = num;

				int degree = ImageUtil.readPictureDegree(path);
				bitmap = BitmapFactory.decodeFile(path, options);
				bitmap = ImageUtil.rotaingImageView(degree, bitmap);
				bitmap = startToHeaderBitmap(bitmap);
				FileOutputStream b = null;
				File file = new File(MyConfig.FILEPATH + "img/");
				if (!file.exists()) {
					file.mkdirs();
				}

				String fileName;
				if (path.startsWith(MyConfig.FILEPATH)) {
					fileName = path;
				} else {
					fileName = MyConfig.FILEPATH + "img/"
							+ MyConfig.imageName();
				}

				try {

					b = new FileOutputStream(fileName);
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						b.flush();
						b.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return fileName;
			} else {
				return path;
			}

		} catch (OutOfMemoryError err) {
			err.printStackTrace();
		}

		return path;
	}

	public static Bitmap startToHeaderBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			top = 0;
			bottom = width;
			left = 0;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right,
				(int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top,
				(int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);

		paint.setAntiAlias(true);

		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, 0, 0, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);
		return output;
	}

	// 获取视频帧图像
	@SuppressLint("NewApi")
	public static Bitmap createVideoThumbnail(String filePath) {
		File videoImageFile;
		File fileDir;

		Bitmap bitmap = null;

		MediaMetadataRetriever retriever = new MediaMetadataRetriever();
		try {
			retriever.setDataSource(filePath);
			bitmap = retriever.getFrameAtTime();
		} catch (IllegalArgumentException ex) {
			// Assume this is a corrupt video file
		} catch (RuntimeException ex) {
			// Assume this is a corrupt video file.
		} finally {
			try {
				retriever.release();
			} catch (RuntimeException ex) {
				// Ignore failures while cleaning up.
			}
		}
		FileOutputStream b = null;

		fileDir = new File(MyConfig.FILEPATH + "img/");
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}

		String fileName = MyConfig.FILEPATH + "img/"
				+ MyConfig.imageName();
		try {

			b = new FileOutputStream(fileName);
			if (null != bitmap) {
				bitmap.compress(Bitmap.CompressFormat.JPEG, 60, b);// 把数据写入文件
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				b.flush();
				b.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bitmap;
	}

	/**
	 * 读取图片属性：旋转的角度
	 * 
	 * @param path
	 *            图片绝对路径
	 * @return degree旋转的角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * 旋转图片
	 * 
	 * @param angle
	 * @param bitmap
	 * @return Bitmap
	 */
	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		;
		matrix.postRotate(angle);
		System.out.println("angle2=" + angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}

	public static int computeSampleSize(Options options, int minSideLength,
			int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	public static void saveMyBitmap(String bitName, Bitmap mBitmap) {
		File f = new File(bitName);
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Bitmap getDiskBitmap(String pathString) {
		Bitmap bitmap = null;
		try {
			File file = new File(pathString);
			if (file.exists()) {
				bitmap = BitmapFactory.decodeFile(pathString);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return bitmap;
	}

	public static int Dp2Px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	public int Px2Dp(Context context, float px) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}

}
