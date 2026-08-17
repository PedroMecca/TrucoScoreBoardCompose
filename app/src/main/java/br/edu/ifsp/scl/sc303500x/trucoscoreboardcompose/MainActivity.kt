package br.edu.ifsp.scl.sc303500x.trucoscoreboardcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.ifsp.scl.sc303500x.trucoscoreboardcompose.ui.theme.TrucoScoreBoardComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrucoScoreBoardComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TrucoScoreBoard(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TrucoScoreBoard(modifier: Modifier = Modifier) {
    var pontosEquipeA by remember { mutableIntStateOf(0) }
    var pontosEquipeB by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1B5E20))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Truco Scoreboard",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            EquipeCard(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                nomeEquipe = "Equipe A",
                pontos = pontosEquipeA,
                corFundo = Color(0xFF2E7D32),
                onMais1 = { pontosEquipeA += 1 },
                onMais3 = { pontosEquipeA += 3 }
            )

            EquipeCard(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                nomeEquipe = "Equipe B",
                pontos = pontosEquipeB,
                corFundo = Color(0xFF1565C0),
                onMais1 = { pontosEquipeB += 1 },
                onMais3 = { pontosEquipeB += 3 }
            )
        }

        Button(
            onClick = {
                pontosEquipeA = 0
                pontosEquipeB = 0
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = "Reiniciar Partida")
        }
    }
}

@Composable
fun EquipeCard(
    modifier: Modifier = Modifier,
    nomeEquipe: String,
    pontos: Int,
    corFundo: Color,
    onMais1: () -> Unit,
    onMais3: () -> Unit
) {
    Column(
        modifier = modifier
            .background(corFundo)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = nomeEquipe,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = pontos.toString(),
            color = Color.White,
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Button(
            onClick = onMais1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text(text = "+1")
        }

        Button(
            onClick = onMais3,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "+3")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TrucoScoreBoardPreview() {
    TrucoScoreBoardComposeTheme {
        TrucoScoreBoard()
    }
}