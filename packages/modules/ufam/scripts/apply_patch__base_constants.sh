#!/bin/bash

# shellcheck source=/dev/null
. shell_utils.sh

_AOSP_OUTPUT_DIRECTORY=$(gettop)/
_UFAM_SECURITY_DIRECTORY=$(getUfamSecurityPath)

_UFAM_SECURITY_PATCH_DIRECTORY=$_UFAM_SECURITY_DIRECTORY"packages/modules/ufam/patches"
