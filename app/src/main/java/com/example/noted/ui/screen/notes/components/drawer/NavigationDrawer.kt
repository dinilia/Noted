package com.example.noted.ui.screen.notes.components.drawer

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noted.R
import com.example.noted.utils.ColorSchemeName
import com.example.noted.utils.Constants
import com.example.noted.utils.NoteDataStore
import kotlinx.coroutines.launch

@Composable
fun NavDrawer(
    drawerState: DrawerState, content: @Composable () -> Unit,
) {
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp

    val dataStore = NoteDataStore(LocalContext.current)

    var currentScheme by remember { mutableStateOf("") }

    LaunchedEffect(key1 = Unit) {
        dataStore.getStringData(Constants.COLOR_SCHEME).collect {
            currentScheme = it
        }
    }

    val scope = rememberCoroutineScope()

    fun setColorScheme(colorScheme: String) {
        scope.launch {
            Log.d("state", colorScheme)
            dataStore.setStringData(Constants.COLOR_SCHEME, colorScheme)
        }
    }

    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        ModalDrawerSheet(
            drawerShape = RectangleShape,
            drawerContainerColor = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxHeight()
                .padding(0.dp)
                .width(screenWidth - 50.dp)
        ) {
            NavDrawerItemColorSchemeSwitch(
                leftIcon = Icons.Filled.Bedtime,
                title = stringResource(id = R.string.theme_setting),
                currentScheme = currentScheme,
                testTagName = "",
            ) {
                setColorScheme(it)
            }
        }
    }, content = { content() })
}

@Composable
fun NavDrawerItemColorSchemeSwitch(
    leftIcon: ImageVector,
    title: String,
    testTagName: String,
    currentScheme: String,
    onColorSchemePicked: (scheme: String) -> Unit
) {
    val lightModeString = stringResource(R.string.switch_to_light_mode)
    val darkModeString = stringResource(R.string.switch_to_dark_mode)
    val subtitle = if (currentScheme == ColorSchemeName.DARK_MODE)
        lightModeString else darkModeString


    fun onTileClicked() {
        if (subtitle == darkModeString) {
            onColorSchemePicked(ColorSchemeName.DARK_MODE)
        } else {
            onColorSchemePicked(ColorSchemeName.LIGHT_MODE)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 12.dp, top = 8.dp)
            .semantics { testTag = testTagName }
            .clickable {
                onTileClicked()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background)
        ) {
            Box(modifier = Modifier.padding(8.dp)) {
                Icon(
                    leftIcon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(start = 12.dp, bottom = 1.dp)
                .weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 2.dp)
            )
            Text(
                text = subtitle,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Icon(
            Icons.Filled.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Preview
@Composable
fun DrawerPreview() {
    val drawerState = rememberDrawerState(DrawerValue.Open)
    NavDrawer(drawerState = drawerState, content = { })
}

