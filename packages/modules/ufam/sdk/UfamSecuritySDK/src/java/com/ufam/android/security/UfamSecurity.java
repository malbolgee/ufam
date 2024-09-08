package com.ufam.android.security;

import android.content.Context;
import android.os.Build;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceManager.ServiceNotFoundException;
import android.util.Log;

import com.ufam.android.security.exceptions.ApiUnavailableException;
import com.ufam.internal.security.IufamSecurityManager;

public final class UfamSecurity {

    private static final String TAG = UfamSecurity.class.getSimpleName();
    private static final boolean DBG = Build.IS_DEBUGGABLE;

    private final Context mContext;
    private IufamSecurityManager mService = null;

    public UfamSecurity(Context context) {
        if (DBG) {
            Log.d(TAG, "UfamSecurity()");
        }

        this.mContext = context;
        try {
            this.mService = IufamSecurityManager.Stub.asInterface(
                ServiceManager.getServiceOrThrow(Context.UFAM_SECURITY_SERVICE));
        } catch (ServiceNotFoundException e) {
            Log.e(TAG, "could not find service", e);
        }

    }

    /**
     * Closes all doors except for those in the exceptions array.
     *
     * @param exceptions Is an int array where each element represnets a port
     *                   to be left open.
     */
    public void closeAllBut(int[] exceptions) throws ApiUnavailableException {
        try {
            this.mService.closeAllBut(exceptions);
        } catch (RemoteException e) {
            Log.e(TAG, "could not call closeAllBut", e);
        }
    }

    /**
     * Get all dors left open by the policy.
     *
     * @return a {@code int[]} where each element is a door that is open.
     */
    public int[] getAllOpenDoors() throws ApiUnavailableException {
        try {
            return this.mService.getAllOpenDoors();
        } catch (RemoteException e) {
            Log.d(TAG, "could not call getAllOpenDoors", e);
        }
        return null;
    }

}
