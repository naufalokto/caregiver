package com.example.caregiver.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caregiver.R

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp),
        contentAlignment = Alignment.Center
    ) {
        // Background Ellipse Decorations
        EllipseDecoration(
            modifier = Modifier
                .offset(x = (-160).dp, y = 44.dp)
                .size(320.dp, 366.dp)
                .alpha(0.5f)
        )
        
        EllipseDecoration(
            modifier = Modifier
                .offset(x = 145.dp, y = 170.dp)
                .size(373.dp, 430.dp)
                .alpha(0.5f)
        )
        
        EllipseDecoration(
            modifier = Modifier
                .offset(x = (-103).dp, y = (-301).dp)
                .align(Alignment.BottomStart)
                .size(311.dp, 301.dp)
                .alpha(0.5f)
        )

        // Main Content
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_enabler),
                contentDescription = "Enabler Logo",
                modifier = Modifier
                    .size(171.dp, 127.dp),
                contentScale = ContentScale.Fit
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Text(
                text = "Enabler",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun EllipseDecoration(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(
                color = Color(0xFF4285F4),
                shape = CircleShape
            )
    )
}

