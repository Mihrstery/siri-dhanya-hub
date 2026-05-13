package com.example.nithaiproject

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage

data class RecipeItem(
    val title: String,
    val milletType: String,
    val isFavorite: Boolean,
    val imageRes: Int
)

@Composable
fun RecipeLabPage() {
    var selectedRecipe by remember { mutableStateOf<RecipeItem?>(null) }
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        RecipeSearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it }
        )
        RecipeGrid(
            searchQuery = searchQuery,
            onRecipeClick = { selectedRecipe = it }
        )
    }

    selectedRecipe?.let { recipe ->
        RecipeDetailPopup(
            recipe = recipe,
            onDismiss = { selectedRecipe = null }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeSearchBar(query: String, onQueryChange: (String) -> Unit) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Search recipes...", color = BarkBrown.copy(alpha = 0.5f)) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = BarkBrown.copy(alpha = 0.5f)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
            .shadow(2.dp, RoundedCornerShape(999.dp)),
        shape = RoundedCornerShape(999.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedBorderColor = Terracotta,
            unfocusedBorderColor = BarkBrown.copy(alpha = 0.1f)
        )
    )
}

@Composable
fun RecipeGrid(searchQuery: String, onRecipeClick: (RecipeItem) -> Unit) {
    val allRecipes = listOf(
        RecipeItem("ರಾಗಿ ಮುದ್ದೆ / Ragi Mudde", "Finger Millet", true, R.drawable.ragi),
        RecipeItem("ನವಣೆ ಉಪ್ಪಿಟ್ಟು / Navane Upma", "Foxtail Millet", false, R.drawable.upma),
        RecipeItem("ಸಜ್ಜೆ ರೊಟ್ಟಿ / Sajje Rotti", "Pearl Millet", true, R.drawable.sajji),
        RecipeItem("ಅರ್ಕ ದೋಸೆ / Arkha Dosa", "Kodo Millet", false, R.drawable.dosa)
    )

    val filteredRecipes = if (searchQuery.isEmpty()) {
        allRecipes
    } else {
        allRecipes.filter { 
            it.title.contains(searchQuery, ignoreCase = true) || 
            it.milletType.contains(searchQuery, ignoreCase = true) 
        }
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        items(filteredRecipes) { recipe ->
            RecipeCard(recipe, onViewRecipe = { onRecipeClick(recipe) })
        }
    }
}

@Composable
fun RecipeCard(recipe: RecipeItem, onViewRecipe: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .border(0.5.dp, BarkBrown.copy(alpha = 0.1f), RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(SurfaceVariantBeige)
            ) {
                AsyncImage(
                    model = recipe.imageRes,
                    contentDescription = recipe.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                
                Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.05f)))

                IconButton(
                    onClick = { /* Toggle Favorite */ },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.8f))
                ) {
                    Icon(
                        imageVector = if (recipe.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (recipe.isFavorite) Terracotta else BarkBrown.copy(alpha = 0.5f)
                    )
                }
            }
            
            Column(modifier = Modifier.padding(24.dp)) {
                Text(text = recipe.title, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = BarkBrown)
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = SageGreen.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(999.dp),
                        border = BorderStroke(0.5.dp, SageGreen.copy(alpha = 0.2f))
                    ) {
                        Text(
                            text = recipe.milletType,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = SageGreen
                        )
                    }
                    
                    TextButton(onClick = onViewRecipe, contentPadding = PaddingValues(0.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("VIEW RECIPE", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Terracotta, letterSpacing = 1.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp), tint = Terracotta)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RecipeDetailPopup(recipe: RecipeItem, onDismiss: () -> Unit) {
    val context = LocalContext.current
    Dialog(onDismissRequest = onDismiss, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Surface(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            color = Color.White
        ) {
            Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                Box(modifier = Modifier.fillMaxWidth().height(250.dp).background(SurfaceVariantBeige)) {
                    AsyncImage(model = recipe.imageRes, contentDescription = recipe.title, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                    Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.1f)))
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.align(Alignment.TopEnd).padding(16.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.8f))
                    ) { Icon(Icons.Default.Close, contentDescription = "Close", tint = BarkBrown) }
                }

                Column(modifier = Modifier.padding(24.dp)) {
                    Surface(
                        color = SageGreen.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(999.dp),
                        border = BorderStroke(0.5.dp, SageGreen.copy(alpha = 0.2f))
                    ) {
                        Text(text = recipe.milletType, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SageGreen)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = recipe.title, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = BarkBrown)
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(text = "Ingredients", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Terracotta)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "• 1 cup ${recipe.milletType}\n• 2 cups Water\n• Salt to taste\n• Optional seasonings", fontSize = 16.sp, color = BarkBrown.copy(alpha = 0.8f), lineHeight = 24.sp)
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(text = "Preparation", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Terracotta)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "1. Clean and wash the ${recipe.milletType} thoroughly.\n2. Soak for at least 30 minutes.\n3. Boil water, add millet and salt.\n4. Cook on low heat for 15-20 mins.\n5. Rest for 5 mins before serving.", fontSize = 16.sp, color = BarkBrown.copy(alpha = 0.8f), lineHeight = 24.sp)
                    
                    Spacer(modifier = Modifier.height(40.dp))
                    Button(onClick = onDismiss, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Terracotta), shape = RoundedCornerShape(12.dp)) {
                        Text("Done", modifier = Modifier.padding(vertical = 8.dp))
                    }
                    OutlinedButton(
                        onClick = {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_SUBJECT, "Recipe: ${recipe.title}")
                                putExtra(Intent.EXTRA_TEXT, "Check out this recipe for ${recipe.title} on Siri-Dhanya Hub!")
                            }
                            context.startActivity(Intent.createChooser(shareIntent, "Share Recipe"))
                        },
                        modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.5.dp, BarkBrown.copy(alpha = 0.2f))
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null, tint = BarkBrown)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Share Recipe", color = BarkBrown)
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeLabPagePreview() {
    MaterialTheme {
        Box(modifier = Modifier.background(OatBeige)) {
            RecipeLabPage()
        }
    }
}
