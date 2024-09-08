package com.ufam.internal.security;

@PermissionManuallyEnforced
interface IufamSecurityManager {
    void closeAllBut(in int[] exceptions);
    int[] getAllOpenDoors();
}
