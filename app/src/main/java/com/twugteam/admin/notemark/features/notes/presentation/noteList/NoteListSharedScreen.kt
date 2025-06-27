package com.twugteam.admin.notemark.features.notes.presentation.noteList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.core.presentation.designsystem.NoteMarkIcons
import com.twugteam.admin.notemark.core.presentation.designsystem.NoteMarkTheme
import com.twugteam.admin.notemark.core.presentation.designsystem.OnSurfaceVar
import com.twugteam.admin.notemark.core.presentation.designsystem.Surface
import com.twugteam.admin.notemark.core.presentation.designsystem.SurfaceLowest
import com.twugteam.admin.notemark.core.presentation.designsystem.components.NoteMarkDialog
import com.twugteam.admin.notemark.core.presentation.ui.formatAsNoteDate
import com.twugteam.admin.notemark.core.presentation.ui.getInitial
import com.twugteam.admin.notemark.features.notes.presentation.noteList.model.NoteUi
import com.twugteam.admin.notemark.features.notes.presentation.noteList.state.NoteListState
import com.twugteam.admin.notemark.features.notes.presentation.noteList.ui.Util
import com.twugteam.admin.notemark.features.notes.presentation.noteList.viewmodel.NoteListAction


//TODO: It's bad way for trying to control child compose by passing child modifier rather it should have own Default Modifier
@Composable
fun NoteGraphSharedScreen(
    modifier: Modifier = Modifier,
    topBarModifier: Modifier = Modifier,
    noteMarkListPaddingValues: PaddingValues,
    verticalSpace: Dp,
    horizontalSpace: Dp,
    staggeredGridCells: StaggeredGridCells,
    state: NoteListState,
    onActions: (NoteListAction) -> Unit,
    windowSize: WindowWidthSizeClass
) {
    Scaffold(
        modifier = modifier,
        containerColor = Surface,
        topBar = {
            NoteListTopBar(
                modifier = topBarModifier,
                username = state.username
            )
        },
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .padding()
                    .size(64.dp)
                    .clip(
                        RoundedCornerShape(20.dp)
                    )
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF58A1F8),
                                Color(0xFF5A4CF7),
                            )
                        )
                    )
                    .clickable {
                        onActions(NoteListAction.NavigateToUpsertNote(noteId = null))
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = NoteMarkIcons.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { innerPadding ->
        if (!state.notes.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
            ){
                NoteMarkList(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(noteMarkListPaddingValues)
                        .padding(innerPadding),
                    verticalSpace = verticalSpace,
                    horizontalSpace = horizontalSpace,
                    noteMarkList = state.notes,
                    staggeredGridCells = staggeredGridCells,
                    onNoteClick = { noteUi ->
                        onActions(NoteListAction.NavigateToUpsertNote(noteId = noteUi.id))
                    },
                    onNoteDelete = { noteUi->
                        onActions(NoteListAction.OnNoteDelete(noteId = noteUi.id!!))
                    },
                    windowSize = windowSize
                )
                NoteMarkDialog(
                    modifier = Modifier.fillMaxWidth()
                        .align(Alignment.Center),
                    showDialog = state.showDialog,
                    titleResId = R.string.dialog_delete_title,
                    isLoading = state.isLoading,
                    bodyResId = R.string.dialog_delete_body_text,
                    confirmButtonId = R.string.delete,
                    dismissButtonId = R.string.cancel,
                    onConfirmClick = {
                        onActions(NoteListAction.OnDialogConfirm)
                    },
                    onDismissClick = {
                        onActions(NoteListAction.OnDialogDismiss)
                    },
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 80.dp)
                        .align(Alignment.TopCenter),
                    text = stringResource(R.string.empty_notes),
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = OnSurfaceVar,
                        textAlign = TextAlign.Center
                    ),
                )
            }
        }
    }
}

@Composable
fun NoteListTopBar(
    modifier: Modifier = Modifier,
    username: String,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(R.string.noteMark),
            style = MaterialTheme.typography.titleSmall.copy(
                fontSize = 20.sp, lineHeight = 24.sp, letterSpacing = 0.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(shape = MaterialTheme.shapes.small)
                .background(color = MaterialTheme.colorScheme.primary),
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = username.getInitial(),
                style = MaterialTheme.typography.titleSmall.copy(
                    color = MaterialTheme.colorScheme.onPrimary
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun NoteMarkList(
    modifier: Modifier = Modifier,
    verticalSpace: Dp,
    horizontalSpace: Dp,
    noteMarkList: List<NoteUi>,
    staggeredGridCells: StaggeredGridCells,
    onNoteClick: (NoteUi) -> Unit,
    onNoteDelete: (NoteUi) -> Unit,
    windowSize: WindowWidthSizeClass
    ) {
    val state = rememberLazyStaggeredGridState()
    LazyVerticalStaggeredGrid(
        modifier = modifier,
        columns = staggeredGridCells,
        verticalItemSpacing = verticalSpace,
        horizontalArrangement = Arrangement.spacedBy(horizontalSpace),
        state = state
    ) {
        items(items = noteMarkList, key = {
            it.id!!
        }) { noteMark ->
            NoteListItem(
                modifier = Modifier.fillMaxWidth(),
                noteUi = noteMark,
                onNoteClick = onNoteClick,
                onNoteDelete = onNoteDelete,
                windowSize = windowSize
            )
        }
    }
}

@Composable
fun NoteListItem(
    modifier: Modifier = Modifier,
    noteUi: NoteUi,
    onNoteClick: (NoteUi) -> Unit,
    onNoteDelete: (NoteUi) -> Unit,
    windowSize: WindowWidthSizeClass
) {
    NoteMarkTheme {
        Surface(
            modifier = modifier.combinedClickable(
                onClick = {
                    onNoteClick(noteUi)
                },
                onLongClick = {
                    onNoteDelete(noteUi)
                }
            ),
            color = SurfaceLowest,
            shape = MaterialTheme.shapes.small
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = noteUi.createdAt.formatAsNoteDate(),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = noteUi.title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 20.sp,
                        lineHeight = 24.sp,
                    )
                )
                Text(
                    text = Util.textLimit(
                        text = noteUi.content,
                        windowSize = windowSize
                    ),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = OnSurfaceVar
                    )
                )
            }
        }

    }
}
