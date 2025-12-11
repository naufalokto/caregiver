package com.example.caregiver.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import com.example.caregiver.R
import com.example.caregiver.ui.viewmodel.RegisterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = viewModel(),
    onRegisterClick: (String, String, String, String, String) -> Unit = { _, _, _, _, _ -> },
    onLoginClick: () -> Unit = {}
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var genderExpanded by remember { mutableStateOf(false) }
    val genderOptions = listOf("Male", "Female", "Other")
    
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    
    // Show error message
    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { error ->
            snackbarHostState.showSnackbar(error)
            viewModel.clearError()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(27.dp))

        // Logo
        Image(
            painter = painterResource(id = R.drawable.logo2),
            contentDescription = "Logo",
            modifier = Modifier.size(142.dp, 81.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(50.dp))

        // Create an account text
        Text(
            text = "Create an account",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(50.dp))

        // Username Field
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username", color = Color(0xB3000000)) },
            modifier = Modifier
                .width(312.dp),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0x66000000),
                unfocusedBorderColor = Color(0x66000000),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 16.sp,
                color = Color.Black
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(15.dp))

        // Email Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = Color(0xB3000000)) },
            modifier = Modifier
                .width(312.dp),
            shape = RoundedCornerShape(10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0x66000000),
                unfocusedBorderColor = Color(0x66000000),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 16.sp,
                color = Color.Black
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(15.dp))

        // Phone Number Field
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number", color = Color(0xB3000000)) },
            modifier = Modifier
                .width(312.dp),
            shape = RoundedCornerShape(10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0x66000000),
                unfocusedBorderColor = Color(0x66000000),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 16.sp,
                color = Color.Black
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(15.dp))

        // Gender Field (Dropdown)
        ExposedDropdownMenuBox(
            expanded = genderExpanded,
            onExpandedChange = { genderExpanded = !genderExpanded }
        ) {
            OutlinedTextField(
                value = gender,
                onValueChange = {},
                readOnly = true,
                label = { Text("Gender", color = Color(0xB3000000)) },
                modifier = Modifier
                    .width(312.dp)
                    .menuAnchor(),
                shape = RoundedCornerShape(10.dp),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = genderExpanded) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0x66000000),
                    unfocusedBorderColor = Color(0x66000000),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontSize = 16.sp,
                    color = Color.Black
                )
            )
            ExposedDropdownMenu(
                expanded = genderExpanded,
                onDismissRequest = { genderExpanded = false }
            ) {
                genderOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            gender = option
                            genderExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", color = Color(0xB3000000)) },
            modifier = Modifier
                .width(312.dp),
            shape = RoundedCornerShape(10.dp),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Image(
                        painter = painterResource(id = R.drawable.eye_password),
                        contentDescription = "Toggle password visibility",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(Color(0xB3000000))
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0x66000000),
                unfocusedBorderColor = Color(0x66000000),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 16.sp,
                color = Color.Black
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(15.dp))

        // Create Button
        Button(
            onClick = { 
                onRegisterClick(username, email, phoneNumber, gender, password)
                viewModel.registerUser(username, email, phoneNumber, gender, password)
            },
            modifier = Modifier
                .width(312.dp)
                .height(48.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0E64D1)
            ),
            enabled = !uiState.isLoading
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = "Create",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xE6FFFFFF)
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        // Already have account text
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Already have an account? ",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xCC000000)
            )
            Text(
                text = "Login",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF2F89FC),
                modifier = Modifier.clickable { onLoginClick() }
            )
        }
        
        // Snackbar for error messages
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    }
}

