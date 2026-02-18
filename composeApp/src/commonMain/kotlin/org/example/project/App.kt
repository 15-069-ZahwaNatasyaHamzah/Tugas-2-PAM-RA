package org.example.project

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {

    val repository = remember { DataRepository() }
    val viewModel = remember { NewsViewModel(repository) }

    val currentNews by viewModel.newsFlow.collectAsState(
        initial = News(0, "Memuat berita...", "")
    )

    val readCount by viewModel.readCount.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    val categories = listOf("Semua", "Tech", "Campus", "Cuaca", "Otomotif")

    var expanded by remember { mutableStateOf(false) }
    var openedNews by remember { mutableStateOf<News?>(null) }
    var detailText by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    val iosBlue = Color(0xFF007AFF)
    val iosBackground = Color(0xFFF2F2F7)

    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = iosBlue,
            background = iosBackground,
            surface = Color.White
        )
    ) {

        Scaffold(
            containerColor = iosBackground,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "News",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = iosBackground
                    )
                )
            }
        ) { padding ->

            LazyColumn(
                contentPadding = PaddingValues(
                    start = 20.dp,
                    end = 20.dp,
                    top = padding.calculateTopPadding() + 10.dp,
                    bottom = 40.dp
                ),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.fillMaxSize()
            ) {

                // FILTER
                item {
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {

                        TextField(
                            value = selectedCategory,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Kategori") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White
                            )
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            categories.forEach { category ->
                                DropdownMenuItem(
                                    text = { Text(category) },
                                    onClick = {
                                        viewModel.setCategory(category)
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                // NEWS CARD
                item {
                    Card(
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(0.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Column(modifier = Modifier.padding(20.dp)) {

                            Text(
                                text = currentNews.category,
                                color = iosBlue,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = currentNews.title,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "$readCount kali dibaca",
                                fontSize = 13.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }

                // BUTTON iOS STYLE
                item {
                    Button(
                        onClick = {
                            openedNews = currentNews
                            scope.launch {
                                detailText = repository.fetchNewsDetail(currentNews)
                            }
                        },
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = iosBlue
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(
                            "Baca Selengkapnya",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // DETAIL ARTICLE
                if (openedNews != null && detailText.isNotEmpty()) {

                    item {
                        Card(
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            elevation = CardDefaults.cardElevation(0.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Column(modifier = Modifier.padding(20.dp)) {

                                Text(
                                    text = openedNews!!.title,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.SemiBold
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Text(
                                    text = detailText,
                                    lineHeight = 22.sp,
                                    fontSize = 15.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
