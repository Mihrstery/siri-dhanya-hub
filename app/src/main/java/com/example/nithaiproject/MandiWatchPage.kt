package com.example.nithaiproject

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MandiWatchPage() {
    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp)) {
        MandiHeader()
        MandiFilters()
        MandiGrid()
    }
}

@Composable
fun MandiHeader() {
    Column(modifier = Modifier.padding(top = 24.dp, bottom = 24.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Mandi Watch", fontSize = 32.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF745853))
            Spacer(modifier = Modifier.width(12.dp))
            Surface(
                color = Terracotta.copy(alpha = 0.1f),
                shape = RoundedCornerShape(999.dp),
                border = androidx.compose.foundation.BorderStroke(0.5.dp, Terracotta)
            ) {
                Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Terracotta))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("LIVE", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Terracotta)
                }
            }
        }
        Text(text = "Live pricing and market trends for premium millets.", fontSize = 18.sp, color = BarkBrown.copy(alpha = 0.7f), modifier = Modifier.padding(top = 4.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MandiFilters() {
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("Bengaluru Mandi", "Davangere APMC", "Mysuru Market", "Hubli Hub")
    var selectedOption by remember { mutableStateOf(options[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Terracotta, unfocusedBorderColor = BarkBrown.copy(alpha = 0.2f)),
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable).fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { selectionOption ->
                DropdownMenuItem(text = { Text(selectionOption) }, onClick = { selectedOption = selectionOption; expanded = false })
            }
        }
    }
}

data class MandiItem(
    val name: String,
    val scientificName: String,
    val price: String,
    val trend: String,
    val isUp: Boolean?,
    val high: String,
    val low: String,
    val icon: ImageVector
)

@Composable
fun MandiGrid() {
    val items = listOf(
        MandiItem("Sajje", "Pearl Millet", "₹32", "+2.4%", true, "₹35", "₹30", Icons.Default.Eco),
        MandiItem("Navane", "Foxtail Millet", "₹45", "-1.2%", false, "₹48", "₹44", Icons.Default.Spa),
        MandiItem("Ragi", "Finger Millet", "₹38", "+0.5%", true, "₹39", "₹35", Icons.Default.Grass),
        MandiItem("Saame", "Little Millet", "₹52", "0.0%", null, "₹54", "₹50", Icons.Default.Grain)
    )

    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp), contentPadding = PaddingValues(bottom = 100.dp)) {
        items(items) { item -> MandiCard(item) }
    }
}

@Composable
fun MandiCard(item: MandiItem) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceVariantBeige),
        modifier = Modifier.fillMaxWidth().border(1.dp, BarkBrown.copy(alpha = 0.1f), RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(48.dp).clip(CircleShape).background(SageGreen.copy(alpha = 0.2f)), contentAlignment = Alignment.Center) {
                        Icon(item.icon, contentDescription = null, tint = SageGreen)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(item.name, fontSize = 24.sp, fontWeight = FontWeight.SemiBold, color = BarkBrown)
                        Text(item.scientificName, fontSize = 16.sp, color = BarkBrown.copy(alpha = 0.6f))
                    }
                }
                Column(horizontalAlignment = Alignment.End) {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(item.price, fontSize = 32.sp, fontWeight = FontWeight.SemiBold, color = Terracotta)
                        Text("/kg", fontSize = 16.sp, color = BarkBrown.copy(alpha = 0.6f), modifier = Modifier.padding(bottom = 4.dp))
                    }
                    val trendColor = when (item.isUp) {
                        true -> SageGreen
                        false -> Color(0xFFBA1A1A)
                        else -> BarkBrown.copy(alpha = 0.6f)
                    }
                    val trendIcon = when (item.isUp) {
                        true -> Icons.AutoMirrored.Filled.TrendingUp
                        false -> Icons.AutoMirrored.Filled.TrendingDown
                        else -> Icons.Default.HorizontalRule
                    }
                    Row(
                        modifier = Modifier.clip(RoundedCornerShape(999.dp)).background(trendColor.copy(alpha = 0.1f)).padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(trendIcon, contentDescription = null, modifier = Modifier.size(14.dp), tint = trendColor)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(item.trend, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = trendColor)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = BarkBrown.copy(alpha = 0.1f))
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "7-Day High: " + item.high, fontSize = 16.sp, color = BarkBrown.copy(alpha = 0.7f))
                Text(text = "7-Day Low: " + item.low, fontSize = 16.sp, color = BarkBrown.copy(alpha = 0.7f))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MandiWatchPagePreview() {
    MaterialTheme {
        Box(modifier = Modifier.background(OatBeige)) {
            MandiWatchPage()
        }
    }
}
