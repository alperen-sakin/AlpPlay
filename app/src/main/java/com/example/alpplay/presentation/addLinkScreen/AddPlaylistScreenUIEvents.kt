package com.example.alpplay.presentation.addLinkScreen

class AddPlaylistScreenUIEvents(
    val onPlayListNameChange: (String) -> Unit = {},
    val onM3UUrlChange: (String) -> Unit = {},
    val onCancelClicked: () -> Unit = {},
    val onAddClicked: () -> Unit = {},
)
