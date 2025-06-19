package com.twugteam.admin.notemark.features.note.presentation.noteList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.core.presentation.designsystem.NoteMarkTheme
import com.twugteam.admin.notemark.core.presentation.designsystem.OnSurfaceVar
import com.twugteam.admin.notemark.core.presentation.designsystem.Surface
import com.twugteam.admin.notemark.core.presentation.designsystem.SurfaceLowest
import com.twugteam.admin.notemark.core.presentation.ui.getInitials
import com.twugteam.admin.notemark.features.note.presentation.model.NoteUi

@Composable
fun NoteListScreenMobilePortrait(
    modifier: Modifier = Modifier
) {
        Scaffold(
            topBar = {
                NoteListTopBar(
                    modifier = modifier,
                    username = "PL"
                )
            },
            floatingActionButton = {
                Box(
                    modifier = Modifier.size(64.dp).clip(
                        RoundedCornerShape(20.dp)
                    ).background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF58A1F8),
                                Color(0xFF5A4CF7),
                            )
                        )
                    ).clickable{
                        //
                    },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        ) { innerPadding ->
            NoteMarkList(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Surface)
                    .padding(top = 16.dp, start = 16.dp)
                    .padding(innerPadding),
                noteMarkList = list
            )
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
                text = username.getInitials(),
                style = MaterialTheme.typography.titleSmall.copy(
                    color = MaterialTheme.colorScheme.onPrimary
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

val list = listOf(
    NoteUi(
        id = "1",
        title = "Title",
        content = "Augue non mauris ante viverra ut arcu sed ut lectus interdum morbi sed leo purus gravida non id mi augue.",
        createdAt = "19 APR",
        lastEditedAt = "TODO()"
    ),
    NoteUi(
        id = "2",
        title = "Title of the note",
        content = "Augue non mauris ante viverra ut arcu sed ut lectus interdum morbi sed leo purus gravida non id mi augue.",
        createdAt = "19 APR",
        lastEditedAt = "TODO()"
    )
)

@Composable
fun NoteMarkList(
    modifier: Modifier = Modifier,
    noteMarkList: List<NoteUi>,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = rememberLazyGridState()
    ) {
        items(items = noteMarkList, key = {
            it.id
        }) { noteMark->
            NoteListItem(
                modifier = Modifier.fillMaxWidth().padding(end= 16.dp),
                noteUi = noteMark
            )
        }
    }
}

@Composable
fun NoteListItem(
    modifier: Modifier = Modifier,
    noteUi: NoteUi,
) {
    NoteMarkTheme {
        Surface(
            modifier = modifier,
            color = SurfaceLowest,
            shape = MaterialTheme.shapes.small
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = noteUi.createdAt,
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
                    text = noteUi.content,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = OnSurfaceVar
                    )
                )
            }
        }

    }
}