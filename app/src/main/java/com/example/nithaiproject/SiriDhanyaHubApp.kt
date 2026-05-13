package com.example.nithaiproject

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpCenter
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.NavController
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import kotlinx.coroutines.launch


// --- Project Color Palette ---
val OatBeige = Color(0xFFF5F1E6)
val BarkBrown = Color(0xFF3E2723)
val Terracotta = Color(0xFFE2725B)
val SageGreen = Color(0xFF8F9779)
val SurfaceVariantBeige = Color(0xFFEFEBE0)

@Composable
fun SiriDhanyaHubApp() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // My Wellness State
    var userName by remember { mutableStateOf("") }
    var selectedGoal by remember { mutableStateOf("Grounded Living") }

    MaterialTheme(
        colorScheme = lightColorScheme(
            background = OatBeige,
            onBackground = BarkBrown,
            primary = Terracotta,
            onPrimary = Color.White,
            secondary = SageGreen,
            onSecondary = Color.White,
            surface = Color.White,
            onSurface = BarkBrown
        )
    ) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                DrawerContent(
                    navController = navController,
                    onCloseDrawer = {
                        scope.launch { drawerState.close() }
                    }
                )
            }
        ) {
            Scaffold(
                topBar = { 
                    TopAppBar(
                        onMenuClick = {
                            scope.launch { drawerState.open() }
                        },
                        onProfileClick = {
                            navController.navigate(Screen.Profile.route)
                        }
                    ) 
                },
                bottomBar = { BottomNavBar(navController) },
                containerColor = OatBeige
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = Screen.Home.route,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(Screen.Home.route) { HomeContent(navController, userName, selectedGoal) }
                    composable(Screen.Mandi.route) { MandiWatchPage() }
                    composable(Screen.Recipes.route) { RecipeLabPage() }
                    composable(Screen.Connect.route) { FPOConnectPage() }
                    composable(Screen.Profile.route) { 
                        MyWellnessPage(
                            userName = userName,
                            onNameChange = { userName = it },
                            selectedGoal = selectedGoal,
                            onGoalChange = { selectedGoal = it }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeContent(navController: NavHostController, userName: String, selectedGoal: String) {
    val dailyTips = listOf(
        "Soak millets for at least 30 minutes for better digestion.",
        "Millet is naturally gluten-free and alkaline.",
        "Navane (Foxtail Millet) is rich in Vitamin B12.",
        "Ragi has 3x more calcium than milk per 100g."
    )
    val randomTip = remember { dailyTips.random() }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        item { GreetingSection(userName) }
        
        item {
            DailyTipCard(randomTip)
            Spacer(modifier = Modifier.height(32.dp))
        }

        item { MilletOfWeekSection(navController) }
        item { QuickFactsSection(selectedGoal) }
    }
}

@Composable
fun DailyTipCard(tip: String) {
    Surface(
        color = SageGreen.copy(alpha = 0.1f),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(0.5.dp, SageGreen.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Lightbulb, contentDescription = null, tint = SageGreen)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = tip, fontSize = 14.sp, color = BarkBrown.copy(alpha = 0.8f))
        }
    }
}

@Composable
fun PlaceholderScreen(name: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "$name Coming Soon", fontSize = 24.sp, color = BarkBrown)
    }
}

@Composable
fun DrawerContent(navController: NavHostController, onCloseDrawer: () -> Unit) {
    ModalDrawerSheet(
        drawerContainerColor = OatBeige,
        drawerContentColor = BarkBrown,
        drawerShape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(24.dp)
        ) {
            // Drawer Header
            Text(
                text = "Siri-Dhanya",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Terracotta
            )
            Text(
                text = "Artisanal Wellness Hub",
                fontSize = 14.sp,
                color = BarkBrown.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Navigation Items
            Text(
                text = "NAVIGATION",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = SageGreen,
                letterSpacing = 1.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            bottomNavItems.forEach { screen ->
                NavigationDrawerItem(
                    label = { Text(screen.title, fontWeight = FontWeight.SemiBold) },
                    selected = false,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        onCloseDrawer()
                    },
                    icon = { Icon(screen.icon, contentDescription = null) },
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Color.Transparent,
                        unselectedIconColor = BarkBrown,
                        unselectedTextColor = BarkBrown
                    ),
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Extra Features
            Text(
                text = "MORE",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = SageGreen,
                letterSpacing = 1.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            val context = LocalContext.current
            DrawerExtraItem(
                icon = Icons.Default.Info,
                label = "About Nithai",
                onClick = { /* Handle About */ onCloseDrawer() }
            )
            DrawerExtraItem(
                icon = Icons.Default.Settings,
                label = "Settings",
                onClick = { /* Handle Settings */ onCloseDrawer() }
            )
            DrawerExtraItem(
                icon = Icons.AutoMirrored.Filled.HelpCenter,
                label = "Help & Support",
                onClick = { /* Handle Help */ onCloseDrawer() }
            )
            DrawerExtraItem(
                icon = Icons.Default.Share,
                label = "Share App",
                onClick = { 
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_SUBJECT, "Siri-Dhanya Hub")
                        putExtra(Intent.EXTRA_TEXT, "Join me on Siri-Dhanya Hub to discover the wellness of millets! Download now.")
                    }
                    context.startActivity(Intent.createChooser(shareIntent, "Share App"))
                    onCloseDrawer() 
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            // App Version
            Text(
                text = "v1.0.4 - Artisanal Edition",
                fontSize = 12.sp,
                color = BarkBrown.copy(alpha = 0.4f),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun DrawerExtraItem(icon: ImageVector, label: String, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            Icon(icon, contentDescription = null, tint = BarkBrown, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = label, fontSize = 16.sp, color = BarkBrown)
        }
    }
}

@Composable
fun TopAppBar(onMenuClick: () -> Unit, onProfileClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(OatBeige)
            .statusBarsPadding()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onMenuClick,
            modifier = Modifier
                .clip(CircleShape)
                .size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                tint = Terracotta
            )
        }

        Text(
            text = "Siri-Dhanya Hub",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Terracotta
        )

        IconButton(
            onClick = onProfileClick,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFFF2DEDA)) // surface-variant
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile",
                tint = BarkBrown.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun GreetingSection(userName: String) {
    val greeting = if (userName.isBlank()) "Namaskara!" else "Namaskara, $userName!"
    Column(modifier = Modifier.padding(top = 16.dp, bottom = 40.dp)) {
        Text(
            text = greeting,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = BarkBrown
        )
        Text(
            text = "Your daily dose of grounded, artisanal wellness.",
            fontSize = 18.sp,
            color = BarkBrown.copy(alpha = 0.7f),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun MilletOfWeekSection(navController: NavHostController) {
    Column(modifier = Modifier.padding(bottom = 40.dp)) {
        Text(
            text = "Millet of the Week",
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold,
            color = BarkBrown,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Bento Style Card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .background(SurfaceVariantBeige)
                .border(0.5.dp, BarkBrown.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
        ) {
            // Image Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                // Placeholder for Navane Millet Image
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Gray.copy(alpha = 0.2f))
                ) {
                    AsyncImage(
                        model = R.drawable.millet,
                        contentDescription = "Ragi Millet",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                // Tag
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .clip(RoundedCornerShape(999.dp))
                        .background(SageGreen.copy(alpha = 0.15f))
                        .border(0.5.dp, SageGreen.copy(alpha = 0.2f), RoundedCornerShape(999.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "FOXTAIL",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = SageGreen
                    )
                }
            }

            // Content Area
            Column(modifier = Modifier.padding(24.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Eco,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = SageGreen
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "FEATURED GRAIN",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = SageGreen,
                        letterSpacing = 1.sp
                    )
                }

                Text(
                    text = "Navane",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Terracotta,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Text(
                    text = "An ancient, resilient grain known for its nutty flavor and remarkable health benefits. Grown sustainably without synthetic inputs.",
                    fontSize = 16.sp,
                    color = BarkBrown.copy(alpha = 0.7f),
                    lineHeight = 24.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { navController.navigate(Screen.Recipes.route) },
                    colors = ButtonDefaults.buttonColors(containerColor = Terracotta),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.shadow(8.dp, RoundedCornerShape(16.dp))
                ) {
                    Text(
                        text = "Explore Recipes",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun QuickFactsSection(selectedGoal: String) {
    val factsTitle = when (selectedGoal) {
        "Managing Diabetes" -> "Millets for Glucose Control"
        "Strength & Energy" -> "Fueling for Endurance"
        "Weight Management" -> "Natural Weight Control"
        else -> "Why Millets are Superfoods"
    }

    Column(modifier = Modifier.padding(bottom = 64.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.HealthAndSafety,
                contentDescription = null,
                tint = Terracotta
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = factsTitle,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = BarkBrown
            )
        }

        if (selectedGoal == "Managing Diabetes") {
            FactCard(
                icon = Icons.AutoMirrored.Filled.TrendingDown,
                title = "Low GI Index",
                description = "Releases glucose slowly into the bloodstream, preventing sudden sugar spikes."
            )
        } else if (selectedGoal == "Strength & Energy") {
            FactCard(
                icon = Icons.Default.FlashOn,
                title = "High Protein",
                description = "Millet is a great source of plant-based protein for muscle repair and energy."
            )
        } else {
            FactCard(
                icon = Icons.Default.FiberManualRecord,
                title = "High in Fiber",
                description = "Promotes satiety and aids in better digestion, crucial for metabolic management."
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        FactCard(
            icon = Icons.Default.MonitorHeart,
            title = "Rich in Minerals",
            description = "Packed with essential minerals like magnesium that support heart health and insulin sensitivity."
        )
    }
}

@Composable
fun FactCard(icon: ImageVector, title: String, description: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(1.dp, BarkBrown.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
            .padding(24.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(SageGreen.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = SageGreen
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = BarkBrown
        )

        Text(
            text = description,
            fontSize = 16.sp,
            color = BarkBrown.copy(alpha = 0.7f),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            .shadow(16.dp)
            .navigationBarsPadding(),
        tonalElevation = 0.dp
    ) {
        bottomNavItems.forEach { screen ->
            val isSelected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (screen.route == Screen.Home.route) {
                        // FORCE navigate to Home and clear EVERYTHING else
                        navController.navigate(Screen.Home.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    } else if (currentDestination?.route != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = BarkBrown,
                    selectedTextColor = BarkBrown,
                    indicatorColor = SageGreen.copy(alpha = 0.3f),
                    unselectedIconColor = BarkBrown.copy(alpha = 0.6f),
                    unselectedTextColor = BarkBrown.copy(alpha = 0.6f)
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SiriDhanyaHubAppPreview() {
    SiriDhanyaHubApp()
}
