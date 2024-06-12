import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageDecoderDecoder
import com.example.ad_gdynia.R
import com.example.ad_gdynia.database.entity.Location

// Location element
@Composable
fun LocationItem(
    location: Location,
    onNavigate: (Location) -> Unit
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            }
        }
        .build()

    // Image loader
    val painter = rememberAsyncImagePainter(
        model = location.image,
        imageLoader = imageLoader
    )

    // Card type clickable element for a list
    Card(
        modifier = Modifier
            .padding(16.dp)
            .clickable { onNavigate(location) }
    ) {
        Column {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = location.name,
                modifier = Modifier.padding(horizontal = 16.dp),
                style = TextStyle(fontSize = 20.sp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onNavigate(location) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(text = stringResource(id = R.string.show_location))
            }
        }
    }
}