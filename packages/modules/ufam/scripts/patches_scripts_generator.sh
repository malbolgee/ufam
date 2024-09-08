#!/bin/bash

# shellcheck source=/dev/null
. patches_scripts_generator_constants.sh

function reset_all_patches_scripts() {
    awk '!/generate_patch/' generate_all_patches.sh >temp && cat temp >generate_all_patches.sh
    awk '!/apply_patch/' apply_all_patches.sh >temp && cat temp >apply_all_patches.sh
    awk '!/revert_patch/' revert_all_patches.sh >temp && cat temp >revert_all_patches.sh
    rm -f temp
}

function create_generate_script() {

    local script_name=generate_$1
    local directory_to_patch=$2
    local patch_file_name=$3

    if [[ ! -f "./$script_name" ]]; then
        echo "Creating $script_name"

        touch "$script_name"
        add_header "$script_name" "generates a specific patch from a specific repo"
        {
            printf ". generate_patch__base_constants.sh\n\n"
            printf "_PATCH_DIRECTORY=\${_UFAM_SECURITY_PATCH_DIRECTORY};\n"
            printf "_DIRECTORY_TO_BE_PATCHED=\"%s\";\n" "$directory_to_patch"
            printf "_PATCH_FILE=\'%s\';\n\n" "$patch_file_name"
            printf ". generate_patch__base.sh\n"
        } >>"$script_name"
    fi
    add_line_to_file_script "$script_name" ./generate_all_patches.sh
}

function create_apply_script() {

    local script_name=apply_$1
    local directory_to_patch=$2
    local patch_file_name=$3

    if [[ ! -f "./$script_name" ]]; then

        echo "Creating $script_name"
        touch "$script_name"
        add_header "$script_name" "applies a specific patch into a specific repo"
        {
            printf ". apply_patch__base_constants.sh\n\n"
            printf "_PATCH_DIRECTORY=\${_UFAM_SECURITY_PATCH_DIRECTORY};\n"
            printf "_DIRECTORY_TO_BE_PATCHED=\${_AOSP_OUTPUT_DIRECTORY}\"%s\"\n" "$directory_to_patch"
            printf "_PATCH_FILE=\"%s\";\n\n" "$patch_file_name"
            printf ". apply_patch__base.sh\n"
        } >>"$script_name"
    fi
    add_line_to_file_script "$script_name" ./apply_all_patches.sh
}

function create_revert_script() {

    local script_name=revert_$1
    local directory_to_patch=$2
    local patch_file_name=$3

    if [[ ! -f "./$script_name" ]]; then

        echo "Creating $script_name"
        touch "$script_name"
        add_header "$script_name" "revert a specific patch on a specific repo"
        {
            printf ". revert_patch__base_constants.sh\n\n"
            printf "_DIRECTORY_TO_BE_PATCHED=\"%s\";\n\n" "$directory_to_patch"
            printf ". revert_patch__base.sh \"\$1\"\n"
        } >>"$script_name"
    fi
    add_line_to_file_script_special "$script_name" ./revert_all_patches.sh
}

function add_shebang() {
    printf "#!/bin/bash\n\n" >"$1"
}

function add_doc() {
    printf "# %s\n\n" "$2" >>"$1"
}

function add_header() {
    # Add double quotes as some of the arguments have spaces
    add_shebang "$1"
    add_doc "$1" "$2"
}

function create_script() {
    local directory=$1

    local file_name
    local patch_file

    file_name=$(echo "$directory" | tr '/' '_' | awk '{print tolower($0)}')
    patch_file=$(echo "$directory" | tr '/' '.' | awk '{print tolower($0)}').patch

    local script_name=patch_$file_name.sh

    create_generate_script "$script_name" "$directory" "$patch_file"
    create_apply_script "$script_name" "$directory" "$patch_file"
    create_revert_script "$script_name" "$directory" "$patch_file"
}

function add_line_to_file_script_special() {

    local script_file_name=$1
    local file_to_add_line=$2

    # This is a more complex situation compared to the one in
    # the add_line_to_file_script function.
    # We want to add a line among other lines. First we check if
    # the line we want to add exists in the
    # file and if not, we look of a certain reference,
    # in this case the 'fi' keyword, and insert the new
    # line on top of it.
    if ! grep -Fxq "    . $script_file_name confirm" "$file_to_add_line"; then
        sed -i "/^fi/i  \ \ \ \ . $script_file_name confirm" "$file_to_add_line"
    fi
}

function add_line_to_file_script() {

    local script_file_name=$1
    local file_to_add_line=$2

    # Look if the line we want to add exists in the file,
    # and if it does not, add it.
    # As this is a simple sequence of lines, we can simply
    # add the new line at the end of the file.
    if ! grep -Fxq ". $script_file_name" "$file_to_add_line"; then
        printf ". %s\n" "$script_file_name" >>"$file_to_add_line"
    fi
}

function call_patch_script() {
    for value in "${DIRECTORIES[@]}"; do create_script "$value"; done
}

reset_all_patches_scripts
call_patch_script
