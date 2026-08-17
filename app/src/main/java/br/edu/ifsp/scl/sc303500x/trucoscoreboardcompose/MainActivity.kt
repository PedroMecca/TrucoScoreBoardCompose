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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.ifsp.scl.sc303500x.trucoscoreboardcompose.ui.theme.TrucoScoreBoardComposeTheme

private const val PONTUACAO_MAXIMA = 12
private const val MAO_DE_ONZE = 11

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
    var nomeEquipeA by remember { mutableStateOf("") }
    var nomeEquipeB by remember { mutableStateOf("") }
    var partidaComecou by remember { mutableStateOf(false) }
    var partidasVencidasEquipeA by remember { mutableIntStateOf(0) }
    var partidasVencidasEquipeB by remember { mutableIntStateOf(0) }

    val jogoFinalizado = pontosEquipeA >= PONTUACAO_MAXIMA || pontosEquipeB >= PONTUACAO_MAXIMA

    val nomeExibidoA = nomeEquipeA.ifBlank { "Equipe A" }
    val nomeExibidoB = nomeEquipeB.ifBlank { "Equipe B" }

    val avisoMaoDeOnze = when {
        jogoFinalizado -> null
        pontosEquipeA == MAO_DE_ONZE && pontosEquipeB == MAO_DE_ONZE ->
            "Mão de 11 para $nomeExibidoA e $nomeExibidoB!"
        pontosEquipeA == MAO_DE_ONZE -> "$nomeExibidoA entrou na mão de 11!"
        pontosEquipeB == MAO_DE_ONZE -> "$nomeExibidoB entrou na mão de 11!"
        else -> null
    }

    val nomeVencedor = when {
        pontosEquipeA >= PONTUACAO_MAXIMA -> nomeExibidoA
        pontosEquipeB >= PONTUACAO_MAXIMA -> nomeExibidoB
        else -> null
    }

    fun adicionarPontos(equipe: Char, pontos: Int) {
        if (jogoFinalizado) return
        partidaComecou = true

        if (equipe == 'A') {
            pontosEquipeA = (pontosEquipeA + pontos).coerceAtMost(PONTUACAO_MAXIMA)
            if (pontosEquipeA >= PONTUACAO_MAXIMA) partidasVencidasEquipeA++
        } else {
            pontosEquipeB = (pontosEquipeB + pontos).coerceAtMost(PONTUACAO_MAXIMA)
            if (pontosEquipeB >= PONTUACAO_MAXIMA) partidasVencidasEquipeB++
        }
    }

    fun iniciarNovaMao() {
        pontosEquipeA = 0
        pontosEquipeB = 0
        partidaComecou = false
    }

    fun zerarPlacarDePartidas() {
        partidasVencidasEquipeA = 0
        partidasVencidasEquipeB = 0
        iniciarNovaMao()
    }

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

        Text(
            text = "Partidas: $partidasVencidasEquipeA x $partidasVencidasEquipeB",
            color = Color(0xCCFFFFFF),
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 4.dp)
        )

        if (avisoMaoDeOnze != null) {
            Text(
                text = avisoMaoDeOnze,
                color = Color(0xFFFFEB3B),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        if (nomeVencedor != null) {
            Text(
                text = "$nomeVencedor venceu a partida!",
                color = Color(0xFFFF5252),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

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
                nomeEquipe = nomeEquipeA,
                onNomeChange = { nomeEquipeA = it },
                nomeTravado = partidaComecou,
                pontos = pontosEquipeA,
                corFundo = Color(0xFF2E7D32),
                habilitado = !jogoFinalizado,
                onMais1 = { adicionarPontos('A', 1) },
                onMais3 = { adicionarPontos('A', 3) }
            )

            EquipeCard(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                nomeEquipe = nomeEquipeB,
                onNomeChange = { nomeEquipeB = it },
                nomeTravado = partidaComecou,
                pontos = pontosEquipeB,
                corFundo = Color(0xFF1565C0),
                habilitado = !jogoFinalizado,
                onMais1 = { adicionarPontos('B', 1) },
                onMais3 = { adicionarPontos('B', 3) }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { iniciarNovaMao() },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Nova Mão")
            }

            Button(
                onClick = { zerarPlacarDePartidas() },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Zerar Placar")
            }
        }
    }
}

@Composable
fun EquipeCard(
    modifier: Modifier = Modifier,
    nomeEquipe: String,
    onNomeChange: (String) -> Unit,
    nomeTravado: Boolean,
    pontos: Int,
    corFundo: Color,
    habilitado: Boolean,
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
        OutlinedTextField(
            value = nomeEquipe,
            onValueChange = onNomeChange,
            enabled = !nomeTravado,
            placeholder = { Text("Nome da equipe") },
            singleLine = true,
            textStyle = androidx.compose.ui.text.TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words
            ),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                disabledTextColor = Color.White,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth()
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
            enabled = habilitado,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text(text = "+1")
        }

        Button(
            onClick = onMais3,
            enabled = habilitado,
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