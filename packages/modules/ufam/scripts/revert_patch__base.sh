#!/bin/bash

# base script for revert_patch_* scripts

if [ "$1" != "confirm" ]; then
    MESSAGE="
        Please check AOSP repos and confirm the destructive operation
        by passing 'confirm' at the end of the command.
    "

    echo "$MESSAGE"
else

    if [[ ! ${_TOP_DIR} ]]; then
        echo "Can not locate root of source tree. $(basename $0) must be run from within the Android source tree." >&2
        exit
    fi
    if ! [ -d ${_TOP_DIR}/${_DIRECTORY_TO_BE_PATCHED} ]; then
        echo -e "\e[33mNo patches were reverted at ${_TOP_DIR}/${_DIRECTORY_TO_BE_PATCHED}, directory not present.\e[0m"
    else
        echo "Reverting patch from ${_TOP_DIR}/${_DIRECTORY_TO_BE_PATCHED}"
        git -C ${_TOP_DIR}/${_DIRECTORY_TO_BE_PATCHED} restore .
        git -C ${_TOP_DIR}/${_DIRECTORY_TO_BE_PATCHED} clean -fd
    fi
fi
