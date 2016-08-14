package com.woodys.core.control.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.telephony.TelephonyManager;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.UUID;


/**
 * Created by ldfs on 15/7/27.
 */
public class PubKeySignature {
    private static final int KEY_SIZE = 36;
    private static final int FOOT_SUB_KEY_SIZE = 5;
    private static final int HEAD_SUB_KEY_SIZE = 9;

    /**
     * 获取证书签名的部分信息作为key
     *
     * @param context
     * @return
     */
    public static String getSingInfo(Context context, char random) {
        String public_key = "";
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
            public_key = parseSignature(sign.toByteArray(), random);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return public_key;
    }

    /**
     * 获取签名key
     *
     * @param signature
     * @param random
     * @return
     */
    public static String parseSignature(byte[] signature, char random) {
        String pubKey = "";
        try {
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) certFactory
                    .generateCertificate(new ByteArrayInputStream(signature));
            String keyEncoded = new String(Base64.encode(cert.getPublicKey().getEncoded(), Base64.URL_SAFE | Base64.NO_WRAP));
            pubKey=keyEncoded.substring(HEAD_SUB_KEY_SIZE,keyEncoded.length());
            pubKey=pubKey.substring(0,pubKey.length()-FOOT_SUB_KEY_SIZE);
            pubKey = pubKey.substring(pubKey.length() - KEY_SIZE);
            pubKey = pubKey.substring(0, pubKey.length() - (((int) random) % 10));
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return pubKey;
    }


    public static String getUUID(Context context) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId;
    }
}
