#!/bin/bash

# removes all all generate_patch_*, apply_patch_* and revert_patch_* scripts

# shellcheck source=/dev/null
. patches_scripts_generator_constants.sh

function remove_generated_scripts() {
    local unique_scripts=($(echo "${DIRECTORIES[@]}" | tr ' ' '\n' | cut -d '/' -f 1 | sort -u | tr '\n' ' '))
    for value in "${unique_scripts[@]}"; do rm -rf *$value*; done
}

remove_generated_scripts
