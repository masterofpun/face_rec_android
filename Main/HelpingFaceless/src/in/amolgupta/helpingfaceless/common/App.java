package in.amolgupta.helpingfaceless.common;

import in.amolgupta.helpingfaceless.utils.ET;

import java.io.File;

import android.app.Application;
import android.graphics.Bitmap.CompressFormat;

import com.helpshift.Helpshift;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

/**
 * @author amol
 * 
 */
public class App extends Application {
	private DisplayImageOptions options;
	private ImageLoaderConfiguration config;

	@Override
	public void onCreate() {
		super.onCreate();
		File cacheDir = StorageUtils.getCacheDirectory(this);
		config = new ImageLoaderConfiguration.Builder(this)
				.memoryCacheExtraOptions(480, 800)
				// default = device screen dimensions
				.discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75, null)
				.threadPoolSize(3)
				// default
				.threadPriority(Thread.NORM_PRIORITY - 1)
				// default
				.tasksProcessingOrder(QueueProcessingType.FIFO)
				// default
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				.memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13)
				// default
				.discCache(new UnlimitedDiscCache(cacheDir))
				// default
				.discCacheSize(50 * 1024 * 1024).discCacheFileCount(100)
				.discCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
				.writeDebugLogs().build();
		ImageLoader.getInstance().init(config);
//		Pushbots.init(this, getResources().getString(R.string.push_bots_key),
//				getResources().getString(R.string.push_bots_key));
		
		Helpshift.install(this,
                "de66bee61e1b472923ff949cb964ec9d", // API Key
                "helpingfaceless.helpshift.com", // Domain Name
                "helpingfaceless_platform_20140518124454617-20930bb94600808");
		
		
		ET.trackAppopened();
		

	}

}
