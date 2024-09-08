package com.ufam.server.security;

import android.annotation.NonNull;
import android.content.Context;
import android.net.NetworkPolicyManager;
import android.net.INetd;
import android.os.Binder;
import android.os.IBinder;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.util.Slog;
import android.os.RemoteException;
import android.os.ServiceSpecificException;

import androidx.annotation.RequiresApi;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.android.internal.net.IOemNetd;
import com.android.server.SystemService;
import com.ufam.internal.security.IufamSecurityManager;

import static android.os.Build.VERSION_CODES.UPSIDE_DOWN_CAKE;

@RequiresApi(UPSIDE_DOWN_CAKE)
public final class UfamSecurityService extends SystemService {

    private static final String TAG = UfamSecurityService.class.getSimpleName();

    private final Context mContext;
    private NetworkPolicyManager mNetworkPolicyService;

    private INetd mNetd;
    private IOemNetd mOemNetdService;

    public UfamSecurityService(Context context) {
        super(context);
        this.mContext = context;
        mNetd = INetd.Stub.asInterface((IBinder)
                mContext.getSystemService(Context.NETD_SERVICE));
        setNetworkPolicyService();

        try {
            mOemNetdService = IOemNetd.Stub.asInterface(mNetd.getOemNetd());
        } catch (RemoteException | ServiceSpecificException e) {
            Slog.e(TAG, "Failed to getOemNetd ", e);
        }

    }

    @Override
    public void onStart() {
        Slog.d(TAG, "onStart()");
        publishBinderService(Context.UFAM_SECURITY_SERVICE, new Stub());
    }

    @Override
    public void onBootPhase(int phase) {
        if (phase == SystemService.PHASE_BOOT_COMPLETED) {
            final String value = getSettings(Secure.UFAM_SECURITY_DOORS_OPEN);

            if (!value.isEmpty() && isOemNetdServiceAlive()) {
                closeAllButInternal(convertStringToArray(value));
            }
        }
    }

    private boolean isOemNetdServiceAlive() {
        try {
            return mOemNetdService.isAlive();
        } catch(RemoteException | ServiceSpecificException e) {
            Slog.d(TAG, "Could not access oemnetd");
        }

        return false;
    }

    private void setNetworkPolicyService() {
        this.mNetworkPolicyService = mContext.getSystemService(NetworkPolicyManager.class);
    }

    private NetworkPolicyManager getNetworkPolicyService() {
        return this.mNetworkPolicyService;
    }

    private final class Stub extends IufamSecurityManager.Stub {

        @Override
        public void closeAllBut(@NonNull int[] exceptions) {
            Slog.d(TAG, "closeAllBut()");

            if (!validateExceptions(exceptions)) {
                Slog.d(TAG, "Found invalid port number");
                return;
            }

            final long token = Binder.clearCallingIdentity();
            try {
                closeAllButInternal(exceptions);
            } finally {
                Binder.restoreCallingIdentity(token);
            }
        }

        @Override
        public int[] getAllOpenDoors() {
            final long token = Binder.clearCallingIdentity();
            try {
                final String value = getSettings(Secure.UFAM_SECURITY_DOORS_OPEN);
                return value.isEmpty() ? null : convertStringToArray(value);
            } finally {
                Binder.restoreCallingIdentity(token);
            }
        }
    }

    private void closeAllButInternal(@NonNull int[] exceptions) {
        getNetworkPolicyService().closeAllBut(exceptions);
        saveSettings(Secure.UFAM_SECURITY_DOORS_OPEN,
                convertArrayToString(exceptions));
    }

    private boolean validateExceptions(int[] exceptions) {
        return Arrays.stream(exceptions)
                    .allMatch(number -> number >= 1024 && number <= 65535);
    }

    private String convertArrayToString(int[] array) {
        return Arrays.stream(array)
                    .mapToObj(String::valueOf)
                    .collect(Collectors.joining(","));
    }

    private int[] convertStringToArray(String str) {
        return Arrays.stream(str.split(","))
                    .filter(s -> !s.isEmpty())
                    .map(s -> s.trim())
                    .mapToInt(Integer::parseInt)
                    .toArray();
    }

    private void saveSettings(String key, String value) {
        Settings.Secure.putString(mContext.getContentResolver(),
                key, value);
    }

    private String getSettings(String key) {
        final String value = Settings.Secure.getString(mContext.getContentResolver(), key);
        return value != null ? value : "";
    }

}
