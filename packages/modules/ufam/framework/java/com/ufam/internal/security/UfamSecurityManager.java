package com.ufam.internal.security;

import android.annotation.NonNull;
import android.annotation.SystemService;
import android.content.Context;
import android.os.Binder;
import android.os.RemoteException;
import androidx.annotation.RequiresApi;

import static android.os.Build.VERSION_CODES.UPSIDE_DOWN_CAKE;

@RequiresApi(UPSIDE_DOWN_CAKE)
@SystemService(Context.UFAM_SECURITY_SERVICE)
public final class UfamSecurityManager {

    private static final String TAG = UfamSecurityManager.class.getSimpleName();

    @NonNull private final Context mContext;
    @NonNull private final IufamSecurityManager mService;

    public UfamSecurityManager(@NonNull Context context, @NonNull IufamSecurityManager service) {
        this.mContext = context;
        this.mService = service;
    }

    public void closeAllBut(@NonNull int[] exceptions) {
        try {
            mService.closeAllBut(exceptions);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int[] getAllOpenDoors() {
        try {
            return mService.getAllOpenDoors();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
