#!/bin/bash

if [ "$1" != "confirm" ]; then
    MESSAGE="
        Please check AOSP repos and confirm the destructive operation
        by passing 'confirm' at the end of the command.
    "

    echo "$MESSAGE"
else
    . revert_patch_build_soong.sh confirm
    . revert_patch_frameworks_base.sh confirm
    . revert_patch_system_sepolicy.sh confirm
    . revert_patch_system_netd.sh confirm
    . revert_patch_build_target.sh confirm
fi
