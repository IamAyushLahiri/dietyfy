package com.example.xyz

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

// ── Design Tokens ──────────────────────────────────────────────────────────────
private val BgDeep       = Color(0xFF0A0F0E)
private val BgMid        = Color(0xFF0D1B15)
private val GreenPrimary = Color(0xFF2E9E58)
private val GreenLight   = Color(0xFF4ABB75)
private val GreenGlow    = Color(0xFF4ABB75).copy(alpha = 0.18f)
private val TextPrimary  = Color(0xFFFFFFFF)
private val TextSub      = Color(0x73FFFFFF)
private val TextMuted    = Color(0x38FFFFFF)
private val BorderSubtle = Color(0x1AFFFFFF)
private val BorderGreen  = Color(0x33FFFFFF)

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onNavigateToDetails: () -> Unit
) {
    var isLoading by remember { mutableStateOf(false) }

    // Infinite animations
    val infiniteTransition = rememberInfiniteTransition(label = "global")

    val ringRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(10_000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ring_rotation"
    )

    val glowPulse by infiniteTransition.animateFloat(
        initialValue = 0.12f,
        targetValue = 0.28f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_pulse"
    )

    val badgeBlink by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "badge_blink"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF0D1F1A), Color(0xFF0A1510), Color(0xFF081208))
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp, vertical = 52.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            // ── TOP: Badge + Logo ──────────────────────────────────────────────
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                // "AI-Powered" badge
                Row(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(GreenLight.copy(alpha = 0.1f))
                        .border(1.dp, GreenLight.copy(alpha = 0.25f), CircleShape)
                        .padding(horizontal = 14.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(GreenLight.copy(alpha = badgeBlink))
                    )
                    Text(
                        "AI-POWERED NUTRITION",
                        fontSize = 10.sp,
                        letterSpacing = 1.5.sp,
                        fontWeight = FontWeight.Medium,
                        color = GreenLight
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Spinning ring + logo orb
                Box(
                    modifier = Modifier.size(140.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Glow halo
                    Box(
                        modifier = Modifier
                            .size(140.dp)
                            .clip(CircleShape)
                            .background(GreenLight.copy(alpha = glowPulse))
                    )
                    // Outer spinning ring with dot
                    Box(
                        modifier = Modifier
                            .size(136.dp)
                            .rotate(ringRotation)
                            .border(1.dp, GreenLight.copy(alpha = 0.2f), CircleShape)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .align(Alignment.TopCenter)
                                .clip(CircleShape)
                                .background(GreenLight)
                        )
                    }
                    // Inner ring
                    Box(
                        modifier = Modifier
                            .size(112.dp)
                            .border(1.dp, GreenLight.copy(alpha = 0.1f), CircleShape)
                    )
                    // Logo circle
                    Box(
                        modifier = Modifier
                            .size(96.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(Color(0xFF1A3A2A), Color(0xFF0F2218))
                                )
                            )
                            .border(1.dp, GreenLight.copy(alpha = 0.3f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🥗", fontSize = 40.sp)
                    }
                }
            }

            // ── MID: Name + Tagline ────────────────────────────────────────────
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Dietify",
                    fontSize = 52.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-1).sp,
                    style = LocalTextStyle.current.copy(
                        brush = Brush.linearGradient(
                            listOf(Color.White, Color(0xFFA8E69B), GreenLight)
                        )
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                // Ornamental divider
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(1.dp)
                            .background(
                                Brush.horizontalGradient(
                                    listOf(Color.Transparent, GreenLight.copy(alpha = 0.4f))
                                )
                            )
                    )
                    Text("✦", fontSize = 10.sp, color = GreenLight.copy(alpha = 0.6f))
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(1.dp)
                            .background(
                                Brush.horizontalGradient(
                                    listOf(GreenLight.copy(alpha = 0.4f), Color.Transparent)
                                )
                            )
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "YOUR PERSONAL DIET PLANNER",
                    fontSize = 11.sp,
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Light,
                    color = TextSub,
                    textAlign = TextAlign.Center
                )
            }

            // ── STATS ROW ─────────────────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(GreenLight.copy(alpha = 0.06f))
                    .border(1.dp, GreenLight.copy(alpha = 0.12f), RoundedCornerShape(16.dp))
            ) {
                listOf(
                    Triple("2M+", "Users", false),
                    Triple("500K", "Recipes", true),
                    Triple("4.9★", "Rating", false)
                ).forEach { (num, label, withDividers) ->
                    if (withDividers) {
                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(48.dp)
                                .background(GreenLight.copy(alpha = 0.12f))
                                .align(Alignment.CenterVertically)
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            num,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            label.uppercase(),
                            fontSize = 9.sp,
                            letterSpacing = 1.sp,
                            color = TextMuted
                        )
                    }
                    if (withDividers) {
                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(48.dp)
                                .background(GreenLight.copy(alpha = 0.12f))
                                .align(Alignment.CenterVertically)
                        )
                    }
                }
            }

            // ── FEATURE CARDS ─────────────────────────────────────────────────
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                listOf(
                    Triple("🎯", "Personalized Plans", "Tailored to your body & goals"),
                    Triple("📊", "Smart Tracking", "Calories, macros & progress")
                ).forEach { (icon, title, sub) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White.copy(alpha = 0.03f))
                            .border(1.dp, BorderSubtle, RoundedCornerShape(12.dp))
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    Brush.linearGradient(
                                        listOf(GreenLight.copy(0.2f), GreenLight.copy(0.05f))
                                    )
                                )
                                .border(1.dp, GreenLight.copy(0.2f), RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(icon, fontSize = 17.sp)
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(title, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = Color.White.copy(0.85f))
                            Text(sub, fontSize = 11.sp, color = TextMuted, fontWeight = FontWeight.Light)
                        }
                        Text("›", fontSize = 18.sp, color = GreenLight.copy(0.4f))
                    }
                }
            }

            // ── BOTTOM: CTA ───────────────────────────────────────────────────
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AnimatedVisibility(
                    visible = !isLoading,
                    enter = fadeIn(tween(300)) + scaleIn(tween(300)),
                    exit = fadeOut(tween(200)) + scaleOut(tween(200))
                ) {
                    Button(
                        onClick = {
                            isLoading = true
                            onNavigateToDetails()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        contentPadding = PaddingValues(0.dp),
                        elevation = ButtonDefaults.buttonElevation(0.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.linearGradient(
                                        listOf(Color(0xFF2E9E58), Color(0xFF1D7A3F), Color(0xFF155C30))
                                    ),
                                    RoundedCornerShape(14.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Get Started — It's Free",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium,
                                letterSpacing = 0.5.sp,
                                color = Color.White
                            )
                        }
                    }
                }

                AnimatedVisibility(
                    visible = isLoading,
                    enter = fadeIn(tween(400)) + scaleIn(tween(400)),
                    exit = fadeOut(tween(200)) + scaleOut(tween(200))
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(32.dp),
                            color = GreenLight,
                            strokeWidth = 2.dp,
                            trackColor = GreenLight.copy(alpha = 0.12f)
                        )
                        Text(
                            "Preparing your journey...",
                            fontSize = 13.sp,
                            color = TextSub,
                            letterSpacing = 0.5.sp
                        )
                    }
                }

                Text(
                    "By continuing you agree to our Terms & Privacy Policy",
                    fontSize = 10.sp,
                    color = TextMuted,
                    textAlign = TextAlign.Center,
                    lineHeight = 16.sp
                )
            }
        }
    }

    LaunchedEffect(isLoading) {
        if (isLoading) {
            delay(3000)
            isLoading = false
        }
    }
}