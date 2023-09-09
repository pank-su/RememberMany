package su.pank.remembermany

import android.content.Context


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalSize
import androidx.glance.action.ActionParameters
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.size
import androidx.glance.text.Text
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MyWidget : GlanceAppWidget() {

    override val sizeMode = SizeMode.Exact

    lateinit var context: Context

    var isEdited = false

    override suspend fun provideGlance(context: Context, id: GlanceId) {

        // In this method, load data needed to render the AppWidget.
        // Use `withContext` to switch to another thread for long running
        // operations.

        this.context = context


        val counterData = context.dataStore.data.map {
            it[KEY_COUNTER] ?: 0
        }
        provideContent {
            this.Content(counterData)
        }
    }

    private val KEY_COUNTER = intPreferencesKey("counter")

    @Composable
    fun Content(counterData: Flow<Int>) {
        val counter by counterData.collectAsState(0)
        val size = LocalSize.current


        GlanceTheme {
            Row(
                modifier = GlanceModifier.fillMaxSize().background(GlanceTheme.colors.surface),
                verticalAlignment = Alignment.Vertical.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    provider = ImageProvider(R.drawable.minus_sign_of_a_line_in_horizontal_position_svgrepo_com),
                    contentDescription = null,
                    modifier = GlanceModifier.size(size.width * 0.2f).clickable {
                        if (isEdited){
                            isEdited = false
                            return@clickable
                        }
                        isEdited = true
                        CoroutineScope(Dispatchers.IO).launch {
                            context.dataStore.edit {
                                it[KEY_COUNTER] = (it[KEY_COUNTER] ?: 0) - 1
                            }
                        }
                    }
                )
                Spacer(GlanceModifier.defaultWeight())
                Text(text = counter.toString())
                Spacer(GlanceModifier.defaultWeight())
                Image(
                    provider = ImageProvider(R.drawable.plus_svgrepo_com),
                    contentDescription = null,
                    modifier = GlanceModifier.size(size.width * 0.2f).clickable{
                        if (isEdited){
                            isEdited = false
                            return@clickable
                        }
                        isEdited = true
                        CoroutineScope(Dispatchers.IO).launch {
                            context.dataStore.edit {
                                it[KEY_COUNTER] = (it[KEY_COUNTER] ?: 0) + 1
                            }
                        }
                    }
                )
            }
        }
    }
}
