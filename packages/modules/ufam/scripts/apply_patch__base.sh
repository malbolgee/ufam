#!/bin/bash

if ! [ -d "${_PATCH_DIRECTORY}" ]; then
  echo -e "\e[31mNone patches were applied because the path is not valid.\e[0m"
elif ! [ -d "${_DIRECTORY_TO_BE_PATCHED}" ]; then
  echo -e "\e[33mNone patches were applied at ${_DIRECTORY_TO_BE_PATCHED}, directory not present.\e[0m"
else
  git -C "${_DIRECTORY_TO_BE_PATCHED}" apply "${_PATCH_DIRECTORY}/${_PATCH_FILE}"
  ret=$?
  if [ "$ret" -eq 0 ]; then
    echo -e "\e[32m${_PATCH_FILE} file was applied into ${_DIRECTORY_TO_BE_PATCHED}.\e[0m"
  fi
fi
