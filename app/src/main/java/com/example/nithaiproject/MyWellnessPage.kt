package com.example.nithaiproject

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyWellnessPage(
    userName: String,
    onNameChange: (String) -> Unit,
    selectedGoal: String,
    onGoalChange: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        contentPadding = PaddingValues(top = 24.dp, bottom = 100.dp)
    ) {
        item {
            Text(
                text = "My Wellness",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = BarkBrown
            )
            Text(
                text = "Personalize your artisanal journey.",
                fontSize = 16.sp,
                color = BarkBrown.copy(alpha = 0.6f),
                modifier = Modifier.padding(top = 8.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
        }

        item {
            WellnessSectionTitle("IDENTITY")
            OutlinedTextField(
                value = userName,
                onValueChange = onNameChange,
                label = { Text("Your Name") },
                placeholder = { Text("How shall we greet you?") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Terracotta,
                    unfocusedBorderColor = BarkBrown.copy(alpha = 0.1f)
                )
            )
            Spacer(modifier = Modifier.height(32.dp))
        }

        item {
            WellnessSectionTitle("DIETARY FOCUS")
            val goals = listOf("Managing Diabetes", "Grounded Living", "Strength & Energy", "Weight Management")
            
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                goals.forEach { goal ->
                    GoalChip(
                        text = goal,
                        isSelected = selectedGoal == goal,
                        onClick = { onGoalChange(goal) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }

        item {
            WellnessSectionTitle("SAVED RECIPES")
            // Simplified view of saved recipes
            val savedRecipes = listOf(
                "Ragi Mudde",
                "Navane Upma"
            )
            
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                savedRecipes.forEach { recipe ->
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White,
                        border = androidx.compose.foundation.BorderStroke(0.5.dp, BarkBrown.copy(alpha = 0.1f))
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Restaurant, contentDescription = null, tint = SageGreen)
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = recipe, fontSize = 16.sp, color = BarkBrown)
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(Icons.Default.Favorite, contentDescription = null, tint = Terracotta)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WellnessSectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = SageGreen,
        letterSpacing = 1.sp,
        modifier = Modifier.padding(bottom = 12.dp)
    )
}

@Composable
fun GoalChip(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) SageGreen.copy(alpha = 0.1f) else Color.White,
        border = androidx.compose.foundation.BorderStroke(
            width = if (isSelected) 1.dp else 0.5.dp,
            color = if (isSelected) SageGreen else BarkBrown.copy(alpha = 0.1f)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(selectedColor = SageGreen)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                fontSize = 16.sp,
                color = if (isSelected) SageGreen else BarkBrown,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
            )
        }
    }
}
