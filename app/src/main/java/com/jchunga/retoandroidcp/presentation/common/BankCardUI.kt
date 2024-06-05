//package com.jchunga.retoandroidcp.presentation.common
//
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.aspectRatio
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.material3.Card
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontStyle
//import androidx.compose.ui.unit.sp
//
//@Composable
//fun BankCardUi(
//    // Designing Flexible Composables
//    modifier: Modifier = Modifier, // Modifier as Parameter
//    baseColor: Color = Color(0xFF1252C8),
//    cardNumber: String = "",
//    cardHolder: String = "",
//    expires: String = "",
//    cvv: String = "",
//    brand: String = ""
//) {
//    // Bank Card Aspect Ratio
//    val bankCardAspectRatio = 1.586f // (e.g., width:height = 85.60mm:53.98mm)
//    Card(
//        modifier = modifier
//            .fillMaxWidth()
//            // Aspect Ratio in Compose
//            .aspectRatio(bankCardAspectRatio),
//        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
//    ) {
//        Box {
//            BankCardBackground(baseColor = baseColor)
//            BankCardNumber(cardNumber = cardNumber)
//            // Positioned to corner top left
//            SpaceWrapper(
//                modifier = Modifier.align(Alignment.TopStart),
//                space = 32.dp,
//                top = true,
//                left = true
//            ) {
//                BankCardLabelAndText(label = "card holder", text = cardHolder)
//            }
//            // Positioned to corner bottom left
//            SpaceWrapper(
//                modifier = Modifier.align(Alignment.BottomStart),
//                space = 32.dp,
//                bottom = true,
//                left = true
//            ) {
//                Row {
//                    BankCardLabelAndText(label = "expires", text = expires)
//                    Spacer(modifier = Modifier.width(16.dp))
//                    BankCardLabelAndText(label = "cvv", text = cvv)
//                }
//            }
//            // Positioned to corner bottom right
//            SpaceWrapper(
//                modifier = Modifier.align(Alignment.BottomEnd),
//                space = 32.dp,
//                bottom = true,
//                right = true
//            ) {
//                // Feel free to use an image instead
//                Text(
//                    text = brand, style = TextStyle(
//                        fontFamily = LatoFont,
//                        fontWeight = FontWeight.W500,
//                        fontStyle = FontStyle.Italic,
//                        fontSize = 22.sp,
//                        letterSpacing = 1.sp,
//                        color = Color.White
//                    )
//                )
//            }
//        }
//    }
//}