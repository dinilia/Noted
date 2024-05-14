package com.example.noted.ui.screen.notes.components.navbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noted.R
import com.example.noted.utils.components.navbar.TopNavBarIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesAppBar(
    isMarking: Boolean,
    markedNoteListSize: Int,
    isSearching: Boolean,
    searchedTitle: String,
    toggleDrawerCallback: () -> Unit,
    selectAllCallback: () -> Unit,
    unSelectAllCallback: () -> Unit,
    onSearchValueChange: (String) -> Unit,
    closeMarkingCallback: () -> Unit,
    deleteCallback: () -> Unit,
    checkedGridState: MutableState<Boolean>
) {
    val expanded = remember { mutableStateOf(false) }

    TopAppBar(title = {
        if (isMarking) {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { expanded.value = true }) {
                NavText(
                    text = markedNoteListSize.toString(),
                    size = 16.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
                NavText(
                    text = stringResource(R.string.selected_text),
                    size = 16.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = stringResource(R.string.dropdown_nav_icon),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            DropdownMenu(expanded = expanded.value,
                onDismissRequest = { expanded.value = false }) {
                DropdownMenuItem(text = {
                    Text(
                        text = stringResource(R.string.select_all),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }, onClick = {
                    selectAllCallback()
                    expanded.value = false
                })
                HorizontalDivider()
                DropdownMenuItem(text = {
                    Text(
                        text = stringResource(R.string.unselect_all),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }, onClick = {
                    unSelectAllCallback()
                    expanded.value = false
                })
            }
        } else if (isSearching) {
            TextField(value = searchedTitle,
                onValueChange = {
                    onSearchValueChange(it)
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
                ),
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp,
                    lineHeight = 0.sp
                ),
                singleLine = true,
                modifier = Modifier
                    .padding(bottom = 0.dp),
                placeholder = {
                    NavText(text = stringResource(R.string.search_textField),
                        size = 16.sp,
                        modifier = Modifier.semantics { })
                })
        } else {
            Text(
                text = stringResource(id = R.string.title),
                color = MaterialTheme.colorScheme.primary,
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold, fontSize = 20.sp
                ),
                modifier = Modifier
                    .padding(start = 4.dp),
            )
        }
    }, colors = topAppBarColors(
        containerColor = MaterialTheme.colorScheme.surface,
    ), navigationIcon = {
        LeadingIcon(isMarking = isMarking, closeMarkingCallback = {
            closeMarkingCallback()
        }, toggleDrawerCallback = { toggleDrawerCallback() })
    }, actions = {
        TrailingMenuIcons(
            isMarking = isMarking,
            markedItemsCount = markedNoteListSize,
            deleteCallback = {
                deleteCallback()
            },
            checkedGridState = checkedGridState
        )
    })
}

@Composable
fun LeadingIcon(
    isMarking: Boolean, closeMarkingCallback: () -> Unit, toggleDrawerCallback: () -> Unit
) {
    if (isMarking) {
        TopNavBarIcon(
            Icons.Filled.Close,
            stringResource(R.string.close_nav_icon)
        ) {
            closeMarkingCallback()
        }
    } else {
        TopNavBarIcon(
            Icons.Filled.Menu,
            stringResource(R.string.drawer_nav_icon)
        ) {
            toggleDrawerCallback()
        }
    }
}

@Composable
fun TrailingMenuIcons(
    isMarking: Boolean,
    markedItemsCount: Int,
    deleteCallback: () -> Unit,
    checkedGridState: MutableState<Boolean>
) {
    if (isMarking) {
        TopNavBarIcon(
            Icons.Filled.Delete,
            stringResource(R.string.delete_nav_icon),
            tint = if (markedItemsCount > 0) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.primary.copy(
                alpha = 0.6f
            )
        ) {
            if (markedItemsCount > 0) deleteCallback()
        }
    } else {
        IconToggleButton(
            checked = checkedGridState.value,
            onCheckedChange = {
                checkedGridState.value = !checkedGridState.value
            },
            modifier = Modifier
                .padding(2.dp)
        ) {
            TopNavBarIcon(
                if (checkedGridState.value) {
                    Icons.Filled.GridView
                } else {
                    Icons.AutoMirrored.Filled.Sort
                },
                stringResource(R.string.sort_nav_icon)
            ) {
                checkedGridState.value = !checkedGridState.value
            }
        }
    }
}

@Composable
fun NavText(text: String, size: TextUnit, modifier: Modifier) {
    Text(
        text = text, fontSize = size, color = MaterialTheme.colorScheme.primary, modifier = modifier
    )
}