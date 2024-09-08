#!/bin/bash

# applies all existing patches

if [[ ! ${_TOP_DIR} ]]; then
    echo "Can not locate root of source tree. $(basename $0) must be run from within the Android source tree." >&2
    exit
fi
if ! [ -d "${_TOP_DIR}/${_DIRECTORY_TO_BE_PATCHED}" ]; then
    echo -e "\e[33mNone patches were generated because ${_TOP_DIR}/${_DIRECTORY_TO_BE_PATCHED} not present.\e[0m"
else

    echo "git -C ${_TOP_DIR}/${_DIRECTORY_TO_BE_PATCHED} diff --cached --full-index > ../${_PATCH_DIRECTORY}/${_PATCH_FILE}";
    git -C "${_TOP_DIR}/${_DIRECTORY_TO_BE_PATCHED}" add .
    git -C "${_TOP_DIR}/${_DIRECTORY_TO_BE_PATCHED}" diff --cached --full-index > ../"${_PATCH_DIRECTORY}/${_PATCH_FILE}";
    git -C "${_TOP_DIR}/${_DIRECTORY_TO_BE_PATCHED}" restore --staged .

    echo "Generated ${_PATCH_FILE} from ${_DIRECTORY_TO_BE_PATCHED}."
fi
