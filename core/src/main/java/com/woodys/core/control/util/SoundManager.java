package com.woodys.core.control.util;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;

import java.util.HashMap;

/**
 * 声音帮助类
 * 
 * @author 扑倒末末 TODO 更改为Medelpool播放音乐
 */
public class SoundManager {
	private static SoundManager instance = null;
	private final HashMap<SoundStatus, Integer> soundMap;

	private MediaPlayer mediaPlayer;

	private SoundManager() {
		soundMap = new HashMap<SoundStatus, Integer>();
	}

	public static SoundManager instance(Context context) {
		if (null == instance) {
			synchronized (SoundManager.class) {
				if (null == instance) {
					instance = new SoundManager();
				}
			}
		}
		return instance;
	}

	public void addSoundEvent(SoundStatus status, int resId) {
		soundMap.put(status, resId);
	}

	public void clearSounds() {
		soundMap.clear();
	}

	public MediaPlayer getCurrentMediaPlayer() {
		return mediaPlayer;
	}

	public void playSound(Context context, SoundStatus status) {
		if (null != mediaPlayer) {
			mediaPlayer.stop();
			mediaPlayer.release();
		}
		if (null != soundMap.get(status)) {
			mediaPlayer = MediaPlayer.create(context, soundMap.get(status));
			if (null != mediaPlayer) {
				mediaPlayer.start();
			}
		}
	}

	/**
	 * 播放系统提示音------- RingtoneManager.TYPE_ALARM:系统警告
	 * RingtoneManager.TYPE_NOTIFICATION 系统提示 RingtoneManager.TYPE_RINGTONE 手机响铃
	 */
	public void playSystemNotifySound(Context context, int ringtone) {
		if (null != mediaPlayer) {
			mediaPlayer.stop();
			mediaPlayer.release();
		}
		Uri uri = RingtoneManager.getDefaultUri(ringtone);
		mediaPlayer = MediaPlayer.create(context, uri);
		if (null != mediaPlayer) {
			mediaPlayer.start();
		}
	}

	public static enum SoundStatus {
		SYSTEM, INVITE, TALK;
	}
}
