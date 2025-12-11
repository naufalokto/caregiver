package com.example.caregiver.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caregiver.R

@Composable
fun HomeScreen(
    username: String,
    onCaregiverClick: () -> Unit = {},
    onDisabilityClick: () -> Unit = {}
) {
    // Manrope font family - fallback to Default if font files are not available
    // To use Manrope font, add Manrope-Regular.ttf and Manrope-Bold.ttf to res/font/
    // Then update this to: FontFamily(Font(R.font.manrope_regular, FontWeight.Normal), Font(R.font.manrope_bold, FontWeight.Bold))
    val manropeFontFamily = FontFamily.Default // Will use Manrope when font files are added
    
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background gradient - vertical from light blue to light green according to Figma
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFE0EFFF), // Light blue from top
                            Color(0xFFE6FFE6)  // Light green towards bottom
                        )
                    )
                )
        )
        
        // Decorative ellipses positioned according to Figma design
        // Top-left decorative shape
        Box(
            modifier = Modifier
                .offset(x = (-80).dp, y = (-100).dp)
                .size(200.dp, 180.dp)
                .background(
                    color = Color(0xFF202020),
                    shape = RoundedCornerShape(percent = 50)
                )
                .clip(RoundedCornerShape(percent = 50))
        )
        
        // Bottom-left decorative shape
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = (-60).dp, y = (-50).dp)
                .size(250.dp, 220.dp)
                .background(
                    color = Color(0xFF202020),
                    shape = RoundedCornerShape(percent = 50)
                )
                .clip(RoundedCornerShape(percent = 50))
        )
        
        // Bottom-right decorative shape (light blue gradient with dark shape)
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 20.dp, y = 20.dp)
                .size(180.dp, 160.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFFFFFFF),
                            Color(0xFF4AFFB1) // Light mint green/cyan
                        ),
                        start = androidx.compose.ui.geometry.Offset(0f, 0f),
                        end = androidx.compose.ui.geometry.Offset(180f, 160f)
                    ),
                    shape = RoundedCornerShape(bottomEnd = 40.dp)
                )
                .clip(RoundedCornerShape(bottomEnd = 40.dp))
        )
        
        // Dark shape behind bottom-right gradient
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 10.dp, y = 10.dp)
                .size(170.dp, 150.dp)
                .background(
                    color = Color(0xFF202020),
                    shape = RoundedCornerShape(bottomEnd = 35.dp)
                )
                .clip(RoundedCornerShape(bottomEnd = 35.dp))
        )
        
        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo at top-left
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 40.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                // Logo: two wavy teal lines, horizontal line, and dark blue circle
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Two wavy lines (teal)
                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = "~~",
                            fontSize = 20.sp,
                            color = Color(0xFF4DB6AC), // Teal color
                            fontWeight = FontWeight.Bold
                        )
                    }
                    // Horizontal line
                    Box(
                        modifier = Modifier
                            .width(20.dp)
                            .height(2.dp)
                            .background(Color(0xFF4DB6AC))
                    )
                    // Dark blue circle
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(Color(0xFF1976D2), shape = CircleShape)
                    )
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Welcome message with Manrope font styling
            Text(
                text = "Welcome, $username ",
                style = TextStyle(
                    fontSize = 32.sp,
                    fontFamily = manropeFontFamily,
                    fontWeight = FontWeight(700),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Please pick your mode",
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF1A1A1A),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 40.dp)
            )

            // Mode selection cards
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Caregiver Card
                ModeCard(
                    title = "Caregiver",
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onCaregiverClick() }
                )

                // Disability Card
                ModeCard(
                    title = "Disability",
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onDisabilityClick() }
                )
            }
            
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun ModeCard(
    title: String,
    modifier: Modifier = Modifier
) {
    // Frosted glass effect card according to Figma design
    Box(
        modifier = modifier
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                color = Color(0x80FFFFFF), // Semi-transparent white for frosted glass effect
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF000000), // Black text
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Icon based on mode - simplified outline style matching Figma
            Box(
                modifier = Modifier
                    .size(80.dp),
                contentAlignment = Alignment.Center
            ) {
                if (title == "Caregiver") {
                    // Open hand with three stylized figures (people) - outline icon style
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        // Three stylized figures (people) - outline style
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            repeat(3) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    // Head (circle outline)
                                    Box(
                                        modifier = Modifier
                                            .size(10.dp)
                                            .border(
                                                width = 2.dp,
                                                color = Color(0xFF1976D2),
                                                shape = CircleShape
                                            )
                                    )
                                    // Body (rounded rectangle outline)
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp, 12.dp)
                                            .border(
                                                width = 2.dp,
                                                color = Color(0xFF1976D2),
                                                shape = RoundedCornerShape(2.dp)
                                            )
                                    )
                                }
                            }
                        }
                        // Open hand representation (simplified outline)
                        Box(
                            modifier = Modifier
                                .size(36.dp, 24.dp)
                                .border(
                                    width = 2.dp,
                                    color = Color(0xFF1976D2),
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            // Simplified hand fingers
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(3.dp)
                            ) {
                                repeat(5) {
                                    Box(
                                        modifier = Modifier
                                            .width(3.dp)
                                            .height(16.dp)
                                            .border(
                                                width = 1.5.dp,
                                                color = Color(0xFF1976D2),
                                                shape = RoundedCornerShape(1.dp)
                                            )
                                    )
                                }
                            }
                        }
                    }
                } else {
                    // OK gesture hand with speech bubble - outline icon style
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        // OK gesture hand (thumb and index finger forming circle)
                        Box(
                            modifier = Modifier.size(36.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            // Circle for OK gesture
                            Box(
                                modifier = Modifier
                                    .size(18.dp)
                                    .border(
                                        width = 2.dp,
                                        color = Color(0xFF1976D2),
                                        shape = CircleShape
                                    )
                            )
                            // Extended fingers
                            Row(
                                modifier = Modifier.offset(x = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                repeat(3) {
                                    Box(
                                        modifier = Modifier
                                            .width(2.dp)
                                            .height(12.dp)
                                            .border(
                                                width = 1.5.dp,
                                                color = Color(0xFF1976D2),
                                                shape = RoundedCornerShape(1.dp)
                                            )
                                    )
                                }
                            }
                        }
                        // Speech bubble with three horizontal lines
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp, 18.dp)
                                    .border(
                                        width = 2.dp,
                                        color = Color(0xFF1976D2),
                                        shape = RoundedCornerShape(4.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    repeat(3) {
                                        Box(
                                            modifier = Modifier
                                                .width(14.dp)
                                                .height(1.5.dp)
                                                .background(Color(0xFF1976D2))
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
