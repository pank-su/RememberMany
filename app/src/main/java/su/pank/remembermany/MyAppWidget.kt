package su.pank.remembermany

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.glance.ColorFilter
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.ContentScale
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.size
import androidx.glance.text.Text
import androidx.glance.unit.ColorProvider

class MyWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {

        // In this method, load data needed to render the AppWidget.
        // Use `withContext` to switch to another thread for long running
        // operations.

        provideContent {
            this.Content()
        }


    }

    @SuppressLint("RemoteViewLayout")
    @Composable
    fun Content() {
        GlanceTheme {


            Row(modifier = GlanceModifier.fillMaxWidth().background(GlanceTheme.colors.surface)) {

                Text(text = "Панков Вася")
            }


        }
    }
}