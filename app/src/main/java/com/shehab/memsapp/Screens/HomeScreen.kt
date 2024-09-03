
package com.shehab.memsapp.Screens
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
 import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
 import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.shehab.memsapp.Model.Meme

@Composable
fun HomeScreen(memeList: List<Meme>, navController: NavHostController) {
    var textState by remember { mutableStateOf(TextFieldValue()) }

    Column(Modifier.fillMaxSize()) {
        SearchView(
            state = textState,
            onValueChange = { newValue -> textState = newValue },
            placeholder = "Search here...",
            modifier = Modifier.padding(8.dp)
        )

        val searchedText = textState.text

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(10.dp)
        ) {
            items(
                items = memeList.filter { it.name.contains(searchedText, true) },
                key = { it.id }
            ) { meme ->
                ItemMeme(

                    itemName = meme.name,
                    itemUrl = meme.url,
                    navController = navController,
                    modifier = Modifier
                        .padding(8.dp)

                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(
    state: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    placeholder: String,
    modifier: Modifier,

) {
    TextField(
        value = state,
        onValueChange = onValueChange,
        placeholder = { Text(text = placeholder) },
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White),
        colors = TextFieldDefaults.textFieldColors(containerColor =Color(0xffffc107)),
        singleLine = true,
        maxLines = 1
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemMeme(
    modifier: Modifier = Modifier,
    itemName: String,
    itemUrl: String,
    navController: NavHostController
) {
    Card(
        modifier = modifier
            .wrapContentSize()
            .padding(10.dp)
            .clickable {

                navController.navigate("DetailsScreen?name=${itemName}&url=${itemUrl}")
            },
        colors = CardDefaults.cardColors(containerColor = Color(0xffffc107))
    ) {
        Column(
            modifier = Modifier.padding(7.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = itemUrl,
                contentDescription = itemName,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = itemName,
                modifier = Modifier
                    .fillMaxWidth()
                    .basicMarquee(),
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}




















