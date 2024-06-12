package com.example.ad_gdynia

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.ad_gdynia.database.entity.Location
import kotlinx.coroutines.flow.Flow

// Screen with a nav bar
// option 1 is for list of all locations
// option 2 is for the closest location
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    locationsFlow: Flow<List<Location>>,
    locationProvider: LocationProvider,
    onItemClick: (location: Location) -> Unit,
)
    {

    var selectedTab by remember { mutableStateOf(0) }

    val tabTitles = listOf(stringResource(id = R.string.all_locations), stringResource(id = R.string.closest_location))

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.topbar_title)) },
            )
        },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {
                TabRow(selectedTabIndex = selectedTab) {
                    tabTitles.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = { Text(title) }
                        )
                    }
                }

                when (selectedTab) {
                    0 -> LocationList(locationsFlow, onItemClick)
                    1 -> ClosestLocationView(locationsFlow, locationProvider, onItemClick)
                }
            }
        }
    )
}