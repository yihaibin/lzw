package com.lzw.zmm.zxing.activity;

import java.io.IOException;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidex.activity.ExActivity;
import com.androidex.util.DensityUtil;
import com.androidex.util.TextUtil;
import com.androidex.util.ToastUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.lzw.zmm.R;
import com.lzw.zmm.zxing.camera.CameraManager;
import com.lzw.zmm.zxing.camera.FlashlightManager;
import com.lzw.zmm.zxing.decoding.CaptureActivityHandler;
import com.lzw.zmm.zxing.decoding.InactivityTimer;
import com.lzw.zmm.zxing.view.ViewfinderView;
/**
 * Initial the camera
 * @author Ryan.Tang
 */
public class MipcaActivityCapture extends ExActivity implements Callback {

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	private EditText mEtBarcode;
	private ImageView mIvFlashlight;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_capture);
	}
	
	@Override
	protected void onStop() {
		
		super.onStop();
		mIvFlashlight.setSelected(false);
	}
	
	@Override
	protected void initData() {
		
		CameraManager.init(getApplication());
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
	}
	
	@Override
	protected void initTitleView() {
		
		addTitleLeftBackView();
		addTitleMiddleTextView(R.string.barcode_scanner);
		mIvFlashlight = addTitleRightImageViewHoriWrap(R.drawable.selector_flashlight, new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(v.isSelected()){
					
					v.setSelected(false);
					CameraManager.get().closeFlashlight();
				}else{
					
					//open
					CameraManager.get().openFlashlight();
					v.setSelected(true);
				}
			}
		});
		
		mIvFlashlight.setBackgroundDrawable(null);
		mIvFlashlight.setPadding(0, 0, DensityUtil.dip2px(14), 0);
	}
	
	@Override
	protected void initContentView() {
		
		//ViewUtil.addTopView(getApplicationContext(), this, R.string.scan_card);
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		TextView tvSearch = (TextView) findViewById(R.id.tvSearch);
		tvSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			
				//TODO 
				ToastUtil.showToast("搜索按钮点击");
			}
		});
		
		mEtBarcode = (EditText) findViewById(R.id.etBarcode);
		mEtBarcode.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
				if(isFinishing())
					return;
				
				if(s.length() == 0){
					
					handler.restartPreviewAndDecode();
				}
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
		
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		FlashlightManager.setFlashlight(false);
		super.onDestroy();
	}
	
	/**
	 * 处理扫描结果
	 * @param result
	 * @param barcode
	 */
	public boolean handleDecode(Result result, Bitmap barcode) {
		
		if(isFinishing())
			return false;
		
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		String resultString = result.getText();
		if (TextUtil.isEmpty(resultString)) {
			
			mEtBarcode.setText("");
			Toast.makeText(MipcaActivityCapture.this, "扫描失败，请重试", Toast.LENGTH_SHORT).show();
			return false;
		}else {
			
			mEtBarcode.setText(resultString);
			return true;
		}
	}
	
	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};
	
	public static void startActivity(Activity activity){
		
		Intent intent = new Intent();
		intent.setClass(activity, MipcaActivityCapture.class);
		activity.startActivity(intent);
	}
}