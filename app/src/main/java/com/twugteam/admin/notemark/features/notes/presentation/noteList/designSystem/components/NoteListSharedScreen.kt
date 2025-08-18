package com.twugteam.admin.notemark.features.notes.presentation.noteList.designSystem.components

import androidx.annotation.StringRes
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
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
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.core.presentation.designsystem.NoteMarkIcons
import com.twugteam.admin.notemark.core.presentation.designsystem.NoteMarkIcons.CloudOff
import com.twugteam.admin.notemark.core.presentation.designsystem.NoteMarkTheme
import com.twugteam.admin.notemark.core.presentation.designsystem.SurfaceLowest
import com.twugteam.admin.notemark.core.presentation.designsystem.components.NoteMarkDialog
import com.twugteam.admin.notemark.core.presentation.ui.formatAsNoteDate
import com.twugteam.admin.notemark.features.notes.presentation.noteList.NoteListActions
import com.twugteam.admin.notemark.features.notes.presentation.noteList.NoteListUiState
import com.twugteam.admin.notemark.features.notes.presentation.noteList.model.NoteUi
import com.twugteam.admin.notemark.features.notes.presentation.noteList.util.TextUtil
import com.twugteam.admin.notemark.features.notes.presentation.noteList.util.getInitial
import timber.log.Timber

@Composable
fun NoteListSharedScreen(
    modifier: Modifier = Modifier,
    topBarPaddingValues: PaddingValues,
    noteMarkListPaddingValues: PaddingValues,
    verticalSpace: Dp,
    horizontalSpace: Dp,
    staggeredGridCells: StaggeredGridCells,
    state: NoteListUiState,
    onActions: (NoteListActions) -> Unit,
    windowSizeClass: WindowSizeClass
) {
    val notePagingFlow = state.notesPagingFlow.collectAsLazyPagingItems()

    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            NoteListTopBar(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.onPrimary)
                    .padding(topBarPaddingValues),
                isConnected = state.isConnected,
                username = state.username,
                onSettingsClick = {
                    onActions(NoteListActions.NavigateToSettings)
                }
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
                        onActions(NoteListActions.NavigateToNoteDetail(noteId = null))
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
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            NoteMarkList(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(noteMarkListPaddingValues)
                    .padding(innerPadding),
                isLoading = state.isLoading,
                verticalSpace = verticalSpace,
                horizontalSpace = horizontalSpace,
                notePagingFlow = notePagingFlow,
                staggeredGridCells = staggeredGridCells,
                onNoteClick = { noteUi ->
                    onActions(NoteListActions.NavigateToNoteDetail(noteId = noteUi.id))
                },
                onNoteDelete = { noteUi ->
                    onActions(NoteListActions.OnNoteDelete(noteId = noteUi.id!!))
                },
                refreshNotLoading = {
                    onActions(NoteListActions.IsLoading(isLoading = false))
                },
                windowSizeClass = windowSizeClass,
            )
            NoteMarkDialog(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                showDialog = state.showDialog,
                titleResId = stringResource(R.string.dialog_delete_title),
                isLoading = state.dialogLoading,
                bodyResId = stringResource(R.string.dialog_delete_body_text),
                confirmButtonId = stringResource(R.string.delete),
                dismissButtonId = stringResource(R.string.cancel),
                onConfirmClick = {
                    onActions(NoteListActions.OnDialogConfirm)
                },
                onDismissClick = {
                    onActions(NoteListActions.OnDialogDismiss)
                },
            )
        }
    }
}

@Composable
fun NoteListTopBar(
    modifier: Modifier = Modifier,
    isConnected: Boolean,
    username: String,
    onSettingsClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier,
                text = stringResource(R.string.noteMark),
                style = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 20.sp, lineHeight = 24.sp, letterSpacing = 0.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            //!isConnected = no internet connection available
            if (!isConnected)
                Icon(
                    modifier = Modifier.padding(start = 10.dp),
                    imageVector = CloudOff,
                    contentDescription = stringResource(R.string.cloud_off),
                    tint = Color.Unspecified
                )

        }


        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = onSettingsClick,
            ) {
                Icon(
                    imageVector = NoteMarkIcons.Settings,
                    contentDescription = stringResource(R.string.settings),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

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
}

@Composable
fun NoteMarkList(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    verticalSpace: Dp,
    horizontalSpace: Dp,
    notePagingFlow: LazyPagingItems<NoteUi>,
    staggeredGridCells: StaggeredGridCells,
    onNoteClick: (NoteUi) -> Unit,
    onNoteDelete: (NoteUi) -> Unit,
    refreshNotLoading: () -> Unit,
    windowSizeClass: WindowSizeClass
) {
    val state = rememberLazyStaggeredGridState()

    val loadState = notePagingFlow.loadState

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        when (loadState.refresh) {
            is LoadState.Error -> {
                Timber.tag("NoteRemoteMediator").d("refresh: error")
                //set loading to false
                refreshNotLoading()
                PagingError(
                    modifier = Modifier,
                    id = R.string.refresh,
                    onPagingPerform = {
                        notePagingFlow.refresh()
                    }
                )
            }

            LoadState.Loading -> Timber.tag("NoteRemoteMediator").d("refresh: loading")
            is LoadState.NotLoading -> {
                Timber.tag("NoteRemoteMediator").d("refresh: notLoading")
                //set loading to false
                refreshNotLoading()
            }
        }

        when (loadState.prepend) {
            is LoadState.Error -> Timber.tag("NoteRemoteMediator").d("prepend: error")
            LoadState.Loading -> Timber.tag("NoteRemoteMediator").d("prepend: loading")
            is LoadState.NotLoading -> Timber.tag("NoteRemoteMediator").d("prepend: notLoading")
        }

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.size(200.dp))
            }
        } else {
            if (notePagingFlow.itemCount != 0) {
                LazyVerticalStaggeredGrid(
                    modifier = Modifier.fillMaxWidth(),
                    columns = staggeredGridCells,
                    verticalItemSpacing = verticalSpace,
                    horizontalArrangement = Arrangement.spacedBy(horizontalSpace),
                    state = state,
                ) {
                    items(count = notePagingFlow.itemCount, key = notePagingFlow.itemKey {
                        it.id!!
                    }) { index ->
                        val note = notePagingFlow[index]
                        note?.let {
                            NoteListItem(
                                modifier = Modifier.fillMaxWidth(),
                                noteUi = note,
                                onNoteClick = onNoteClick,
                                onNoteDelete = onNoteDelete,
                                windowSizeClass = windowSizeClass
                            )
                        }
                    }
                    when (loadState.append) {
                        is LoadState.Error -> {
                            Timber.tag("NoteRemoteMediator").d("append: error")
                            //retry() resume from last page
                            item(
                                span = StaggeredGridItemSpan.FullLine
                            ) {
                                PagingError(
                                    id = R.string.retry,
                                    onPagingPerform = { notePagingFlow.retry() }
                                )
                            }
                        }

                        LoadState.Loading -> {
                            Timber.tag("NoteRemoteMediator").d("append: loading")
                            //GridItemSpan(maxLineSpan) placing it in single row to center
                            item(span = StaggeredGridItemSpan.FullLine) {
                                Column {
                                    LoadingItems()
                                    Spacer(modifier = Modifier.height(12.dp))
                                }
                            }
                        }

                        is LoadState.NotLoading -> Timber.tag("NoteRemoteMediator").d("append: notLoading")
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = 80.dp)
                            .align(Alignment.TopCenter),
                        text = stringResource(R.string.empty_notes),
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        ),
                    )
                }
            }
        }
    }
}

@Composable
fun NoteListItem(
    modifier: Modifier = Modifier,
    noteUi: NoteUi,
    onNoteClick: (NoteUi) -> Unit,
    onNoteDelete: (NoteUi) -> Unit,
    windowSizeClass: WindowSizeClass
) {
    NoteMarkTheme {
        Surface(
            //clip is used because when using shape if we click. longClick on the item it will show background
            //without roundedCornerRadius
            modifier = modifier
                .clip(MaterialTheme.shapes.small)
                .combinedClickable(
                    onClick = {
                        onNoteClick(noteUi)
                    },
                    onLongClick = {
                        onNoteDelete(noteUi)
                    }
                ),
            color = SurfaceLowest,
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
                    text = TextUtil.textLimit(
                        text = noteUi.content,
                        windowSizeClass = windowSizeClass
                    ),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
    }
}

@Composable
fun PagingError(
    modifier: Modifier = Modifier,
    @StringRes id: Int,
    onPagingPerform: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            modifier = Modifier,
            text = stringResource(R.string.no_internet),
            style = MaterialTheme.typography.titleSmall
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            modifier = Modifier,
            onClick = {
                onPagingPerform()
            }
        ) {
            Text(
                modifier = Modifier,
                text = stringResource(id),
                color = Color.White
            )
        }
    }
}

@Composable
fun LoadingItems() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(52.dp)
                .padding(8.dp),
            strokeWidth = 5.dp,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
        )
    }
}

