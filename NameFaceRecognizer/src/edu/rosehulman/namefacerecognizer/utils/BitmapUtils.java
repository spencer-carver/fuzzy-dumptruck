package edu.rosehulman.namefacerecognizer.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapUtils {

	private final static BitmapFactory.Options bitmapFactoryOptions = initializeBitmapFactoryOptions();
	
	private static BitmapFactory.Options initializeBitmapFactoryOptions() {
		BitmapFactory.Options factoryOptions = new BitmapFactory.Options();
		factoryOptions.inPurgeable = true;
		factoryOptions.inInputShareable = true;
		return factoryOptions;
	}
	
	public static Bitmap loadBitmapFromFile(String filePath) {
		Bitmap bitmap = BitmapFactory.decodeFile(filePath, bitmapFactoryOptions);
		return bitmap;
	}
	
	public static Bitmap loadBitmap(byte[] bitmapData) {
		return BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.length, bitmapFactoryOptions);
	}

}
