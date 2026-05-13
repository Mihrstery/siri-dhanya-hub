package com.example.nithaiproject

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FPOConnectPage() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(top = 24.dp, bottom = 100.dp)
    ) {
        item { FPOHeader() }
        item { FPOSearchAndFilter() }
        item { FeaturedFPOCard() }
        item { RegularFPOCard(
            name = "Kurnool Millets Sangham",
            location = "Kurnool Rural • 32.5 km away",
            specialty = "Pearl Millet (Bajra)"
        ) }
        item { RegularFPOCard(
            name = "Rayalaseema Organic Co-op",
            location = "Kadapa • 58.0 km away",
            specialty = "Finger Millet, Sorghum",
            isNew = true
        ) }
        item { PromoCard() }
    }
}

@Composable
fun FPOHeader() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "FPO Connect",
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold,
            color = BarkBrown
        )
        Text(
            text = "Connect directly with local Farmer Producer Organizations for bulk organic millet sourcing. Transparent pricing, verified quality.",
            fontSize = 18.sp,
            color = BarkBrown.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun FPOSearchAndFilter() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var text by remember { mutableStateOf("") }
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text("Search FPOs...", color = BarkBrown.copy(alpha = 0.5f)) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = BarkBrown.copy(alpha = 0.5f)) },
            modifier = Modifier
                .weight(1f)
                .shadow(2.dp, RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = SurfaceVariantBeige,
                unfocusedContainerColor = SurfaceVariantBeige,
                focusedBorderColor = Terracotta,
                unfocusedBorderColor = Color.Transparent
            )
        )
        
        OutlinedButton(
            onClick = { /* Filter */ },
            modifier = Modifier.height(56.dp),
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, BarkBrown.copy(alpha = 0.2f))
        ) {
            Icon(Icons.Default.Tune, contentDescription = null, tint = BarkBrown)
            Spacer(Modifier.width(8.dp))
            Text("FILTER", color = BarkBrown, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FeaturedFPOCard() {
    val context = LocalContext.current
    val fpoPhoneNumber = "+919876543210"

    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceVariantBeige),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, BarkBrown.copy(alpha = 0.1f), RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {

            // --- RESTORED TOP CONTENT ---
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Surface(
                    color = SageGreen.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(999.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Verified, contentDescription = null, modifier = Modifier.size(14.dp), tint = SageGreen)
                        Spacer(Modifier.width(4.dp))
                        Text("PREFERRED PARTNER", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = SageGreen)
                    }
                }
                Surface(
                    color = Terracotta.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(999.dp)
                ) {
                    Text(
                        "ORGANIC CERTIFIED",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Terracotta
                    )
                }
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Anantapur Dryland Farmers Collective",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = BarkBrown,
                        lineHeight = 28.sp
                    )
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(18.dp), tint = BarkBrown.copy(alpha = 0.6f))
                        Spacer(Modifier.width(4.dp))
                        Text("Anantapur District • 14.2 km away", fontSize = 14.sp, color = BarkBrown.copy(alpha = 0.6f))
                    }
                }
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Agriculture, contentDescription = null, modifier = Modifier.size(32.dp), tint = Terracotta)
                }
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Key Offerings", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = BarkBrown.copy(alpha = 0.6f))
                    FlowRow(modifier = Modifier.padding(top = 4.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Tag("Foxtail Millet")
                        Tag("Little Millet")
                    }
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text("Current Stock", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = BarkBrown.copy(alpha = 0.6f))
                    Text("1,200 kg Available", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = BarkBrown, modifier = Modifier.padding(top = 4.dp))
                }
            }
            // --- END RESTORED CONTENT ---

            // NEW WORKING BUTTONS
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:$fpoPhoneNumber")
                        }
                        context.startActivity(intent)
                    },
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Terracotta)
                ) {
                    Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("CALL NOW", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
                OutlinedButton(
                    onClick = {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("smsto:$fpoPhoneNumber")
                            putExtra("sms_body", "Namaskara! I am interested in sourcing from Anantapur Dryland Farmers Collective.")
                        }
                        context.startActivity(intent)
                    },
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, BarkBrown.copy(alpha = 0.2f))
                ) {
                    Icon(Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(18.dp), tint = BarkBrown)
                    Spacer(Modifier.width(8.dp))
                    Text("MESSAGE", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = BarkBrown)
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RegularFPOCard(name: String, location: String, specialty: String, isNew: Boolean = false) {
    val context = LocalContext.current
    val fpoPhoneNumber = "+919876543211"

    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceVariantBeige),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, BarkBrown.copy(alpha = 0.1f), RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {

            // --- RESTORED TOP CONTENT ---
            if (isNew) {
                Surface(
                    color = Terracotta.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(999.dp)
                ) {
                    Text(
                        "NEW",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Terracotta
                    )
                }
            }

            Column {
                Text(name, fontSize = 22.sp, fontWeight = FontWeight.SemiBold, color = BarkBrown, lineHeight = 26.sp)
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(16.dp), tint = BarkBrown.copy(alpha = 0.6f))
                    Spacer(Modifier.width(4.dp))
                    Text(location, fontSize = 14.sp, color = BarkBrown.copy(alpha = 0.6f))
                }
            }

            Column {
                Text("Specialty", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = BarkBrown.copy(alpha = 0.6f))
                FlowRow(modifier = Modifier.padding(top = 4.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    specialty.split(", ").forEach { Tag(it) }
                }
            }
            // --- END RESTORED CONTENT ---

            // NEW WORKING BUTTONS
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:$fpoPhoneNumber")
                        }
                        context.startActivity(intent)
                    },
                    modifier = Modifier.size(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Terracotta)
                ) {
                    Icon(Icons.Default.Call, contentDescription = null)
                }
                OutlinedButton(
                    onClick = {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("smsto:$fpoPhoneNumber")
                            putExtra("sms_body", "Namaskara! I found $name on Siri-Dhanya Hub and would like to connect.")
                        }
                        context.startActivity(intent)
                    },
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, BarkBrown.copy(alpha = 0.2f))
                ) {
                    Icon(Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(18.dp), tint = BarkBrown)
                    Spacer(Modifier.width(8.dp))
                    Text("MESSAGE", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = BarkBrown)
                }
            }
        }
    }
}

@Composable
fun PromoCard() {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(Icons.Default.Hub, contentDescription = null, modifier = Modifier.size(48.dp), tint = Terracotta)
            Text("Expanding Network", fontSize = 24.sp, fontWeight = FontWeight.SemiBold, color = BarkBrown)
            Text(
                "We are onboarding 15 new FPOs next month. Stay tuned for wider variety and better local sourcing.",
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                fontSize = 16.sp,
                color = BarkBrown.copy(alpha = 0.7f)
            )
            TextButton(onClick = { /* Learn More */ }) {
                Text("LEARN HOW WE VERIFY", color = Terracotta, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                Spacer(Modifier.width(4.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp), tint = Terracotta)
            }
        }
    }
}

@Composable
fun Tag(text: String) {
    Surface(
        color = SageGreen.copy(alpha = 0.15f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            fontSize = 14.sp,
            color = SageGreen
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FPOConnectPagePreview() {
    MaterialTheme {
        Box(modifier = Modifier.background(OatBeige)) {
            FPOConnectPage()
        }
    }
}
